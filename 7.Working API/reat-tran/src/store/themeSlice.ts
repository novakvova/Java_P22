import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

export type ThemeType = "light" | "dark" | "pink" | "red" | "ukraine" | "dracula";

interface ThemeState {
    theme: ThemeType;
}

const savedTheme = (localStorage.getItem("theme") as ThemeType) || "light";

const initialState: ThemeState = {
    theme: savedTheme,
};

const themeSlice = createSlice({
    name: "theme",
    initialState,
    reducers: {
        setTheme: (state, action: PayloadAction<ThemeType>) => {
            state.theme = action.payload;
            localStorage.setItem("theme", action.payload);

        },
    },
});

export const { setTheme } = themeSlice.actions;
export default themeSlice.reducer;
