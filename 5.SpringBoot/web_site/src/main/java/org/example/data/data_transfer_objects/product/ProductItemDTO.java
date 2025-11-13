package org.example.data.data_transfer_objects.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductItemDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;

    private List<String> images;

    private boolean isDeleted;
    private String categoryName;
    private Long categoryId;
}
