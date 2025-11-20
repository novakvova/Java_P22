import { useGetCountriesQuery } from "../../../services/countryService";
import CountryCard from "../../../components/country/CountryCard.tsx";

const UserHomePage: React.FC = () => {
    const { data: countries, isLoading } = useGetCountriesQuery();

    return (
        <div className="w-full flex flex-col">

            <section className="bg-gradient-to-r from-blue-800 to-blue-500 text-white py-28 px-4 text-center shadow-xl">
                <h1 className="text-5xl md:text-7xl font-extrabold mb-6 drop-shadow-xl">
                    Пасажирські перевезення
                </h1>
                <p className="text-lg md:text-2xl mb-10 opacity-90">
                    Комфортні поїздки по Україні та Європі
                </p>
                <button className="
                    bg-white text-blue-700 font-semibold px-10 py-4
                    rounded-xl shadow-lg transition
                    hover:bg-blue-50 hover:shadow-2xl active:scale-95
                ">
                    Замовити поїздку
                </button>
            </section>

            <section className="max-w-7xl w-full mx-auto py-16 px-4">
                <h2 className="text-4xl font-bold text-center mb-12 text-blue-700 drop-shadow-sm">
                    Популярні напрямки
                </h2>

                {isLoading && (
                    <div className="text-center text-lg text-blue-600">
                        Завантаження...
                    </div>
                )}

                <div className="
                    grid gap-10
                    grid-cols-1 sm:grid-cols-2 md:grid-cols-3
                ">
                    {countries?.map(country => (
                        <CountryCard key={country.code} country={country} />
                    ))}
                </div>
            </section>

        </div>
    );
};

export default UserHomePage;
