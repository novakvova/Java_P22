import React from "react";
import { useGetCitiesQuery } from "../../../services/cityService";
import CityCard from "../../../components/city/CityCard";

const CitiesPage: React.FC = () => {
    const { data: cities, isLoading } = useGetCitiesQuery();

    return (
        <div className="w-full flex flex-col transition-colors">

            <section className="
                bg-gradient-to-r
                from-green-700 to-green-500
                dark:from-green-900 dark:to-green-950
                text-white py-28 px-4 text-center shadow-xl
                transition-colors
            ">
                <h1 className="text-5xl md:text-7xl font-extrabold mb-6 drop-shadow-xl">
                    Популярні міста
                </h1>
                <p className="text-lg md:text-2xl mb-10 opacity-90">
                    Виберіть місто для перегляду детальної інформації
                </p>
            </section>

            <section className="max-w-7xl w-full mx-auto py-16 px-4 transition-colors">
                <h2 className="
                    text-4xl font-bold text-center mb-12
                    text-green-700 dark:text-green-400
                    drop-shadow-sm transition-colors
                ">
                    Напрямки
                </h2>

                {isLoading && (
                    <div className="text-center text-lg text-green-600 dark:text-green-400">
                        Завантаження...
                    </div>
                )}

                <div className="grid gap-10 grid-cols-1 sm:grid-cols-2 md:grid-cols-3">
                    {cities?.map(city => (
                        <CityCard key={city.id} city={city} />
                    ))}
                </div>
            </section>
        </div>
    );
};

export default CitiesPage;
