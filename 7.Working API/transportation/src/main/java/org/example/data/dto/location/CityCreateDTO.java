package org.example.data.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CityCreateDTO {

    @NotBlank(message = "Ім'я обов'язкове")
    private String name;

    @NotBlank(message = "Slug обов'язковий")
    private String slug;

    private MultipartFile image;

    @NotNull(message = "Країна обов'язкова")
    private Long countryId;

    private int population;
    private String timezone;
    private String description;
    private String mainAirportCode;
    private double avgMealPrice;
    private double avgHotelPrice;
    private boolean hasRecreationalWater;

    private List<Long> descriptionImageIds;
}

