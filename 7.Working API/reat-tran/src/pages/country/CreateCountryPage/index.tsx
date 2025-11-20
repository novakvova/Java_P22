import { useState } from "react";
import {useCreateCountryMutation} from "../../../services/countryService.ts";

const CreateCountryPage = () => {
    const [createCountry, { isLoading }] = useCreateCountryMutation();

    const [form, setForm] = useState({
        name: "",
        code: "",
        slug: "",
    });

    const [image, setImage] = useState(null);
    const [preview, setPreview] = useState(null);

    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleImage = (e) => {
        const file = e.target.files[0];
        setImage(file);
        if (file) setPreview(URL.createObjectURL(file));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            setError(null);

            await createCountry({
                ...form,
                image,
            }).unwrap();

            setMessage("Country created successfully!");
            setForm({ name: "", code: "", slug: "" });
            setImage(null);
            setPreview(null);

        } catch (err) {
            setError("Failed to create country.");
        }
    };

    return (
        <div className="max-w-lg mx-auto bg-neutral-primary border border-default shadow p-6 rounded-lg">
            <h1 className="text-2xl text-heading font-semibold mb-4">Create Country</h1>

            <form onSubmit={handleSubmit} className="space-y-4">

                <div>
                    <label className="block text-heading mb-1">Name</label>
                    <input
                        name="name"
                        type="text"
                        value={form.name}
                        onChange={handleChange}
                        className="block w-full p-2.5 rounded-base border border-default bg-neutral-secondary-soft focus:ring-brand focus:border-brand"
                        required
                    />
                </div>

                <div>
                    <label className="block text-heading mb-1">Code</label>
                    <input
                        name="code"
                        type="text"
                        value={form.code}
                        onChange={handleChange}
                        className="block w-full p-2.5 rounded-base border border-default bg-neutral-secondary-soft focus:ring-brand focus:border-brand"
                        required
                    />
                </div>

                <div>
                    <label className="block text-heading mb-1">Slug</label>
                    <input
                        name="slug"
                        type="text"
                        value={form.slug}
                        onChange={handleChange}
                        className="block w-full p-2.5 rounded-base border border-default bg-neutral-secondary-soft focus:ring-brand focus:border-brand"
                        required
                    />
                </div>

                <div>
                    <label className="block text-heading mb-1">Image</label>
                    <input
                        type="file"
                        accept="image/*"
                        onChange={handleImage}
                        className="block w-full text-body file:mr-4 file:py-2.5 file:px-4 file:rounded-base file:border-0 file:bg-brand file:text-white hover:file:bg-opacity-90"
                    />

                    {preview && (
                        <img
                            src={preview}
                            alt="Preview"
                            className="mt-3 h-32 w-full object-cover rounded border border-default"
                        />
                    )}
                </div>

                {error && <p className="text-red-500 text-sm">{error}</p>}
                {message && <p className="text-green-500 text-sm">{message}</p>}

                <button
                    type="submit"
                    disabled={isLoading}
                    className="w-full py-2.5 bg-brand text-white rounded-base hover:bg-opacity-90 transition disabled:opacity-50"
                >
                    {isLoading ? "Saving..." : "Create Country"}
                </button>
            </form>
        </div>
    );
}

export default CreateCountryPage;
