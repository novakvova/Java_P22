import React, { useState } from "react";

interface Props {
    label: string;
    name: string;
    accept?: string;
    file?: File | null;
    setFile: (file: File | null) => void;
    error?: string;
    setError?: (err?: string) => void;
    maxSize?: number;
    allowedTypes?: string[];
}

const FileUploadField: React.FC<Props> = ({
                                              label,
                                              name,
                                              accept,
                                              file,
                                              setFile,
                                              error,
                                              setError,
                                              maxSize = 10,
                                              allowedTypes = [],
                                          }) => {
    const [preview, setPreview] = useState<string | null>(null);

    const validateFile = (file: File): string | null => {
        const fileSizeMB = file.size / (1024 * 1024);
        if (fileSizeMB > maxSize) {
            return `Size can't be more than ${maxSize}MB`;
        }

        if (allowedTypes.length > 0 && !allowedTypes.includes(file.type)) {
            return `Invalid type. Allowed: ${allowedTypes.join(', ')}`;
        }

        return null;
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const selected = e.target.files?.[0] || null;

        if (selected) {
            const validationError = validateFile(selected);
            if (validationError) {
                setError?.(validationError);
                setFile(null);
                setPreview(null);
                return;
            }
        }

        setFile(selected);
        setError?.(undefined);

        if (selected && selected.type.startsWith("image")) {
            const reader = new FileReader();
            reader.onload = () => setPreview(reader.result as string);
            reader.readAsDataURL(selected);
        } else {
            setPreview(null);
        }
    };

    return (
        <div className="w-full mb-5">
            <label className="block mb-2 text-sm font-medium">
                {label}
            </label>

            <div
                className={`flex flex-col items-center justify-center w-full border-2 border-dashed rounded-lg p-4 
        cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors
        ${
                    error
                        ? "border-red-400 dark:border-red-500"
                        : "border-gray-300 dark:border-gray-600"
                }`}
            >
                <input
                    type="file"
                    name={name}
                    accept={accept}
                    onChange={handleChange}
                    className="hidden"
                    id={name}
                />
                <label htmlFor={name} className="flex flex-col items-center cursor-pointer">
                    {preview ? (
                        <img
                            src={preview}
                            alt="preview"
                            className="w-24 h-24 object-cover rounded-md mb-2"
                        />
                    ) : (
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="w-10 h-10 text-gray-400 dark:text-gray-500 mb-2"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth={1.5}
                                d="M12 4v16m8-8H4"
                            />
                        </svg>
                    )}
                    <span className="text-sm text-gray-600 dark:text-gray-300">
            {file ? file.name : "Оберіть файл"}
          </span>
                </label>
            </div>

            {error && <p className="text-sm text-red-600 dark:text-red-400 mt-1">{error}</p>}
        </div>
    );
};

export default FileUploadField;
