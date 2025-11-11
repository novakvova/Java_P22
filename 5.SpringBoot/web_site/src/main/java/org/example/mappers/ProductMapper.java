package org.example.mappers;

import org.example.data.seed.ProductSeed;
import org.example.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "images", ignore = true)
    ProductEntity toEntity(ProductSeed product);
}
