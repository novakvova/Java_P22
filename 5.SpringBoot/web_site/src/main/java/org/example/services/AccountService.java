package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.UserEntity;
import org.example.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password, String imageFilename) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setImage(imageFilename);

        userRepository.save(user);
        return true;
    }

    public List<UserEntity> GetAllUsers() {
        return userRepository.findAll();
    }
}
