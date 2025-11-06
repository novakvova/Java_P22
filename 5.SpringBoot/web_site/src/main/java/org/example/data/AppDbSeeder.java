package org.example.data;

import lombok.RequiredArgsConstructor;
import org.example.data.constants.RolesConstants;
import org.example.entities.RoleEntity;
import org.example.repository.IRoleRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppDbSeeder {

    private final IRoleRepository roleRepository;

    @PostConstruct
    public void seedData() {
        seedRoles();
    }

    private void seedRoles() {
        List<String> roles = RolesConstants.Roles;

        for (String roleName : roles) {
            boolean exists = roleRepository.findByName(roleName).isPresent();
            if (!exists) {
                RoleEntity role = new RoleEntity();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Додано роль: " + roleName);
            } else {
                System.out.println("Роль уже існує: " + roleName);
            }
        }
    }
}
