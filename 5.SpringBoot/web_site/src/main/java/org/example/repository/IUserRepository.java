package org.example.repository;

import org.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}
