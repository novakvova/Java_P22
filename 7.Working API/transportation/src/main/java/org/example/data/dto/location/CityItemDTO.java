package org.example.data.dto.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityItemDTO {

    private Long id;

    private String name;

    private String slug;

    private String image;

    private Long countryId;

    private String countryName;

    private int population;

    private String timezone;

    private String description;

    private String mainAirportCode;

    private double avgMealPrice;

    private double avgHotelPrice;

    private boolean hasRecreationalWater;

    private String dateCreated;
}
