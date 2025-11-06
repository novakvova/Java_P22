package org.example.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.entities.RoleEntity;
import org.example.entities.UserEntity;
import org.example.repository.IRoleRepository;
import org.example.repository.IUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    public boolean registerUser(String username, String password, String imageFilename,
                                HttpServletRequest request) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setImage(imageFilename);

        Optional<RoleEntity> userRoleOpt = roleRepository.findByName("User");

        if (userRoleOpt.isPresent()) {
            Set<RoleEntity> roles = new HashSet<>();
            roles.add(userRoleOpt.get());
            user.setRoles(roles);
        }

        userRepository.save(user);

        //щоб була авторизація після реєстрації
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        return true;
    }

    public List<UserEntity> GetAllUsers() {
        return userRepository.findAll();
    }
}
