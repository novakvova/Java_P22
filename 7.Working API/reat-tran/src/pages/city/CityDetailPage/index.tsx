import React from "react";
import { useParams } from "react-router-dom";
import { useGetBySlugQuery } from "../../../services/cityService.ts";
import { APP_ENV } from "../../../env";

const CityDetailPage: React.FC = () => {
    const { slug } = useParams<{ slug: string }>();
    const { data: city, isLoading } = useGetBySlugQuery(slug || '');

    if (isLoading) return <div className="text-center mt-20">Завантаження...</div>;
    if (!city) return <div className="text-center mt-20">Місто не знайдено</div>;

    return (
        <div className="max-w-5xl mx-auto py-16 px-4 transition-colors">
            <h1 className="text-5xl font-bold mb-6">{city.name}</h1>

            {city.image && (
                <img
                    src={`${APP_ENV.IMAGE_BASE_URL}large/${city.image}`}
                    alt={city.name}
                    className="w-full h-96 object-cover rounded-xl mb-8"
                />
            )}

            <div className="space-y-4 text-lg">
                <p><strong>Країна:</strong> {city.countryName}</p>
                {city.population && <p><strong>Населення:</strong> {city.population.toLocaleString()}</p>}
                {city.timezone && <p><strong>Часовий пояс:</strong> {city.timezone}</p>}
                {city.mainAirportCode && <p><strong>Головний аеропорт:</strong> {city.mainAirportCode}</p>}
                {city.avgMealPrice && <p><strong>Середня ціна страви:</strong> {city.avgMealPrice} €</p>}
                {city.avgHotelPrice && <p><strong>Середня ціна готелю:</strong> {city.avgHotelPrice} €</p>}
                {city.hasRecreationalWater !== undefined && (
                    <p><strong>Є вода для відпочинку:</strong> {city.hasRecreationalWater ? 'Так' : 'Ні'}</p>
                )}

                {city.description && (
                    <div>
                        <strong>Опис:</strong>
                        <div
                            className="mt-2 space-y-4"
                            dangerouslySetInnerHTML={{ __html: city.description }}
                        />
                    </div>
                )}
            </div>
        </div>
    );
};

export default CityDetailPage;
