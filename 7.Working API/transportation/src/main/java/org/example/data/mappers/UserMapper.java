package org.example.data.mappers;

import org.example.data.dto.account.RegisterUserDTO;
import org.example.data.dto.account.UserItemDTO;
import org.example.entities.account.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserItemDTO toDto(UserEntity category);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity fromRegisterDTO(RegisterUserDTO dto);
}