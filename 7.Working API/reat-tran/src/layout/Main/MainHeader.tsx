import {Link} from "react-router";
import {type RootState, useAppDispatch, useAppSelector} from "../../store";
import {setTheme, type ThemeType} from "../../store/themeSlice.ts";
import changeTheme from "../../utils/changeTheme.ts";

export const MainHeader = () => {

    const dispatch = useAppDispatch();
    const theme = useAppSelector((state: RootState) => state.theme.theme);

    changeTheme(theme);

    const handleChangeTheme = (e: React.ChangeEvent<HTMLSelectElement>) => {
        dispatch(setTheme(e.target.value as ThemeType));
    };

    return (
        <>
            <nav className="bg-neutral-primary w-full border-b border-default">
                <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                    <Link to={"/"} className="flex items-center space-x-3 rtl:space-x-reverse">
                        <img src="https://flowbite.com/docs/images/logo.svg" className="h-7" alt="Flowbite Logo"/>
                        <span
                            className="self-center text-xl text-heading font-semibold whitespace-nowrap">Bogdan-bus</span>
                    </Link>

                    <select
                        value={theme}
                        onChange={handleChangeTheme}
                        className="

                        px-3 py-1.5 rounded-lg text-sm font-medium
                        bg-neutral-secondary-soft
                        border border-neutral-tertiary
                        focus:outline-none focus:ring-2 focus:ring-blue-500
                        hover:bg-neutral-secondary
                        transition
                      "
                    >
                        <option value="light">Light</option>
                        <option value="dark">Dark</option>
                        <option value="pink">Pink</option>
                        <option value="ukraine">Ukraine</option>
                        <option value="dracula">Lavender</option>
                    </select>

                    <button data-collapse-toggle="navbar-default" type="button"
                            className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-body rounded-base md:hidden hover:bg-neutral-secondary-soft hover:text-heading focus:outline-none focus:ring-2 focus:ring-neutral-tertiary"
                            aria-controls="navbar-default" aria-expanded="false">
                        <span className={"sr-only"}>Open main menu</span>
                        <svg className="w-6 h-6" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24"
                             height="24" fill="none" viewBox="0 0 24 24">
                            <path stroke="currentColor" strokeLinecap="round" strokeWidth="2"
                                  d="M5 7h14M5 12h14M5 17h14"/>
                        </svg>
                    </button>
                    <div className="hidden w-full md:block md:w-auto" id="navbar-default">
                        <ul className="font-medium flex flex-col p-4 md:p-0 mt-4 border border-default rounded-base bg-neutral-secondary-soft md:flex-row md:space-x-8 rtl:space-x-reverse md:mt-0 md:border-0 md:bg-neutral-primary">
                            <li>
                                <Link to="/countries/create"
                                      className="block py-2 px-3 text-heading rounded hover:bg-neutral-tertiary md:hover:bg-transparent md:hover:text-fg-brand md:p-0">
                                    Add Country
                                </Link>
                            </li>

                            <li>
                                <Link to={"/register"}
                                      className="block py-2 px-3 bg-brand rounded md:bg-transparent md:text-fg-brand md:p-0"
                                      aria-current="page">Register</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default MainHeader;