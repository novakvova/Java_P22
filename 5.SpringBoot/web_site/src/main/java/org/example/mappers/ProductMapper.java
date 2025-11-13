package org.example.mappers;

import org.example.data.data_transfer_objects.product.ProductCreateDTO;
import org.example.data.data_transfer_objects.product.ProductItemDTO;
import org.example.data.data_transfer_objects.product.ProductListItemDTO;
import org.example.data.seed.ProductSeed;
import org.example.entities.CategoryEntity;
import org.example.entities.ProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "images", ignore = true)
    ProductEntity toEntity(ProductSeed product);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "images", expression = "java(entity.getImages() == null ? null : entity.getImages().stream().map(org.example.entities.ImageEntity::getName).toList())")
    ProductItemDTO toDTO(ProductEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "deleted", constant = "false")
    ProductEntity fromCreateDTO(ProductCreateDTO dto,
                                @Context CategoryEntity category);

    @AfterMapping
    default void setExtraFields(@MappingTarget ProductEntity entity,
                                ProductCreateDTO dto,
                                @Context CategoryEntity category) {
        entity.setCategory(category);
    }

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "image", expression = "java(entity.getImages() != null && !entity.getImages().isEmpty() ? entity.getImages().get(0).getName() : null)")
    ProductListItemDTO toListItemDTO(ProductEntity entity);
}
