export interface ICountryCreate {
    name: string;
    code: string;
    slug: string;
    image?: File | null;
}
