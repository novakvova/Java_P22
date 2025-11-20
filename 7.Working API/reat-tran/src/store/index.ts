import {configureStore} from "@reduxjs/toolkit";
import {countryService} from "../services/countryService.ts";
import {type TypedUseSelectorHook, useDispatch, useSelector} from "react-redux";

export const store = configureStore({
    reducer: {
        [countryService.reducerPath]: countryService.reducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
        .concat(countryService.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;