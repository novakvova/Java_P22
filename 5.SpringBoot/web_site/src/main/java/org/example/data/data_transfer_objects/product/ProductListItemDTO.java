package org.example.data.data_transfer_objects.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListItemDTO {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private String slug;
    private String image;
}
