import { Editor } from "@tinymce/tinymce-react";
import { useRef } from "react";
import { APP_ENV } from "../../env";
import {useSaveImageFromUrlMutation, useSaveImageMutation} from "../../services/fileService";

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
    const [saveImageFromUrl] = useSaveImageFromUrlMutation();
    //@ts-ignore
    const editorRef = useRef<any>(null);
    const uploadedImagesRef = useRef<{ id: number; imageName: string }[]>([]);

    const uploadImage = async (data: Blob | string) => {
        const response = await (
            typeof data === "string"
                ? saveImageFromUrl(data)
                : saveImage({ imageFile: data as File })
        ).unwrap();

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

                    editor.on("Paste", async (e) => {
                        e.preventDefault();

                        const clipboardData = e.clipboardData || e.originalEvent?.clipboardData;
                        if (!clipboardData) return;

                        const html = clipboardData.getData("text/html");
                        const text = clipboardData.getData("text/plain");

                        let contentToInsert = html || text;
                        if (!contentToInsert) return;

                        const temp = document.createElement("div");
                        temp.innerHTML = contentToInsert;

                        const imgs = Array.from(temp.querySelectorAll("img"));

                        for (const img of imgs) {
                            const src = img.getAttribute("src");
                            if (!src || src.startsWith(APP_ENV.IMAGE_BASE_URL)) continue;

                            try {
                                const { url } = await uploadImage(src);
                                img.setAttribute("src", url);
                            } catch (err) {
                                console.error("Failed to upload image:", err);
                            }
                        }

                        editor.insertContent(temp.innerHTML);
                    });

                    editor.on("NodeChange", async (e) => {
                        const node = e.element;

                        if (node.nodeName !== "IMG") return;
                        if (node.getAttribute("data-id")) return;

                        const src = node.getAttribute("src");
                        if (!src || src.startsWith(APP_ENV.IMAGE_BASE_URL)) return;

                        console.log("Found external image:", src);

                        try {
                            const res = await uploadImage(src);

                            console.log("Uploaded image:", res);

                            node.setAttribute("src", res.url);
                            node.setAttribute("data-id", String(res.id));

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
                    return url;
                }

            }}
        />
    );
};

export default CityDescriptionEditor;
