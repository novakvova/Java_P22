package org.example.data.dto.country;

import lombok.Data;

@Data
public class CountryItemDTO {
    private Long id;
    private String name;
    private String code;
    private String slug;
    private String image;
    private String dateCreated;
}
