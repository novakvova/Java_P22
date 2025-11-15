package org.example.mappers;

import org.example.data.data_transfer_objects.product.CategoryCreateDTO;
import org.example.data.data_transfer_objects.product.CategoryItemDTO;

import org.example.data.seed.CategorySeed;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryItemDTO toDto(CategoryEntity category);

    CategoryEntity toEntity(CategorySeed category);
    CategoryEntity fromCreateDTO(CategoryCreateDTO dto);
}
