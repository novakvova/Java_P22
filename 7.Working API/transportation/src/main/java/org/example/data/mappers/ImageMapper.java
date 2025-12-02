package org.example.data.mappers;

import org.example.data.dto.file.SavedImageDTO;
import org.example.entities.common.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(source = "createdAt", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    SavedImageDTO toDto(ImageEntity entity);
}