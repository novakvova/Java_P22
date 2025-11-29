export interface ICityCreate {
    name: string;
    slug: string;
    image?: File | null;
    countryId: number;
    population?: number;
    timezone?: string;
    description?: string;
    mainAirportCode?: string;
    avgMealPrice?: number;
    avgHotelPrice?: number;
    hasRecreationalWater?: boolean;
}
