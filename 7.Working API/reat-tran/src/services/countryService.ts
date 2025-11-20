import {createApi} from "@reduxjs/toolkit/query/react";
import {createBaseQuery} from "../utils/createBaseQuery";
import type {ICountryItem} from "../types/location/ICountryItem.ts";
import type {ICountryCreate} from "../types/location/ICountryCreate.ts";
import { serialize } from "object-to-formdata";

export const countryService = createApi({
    reducerPath: 'countryService',
    baseQuery: createBaseQuery('countries'),
    tagTypes: ['Countries'],

    endpoints: (builder) => ({
        getCountries: builder.query<ICountryItem[], void>({
            query: () => {
                return {
                    url: '',
                    method: 'GET'
                };
            },
            providesTags: ["Countries"]
        }),

        createCountry: builder.mutation<ICountryItem, ICountryCreate>({
            query: (body) => {
                const formData = serialize(body, { indices: false });

                return {
                    url: "",
                    method: "POST",
                    body: formData,
                };
            },
            invalidatesTags: ["Countries"]
        })

    }),
});

export const {
    useGetCountriesQuery,
    useCreateCountryMutation
} = countryService;