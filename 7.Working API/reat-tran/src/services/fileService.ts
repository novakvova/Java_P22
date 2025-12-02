import {createApi} from "@reduxjs/toolkit/query/react";
import {createBaseQuery} from "../utils/createBaseQuery";
import { serialize } from "object-to-formdata";
import type {ISaveImageFile} from "../types/file/ISaveImageFile.ts";
import type {ISavedImage} from "../types/file/ISavedImage.ts";

export const fileService = createApi({
    reducerPath: 'fileService',
    baseQuery: createBaseQuery('file'),
    tagTypes: ['Files'],

    endpoints: (builder) => ({
        saveImage: builder.mutation<ISavedImage, ISaveImageFile>({
            query: (body) => {
                const formData = serialize(body);

                return {
                    url: '/saveImage',
                    method: "POST",
                    body: formData
                };
            },
            invalidatesTags: ["Files"]
        })

    }),
});

export const {
    useSaveImageMutation
} = fileService;