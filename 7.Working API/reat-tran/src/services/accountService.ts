import { createApi } from "@reduxjs/toolkit/query/react";
import { createBaseQuery } from "../utils/createBaseQuery";
import type { IUserItem } from "../types/account/IUserItem";
import type { IRegisterUser } from "../types/account/IRegisterUser";
import { serialize } from "object-to-formdata";

export const accountService = createApi({
    reducerPath: "accountService",
    baseQuery: createBaseQuery("account"),
    endpoints: (builder) => ({
        register: builder.mutation<IUserItem, IRegisterUser>({
            query: (body) => {
                const formData = serialize(body, { indices: false });
                return {
                    url: "/register",
                    method: "POST",
                    body: formData,
                };
            },
        }),
    }),
});


export const { useRegisterMutation } = accountService;
