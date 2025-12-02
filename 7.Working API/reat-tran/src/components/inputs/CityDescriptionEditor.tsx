import { Editor } from "@tinymce/tinymce-react";
import { useRef } from "react";
import { APP_ENV } from "../../env";
import { useSaveImageMutation } from "../../services/fileService";

interface Props {
    value: string;
    onChange: (value: string) => void;
    onDescriptionImageIdsChange: (ids: number[]) => void;
}

const CityDescriptionEditor: React.FC<Props> = ({
                                                    value,
                                                    onChange,
                                                    onDescriptionImageIdsChange,
                                                }) => {
    const [saveImage] = useSaveImageMutation();
    //@ts-ignore
    const editorRef = useRef<any>(null);
    const uploadedImagesRef = useRef<{ id: number; imageName: string }[]>([]);

    const uploadImage = async (file: Blob) => {
        //@ts-ignore
        const response = await saveImage({ imageFile: file }).unwrap();

        uploadedImagesRef.current.push({
            id: response.id,
            imageName: response.imageName,
        });

        const url = `${APP_ENV.IMAGE_BASE_URL}large/${response.imageName}`;
        return { url, id: response.id };
    };

    const syncImageIds = (content: string) => {
        const tempDiv = document.createElement("div");
        tempDiv.innerHTML = content;

        console.log(tempDiv)

        const imgs = Array.from(tempDiv.querySelectorAll("img"));

        const ids = imgs.map(img => {
            const dataId = img.getAttribute("data-id");
            if (dataId) return Number(dataId);

            const src = img.getAttribute("src") || "";
            const match = uploadedImagesRef.current.find(x => src.includes(x.imageName));
            return match?.id;
        }).filter(Boolean) as number[];

        onDescriptionImageIdsChange(ids);
    };


    return (
        <Editor
            onInit={(_, e) => (editorRef.current = e)}
            apiKey={APP_ENV.APP_TINYMCE_KEY}
            value={value}
            onEditorChange={(content) => {
                onChange(content);
                syncImageIds(content);
            }}
            init={{
                height: 450,
                plugins: [
                    "advlist", "anchor", "autolink", "charmap", "code", "fullscreen",
                    "help", "image", "insertdatetime", "link", "lists", "media",
                    "preview", "searchreplace", "table", "visualblocks"
                ],
                toolbar:
                    "undo redo | styles | bold italic underline | alignleft aligncenter alignright | bullist numlist outdent indent | link image code",
                automatic_uploads: true,
                images_file_types: "jpg,jpeg,png,webp",
                paste_data_images: false,

                setup: (editor) => {
                    // editor.on("PastePostProcess", async (e) => {
                    //     const html = e.node.innerHTML; // весь вставлений блок
                    //     console.log("FULL PASTED BLOCK:", html);
                    //
                    //     const temp = document.createElement("div");
                    //     temp.innerHTML = html;
                    //
                    //     const images = temp.querySelectorAll("img");
                    //     console.log("IMAGES:", images);
                    //     for (const img of images) {
                    //         const src = img.getAttribute("src");
                    //         if (!src) continue;
                    //
                    //     // Якщо це base64 або blob – завантажуємо
                    //         if (src.startsWith("data:") || src.startsWith("blob:")) {
                    //             const blob = await fetch(src).then((r) => r.blob());
                    //             const { url } = await uploadImage(blob);
                    //             img.setAttribute("src", url);
                    //         }
                    //     }
                    //
                    //     editor.setContent(temp.innerHTML);
                    //     e.preventDefault(); // блокує дефолтну вставку
                    // });


                    editor.on("Paste", async (e) => {
                        const items = e.clipboardData?.items;
                        if (!items) return;
                        e.preventDefault();

                        for (const item of items) {
                            console.log("item", item);
                            if(item.kind !== "file") {
                                item.getAsString(async (s) => {
                                    console.log("RAW HTML:", s);

                                    // --- 1. Парсимо HTML у DOM ---
                                    const temp = document.createElement("div");
                                    temp.innerHTML = s;

                                    // --- 2. Знаходимо всі <img> ---
                                    const imgs = temp.querySelectorAll("img");

                                    for (const img of imgs) {
                                        const src = img.getAttribute("src");
                                        if (!src) continue;

                                        console.log("FOUND IMG:", src);

                                        // --- 3. Завантажуємо картинку якщо це base64 / blob ---
                                        if (src.startsWith("data:") || src.startsWith("blob:")) {

                                            const blob = await fetch(src).then((r) => r.blob());
                                            const file = new File([blob], "image.png", { type: blob.type });
                                            const response = await saveImage({ imageFile: file }).unwrap();

                                            //const { url } = await uploadImage(blob);
                                            const serverUrl = `${APP_ENV.IMAGE_BASE_URL}large/${response.imageName}`;
                                            console.log("UPLOAD RESULT:", serverUrl);

                                            // --- 5. Замінюємо src на url з сервера ---
                                            img.setAttribute("src", serverUrl);
                                        }
                                    }


                                    // --- 7. Вставляємо оновлений HTML ---
                                    editor.insertContent(temp.innerHTML);
                                });
                            }

                            // if (item.type.indexOf("image") !== -1) {
                            //     e.preventDefault();
                            //     const file = item.getAsFile();
                            //     if (!file) continue;
                            //
                            //     const { url } = await uploadImage(file);
                            //     editor.insertContent(`<img src="${url}" />`);
                            //
                            // }
                        }
                    });



                    editor.on("NodeChange", async (e) => {
                        const node = e.element;

                        if (node.nodeName !== "IMG") return;
                        if (node.getAttribute("data-id")) return;

                        const src = node.getAttribute("src");
                        if (!src || src.startsWith(APP_ENV.IMAGE_BASE_URL)) return;

                        try {
                            const blob = await fetch(src).then((r) => r.blob());
                            const file = new File([blob], "image.png", { type: blob.type });
                            const response = await saveImage({ imageFile: file }).unwrap();

                            uploadedImagesRef.current.push({
                                id: response.id,
                                imageName: response.imageName,
                            });

                            const serverUrl = `${APP_ENV.IMAGE_BASE_URL}large/${response.imageName}`;
                            node.setAttribute("src", serverUrl);
                            node.setAttribute("data-id", String(response.id));

                            const editorBody = editor.getBody();
                            const updatedHtml = editorBody.innerHTML;

                            onChange(updatedHtml);
                            syncImageIds(updatedHtml);

                        } catch (err) {
                            console.error("Failed to reupload external image", err);
                        }
                    });
                },

                images_upload_handler: async (blobInfo) => {
                    const file = blobInfo.blob();
                    const { url } = await uploadImage(file);
                    // console.log("Simple Upload " + url);
                    return url;
                }

            }}
        />
    );
};

export default CityDescriptionEditor;
