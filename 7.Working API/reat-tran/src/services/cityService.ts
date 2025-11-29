import {createApi} from "@reduxjs/toolkit/query/react";
import {createBaseQuery} from "../utils/createBaseQuery";
import { serialize } from "object-to-formdata";
import type {ICityItem} from "../types/location/ICityItem.ts";
import type {ICityCreate} from "../types/location/ICityCreate.ts";

export const cityService = createApi({
    reducerPath: 'cityService',
    baseQuery: createBaseQuery('cities'),
    tagTypes: ['Cities'],

    endpoints: (builder) => ({
        getCities: builder.query<ICityItem[], void>({
            query: () => {
                return {
                    url: '',
                    method: 'GET'
                };
            },
            providesTags: ["Cities"]
        }),

        createCity: builder.mutation<ICityItem, ICityCreate>({
            query: (body) => {
                const formData = serialize(body, { indices: false });

                return {
                    url: "",
                    method: "POST",
                    body: formData,
                };
            },
            invalidatesTags: ["Cities"]
        })

    }),
});

export const {
    useGetCitiesQuery,
    useCreateCityMutation
} = cityService;