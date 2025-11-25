// pages/RegisterPage.tsx
import React from "react";
import RegisterForm from "../../../components/forms/RegisterForm.tsx";

const RegisterPage: React.FC = () => {
    return (
        <div className="p-5 min-h-screen flex items-center justify-center">
            <div className="max-w-[900px] w-full rounded-2xl overflow-hidden shadow-[0_4px_20px_rgba(0,0,0,0.1)] dark:shadow-gray-800">
                <div className="grid md:grid-cols-2">
                    <div className="bg-blue-600 p-10 hidden md:flex flex-col justify-center text-white">
                        <h2 className="text-3xl font-semibold mb-4">Ласкаво просимо!</h2>
                        <p className="text-lg">Зареєструйтесь, щоб розпочати.</p>
                    </div>

                    <div className="p-6 md:p-10 flex flex-col justify-center">
                        <div className="text-center mb-6">
                            <h3 className="text-2xl font-semibold mb-1">Реєстрація</h3>
                            <p>Введіть свої дані, щоб створити акаунт</p>
                        </div>

                        <RegisterForm />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;
