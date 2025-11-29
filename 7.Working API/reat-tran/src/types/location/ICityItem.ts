export interface ICityItem {
    id: number;
    name: string;
    slug: string;
    image?: string;
    countryId: number;
    countryName: string;
    population?: number;
    timezone?: string;
    description?: string;
    mainAirportCode?: string;
    avgMealPrice?: number;
    avgHotelPrice?: number;
    hasRecreationalWater?: boolean;
    dateCreated?: string;
}
