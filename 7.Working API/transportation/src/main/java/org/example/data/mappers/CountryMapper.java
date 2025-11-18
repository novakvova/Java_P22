package org.example.data.mappers;

import org.example.data.dto.country.CountryCreateDTO;
import org.example.data.dto.country.CountryItemDTO;
import org.example.entities.location.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    @Mapping(source = "createdAt", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CountryItemDTO toDto(CountryEntity country);
    @Mapping(target = "image", ignore = true)
    CountryEntity fromCreateDTO(CountryCreateDTO dto);
}
