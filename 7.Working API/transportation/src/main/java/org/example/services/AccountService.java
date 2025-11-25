package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.dto.account.RegisterUserDTO;
import org.example.data.dto.account.UserItemDTO;
import org.example.data.mappers.CountryMapper;
import org.example.data.mappers.UserMapper;
import org.example.entities.account.RoleEntity;
import org.example.entities.account.UserEntity;
import org.example.repository.IRoleRepository;
import org.example.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;
    private final FileService fileService;
    private final UserMapper userMapper;

    public UserItemDTO registerUser(RegisterUserDTO dto) {
        UserEntity user = userMapper.fromRegisterDTO(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        var file = dto.getImageFile();
        if (file != null) {
            String fileName = fileService.load(file);
            user.setImage(fileName);
        }

        Optional<RoleEntity> userRoleOpt = roleRepository.findByName("User");

        if (userRoleOpt.isPresent()) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(userRoleOpt.get());
            user.setRoles(roles);
        }

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public List<UserEntity> GetAllUsers() {
        return userRepository.findAll();
    }
}