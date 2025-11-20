import { Link } from "react-router";

const NotFoundPage: React.FC = () => {
    return (
        <div className="min-h-screen flex flex-col justify-center items-center bg-white text-gray-900 dark:text-white p-6">
            <h1 className="text-6xl md:text-8xl font-bold text-blue-500 dark:text-blue-400 mb-4">404</h1>
            <h2 className="text-2xl md:text-3xl font-semibold mb-18">Сторінку не знайдено</h2>

            <div className="relative w-full max-w-lg">
                <img
                    src="https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExMzRvbXpwbmhuN2M2d3Fuem92MXFxNXp4dWtwMHZ5am9vcHQ0aXliNyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/ule4vhcY1xEKQ/giphy.gif"
                    alt="Coding cat"
                    className="w-64 mx-auto mb-4 animate-bounce"
                />

            </div>

            <Link
                to="/"
                className="mt-8 bg-blue-500 dark:bg-blue-600 hover:bg-blue-700 dark:hover:bg-blue-700 transition text-white px-6 py-3 rounded-full text-lg font-medium"
            >
                Повернутися додому
            </Link>
        </div>
    );
};

export default NotFoundPage;
