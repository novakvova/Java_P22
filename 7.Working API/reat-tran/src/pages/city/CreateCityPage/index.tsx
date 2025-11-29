import React from "react";
import CreateCityForm from "../../../components/forms/CreateCityForm.tsx";

const CreateCityPage: React.FC = () => {
    return (
        <div className="min-h-screen w-full flex items-center justify-center p-6">
            <div className="max-w-[900px] w-full rounded-2xl">
                <h1 className="text-2xl font-semibold mb-6">
                    Створення міста
                </h1>
                <CreateCityForm />
            </div>
        </div>
    );
};

export default CreateCityPage;
