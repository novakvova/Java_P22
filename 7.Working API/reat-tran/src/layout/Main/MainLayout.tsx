import { Outlet } from "react-router";
import MainHeader from "./MainHeader.tsx";
import MainFooter from "./MainFooter.tsx";

export const MainLayout = () => {
    return (
        <div className={"mx-auto bg-[var(--bg)] text-[var(--fg)]"}>
            <MainHeader />

            <main className="py-5 transition-colors">
                <Outlet />
            </main>

            <MainFooter />
        </div>
    );
};

export default MainLayout;
