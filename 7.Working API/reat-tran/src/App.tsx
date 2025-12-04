import './App.css'
import {Route, Routes} from "react-router";
import MainLayout from "./layout/Main/MainLayout.tsx";
import NotFoundPage from "./pages/common/NotFoundPage.tsx";
import UserHomePage from "./pages/user/UserHomePage";
import RegisterPage from "./pages/account/RegisterPage";
import CreateCountryPage from "./pages/country/CreateCountryPage";
import CreateCityPage from "./pages/city/CreateCityPage";
import CitiesListPage from "./pages/city/CitiesListPage";
import CityDetailPage from "./pages/city/CityDetailPage";

function App() {

    return (
        <>
            <Routes>
                <Route path="/" element={<MainLayout />}>
                    <Route index element={<UserHomePage/>} />

                    <Route path="/countries/create" element={<CreateCountryPage />} />

                    <Route path="cities">
                        <Route index element={<CitiesListPage/>} />
                        <Route path=":slug" element={<CityDetailPage/>} />
                        <Route path="create" element={<CreateCityPage />} />
                    </Route>

                    <Route path={"/register"} element={<RegisterPage/>} />
                </Route>

                <Route path="*" element={<NotFoundPage/>} />
            </Routes>
        </>
    )
}

export default App
