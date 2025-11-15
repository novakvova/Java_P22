package org.example.data.data_transfer_objects.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDTO {
    private String name;
    private String slug;
    private String description;
    private Double minPrice;
    private Double maxPrice;
    private Long categoryId;
}