import {type ChangeEvent, type FormEvent, useState} from "react";
// import { useRegisterMutation } from "../../../api/userService";

const RegisterPage: React.FC = () => {
    // const [register, { isLoading }] = useRegisterMutation();

    const [form, setForm] = useState({
        lastName: "",
        name: "",
        email: "",
        phone: "",
        password: "",
        confirmPassword: "",
    });

    const [message, setMessage] = useState<string|null>(null);
    const [error, setError] = useState<string|null>(null);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (form.password !== form.confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {
            setError(null);
            // const res = await register({
            //     lastName: form.lastName,
            //     name: form.name,
            //     email: form.email,
            //     phone: form.phone,
            //     password: form.password,
            // }).unwrap();

            setMessage("Registered successfully!");
        } catch {
            setError("Registration failed");
        }
    };


    return (
        <div className="p-5 min-h-screen flex items-center justify-center">
            <div className="max-w-[900px] w-full rounded-2xl overflow-hidden shadow-[0_4px_20px_rgba(0,0,0,0.1)] dark:shadow-gray-800">
                <div className="grid md:grid-cols-2">

                    <div className="bg-blue-600 p-10 hidden md:flex flex-col justify-center">
                        <h2 className="text-white text-3xl font-semibold mb-4">Ласкаво просимо!</h2>
                        <p className="text-white text-lg">
                            Зареєструйтесь, щоб розпочати.
                        </p>
                    </div>

                    <div className="p-6 md:p-10 flex flex-col justify-center">
                        <div className="text-center mb-6">
                            <h3 className="text-2xl font-semibold mb-1">Реєстрація</h3>
                            <p className="">
                                Введіть свої дані, щоб створити акаунт
                            </p>
                        </div>

                        <form onSubmit={handleSubmit} className="space-y-5">

                            <div>
                                <label className="block mb-1 font-medium">
                                    Прізвище
                                </label>
                                <input
                                    type="text"
                                    name="lastName"
                                    value={form.lastName}
                                    onChange={handleChange}
                                    className="
                                        w-full px-4 py-2 rounded-lg border
                                        focus:ring-2 focus:ring-blue-500
                                        transition
                                    "
                                    placeholder="Введіть прізвище"
                                    required
                                />
                            </div>

                            <div>
                                <label className="block mb-1 font-medium ">
                                    Ім’я
                                </label>
                                <input
                                    type="text"
                                    name="name"
                                    value={form.name}
                                    onChange={handleChange}

                                    className="
                                        w-full px-4 py-2 rounded-lg border
                                        focus:ring-2 focus:ring-blue-500
                                        transition
                                    "
                                    placeholder="Введіть ім’я"
                                    required
                                />
                            </div>

                            <div>
                                <label className="block mb-1 font-medium">
                                    Email
                                </label>
                                <input
                                    type="email"
                                    name="email"
                                    value={form.email}
                                    onChange={handleChange}
                                    className="
                                        w-full px-4 py-2 rounded-lg border
                                        focus:ring-2 focus:ring-blue-500
                                        transition
                                    "
                                    placeholder="example@gmail.com"
                                    required
                                />
                            </div>

                            <div>
                                <label className="block mb-1 font-medium">
                                    Телефон
                                </label>
                                <input
                                    type="tel"
                                    name="phone"
                                    value={form.phone}
                                    onChange={handleChange}
                                    className="
                                        w-full px-4 py-2 rounded-lg border
                                        focus:ring-2 focus:ring-blue-500
                                        transition
                                    "
                                    placeholder="+380..."
                                    required
                                />
                            </div>

                            <div>
                                <label className="block mb-1 font-medium">
                                    Пароль
                                </label>
                                <input
                                    type="password"
                                    name="password"
                                    value={form.password}
                                    onChange={handleChange}
                                    className="
                                        w-full px-4 py-2 rounded-lg border
                                        focus:ring-2 focus:ring-blue-500
                                        transition
                                    "
                                    placeholder="Введіть пароль"
                                    required
                                />
                            </div>

                            <div>
                                <label className="block mb-1 font-medium">
                                    Confirm Пароль
                                </label>
                                <input
                                    type="password"
                                    name="confirmPassword"
                                    value={form.confirmPassword}
                                    onChange={handleChange}
                                    className="
                                        w-full px-4 py-2 rounded-lg border
                                        focus:ring-2 focus:ring-blue-500
                                        transition
                                    "
                                    placeholder="Confirmaite пароль (:"
                                    required
                                />
                            </div>

                            {error && (
                                <div className="text-sm">{error}</div>
                            )}

                            {message && (
                                <div className="text-sm">{message}</div>
                            )}

                            <button
                                type="submit"
                                className="
                                    w-full bg-blue-600 text-white py-3
                                    rounded-lg font-semibold
                                    transition hover:bg-blue-700 active:scale-95
                                    shadow-md
                                "
                            >
                                Зареєструватися
                            </button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;
