import {configureStore} from "@reduxjs/toolkit";
import {countryService} from "../services/countryService.ts";
import {type TypedUseSelectorHook, useDispatch, useSelector} from "react-redux";
import themeReducer from "./themeSlice";
import {accountService} from "../services/accountService.ts";
import {cityService} from "../services/cityService.ts";
import {fileService} from "../services/fileService.ts";

export const store = configureStore({
    reducer: {
        [countryService.reducerPath]: countryService.reducer,
        [accountService.reducerPath]: accountService.reducer,
        [cityService.reducerPath]: cityService.reducer,
        [fileService.reducerPath]: fileService.reducer,
        theme: themeReducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
        .concat(countryService.middleware,
            accountService.middleware,
            cityService.middleware,
            fileService.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;