import { useState } from "react";
import { useNavigate } from "react-router";
import { useRegisterMutation } from "../../services/accountService.ts";
import type { IRegisterUser } from "../../types/account/IRegisterUser.ts";
import InputField from "../inputs/InputField.tsx";
import ImageUploader from "../uploaders/ImageUploader.tsx";
import BaseButton from "../inputs/BaseButton.tsx";
import type { UploadFile } from "antd";
import {parseServerValidationErrors} from "../../utils/parseServerValidationErrors.ts";

const RegisterForm: React.FC = () => {
    const navigate = useNavigate();
    const [register, { isLoading }] = useRegisterMutation();

    const [formValues, setFormValues] = useState<IRegisterUser>({
        name: "",
        lastName: "",
        phone: "",
        email: "",
        password: "",
        imageFile: null,
    });

    const [fileList, setFileList] = useState<UploadFile[]>([]);
    const [imageError, setImageError] = useState(false);
    const [formError, setFormError] = useState<string | null>(null);
    const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setFormError(null);
        setFieldErrors({});

        if (!fileList[0]?.originFileObj) {
            setImageError(true);
            return;
        }

        try {
            await register({ ...formValues, imageFile: fileList[0].originFileObj }).unwrap();
            navigate("/");
        } catch (err: any) {
            if (err?.data?.errors) {
                const { fieldErrors } = parseServerValidationErrors(err.data.errors);
                setFieldErrors(fieldErrors);
            } else {
                setFormError(err?.data?.message || "Помилка реєстрації");
            }
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <InputField
                    label="First name"
                    name="name"
                    placeholder="Pedro"
                    value={formValues.name}
                    onChange={handleChange}
                    error={fieldErrors.name}
                />
                <InputField
                    label="Last name"
                    name="lastName"
                    placeholder="Timchuk"
                    value={formValues.lastName}
                    onChange={handleChange}
                    error={fieldErrors.lastName}
                />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <InputField
                    label="Phone"
                    name="phone"
                    placeholder="+380..."
                    value={formValues.phone}
                    onChange={handleChange}
                    error={fieldErrors.phone}
                />
                <InputField
                    label="Email"
                    name="email"
                    placeholder="pedro@example.com"
                    value={formValues.email}
                    onChange={handleChange}
                    error={fieldErrors.email}
                />
            </div>

            <InputField
                label="Password"
                type="password"
                name="password"
                placeholder="********"
                value={formValues.password}
                onChange={handleChange}
                error={fieldErrors.password}
            />

            <div className="w-full text-center">
                <ImageUploader
                    fileList={fileList}
                    setFileList={setFileList}
                    imageError={imageError}
                    setImageError={setImageError}
                />
                {imageError && <p className="text-red-500 text-sm mt-1">Image is required</p>}
            </div>

            {formError && <p className="text-red-500 text-sm">{formError}</p>}

            <BaseButton
                type="submit"
                className="w-full rounded-xl border border-blue-300 font-medium py-2"
            >
                {isLoading ? "Loading..." : "Register"}
            </BaseButton>
        </form>
    );
};

export default RegisterForm;
