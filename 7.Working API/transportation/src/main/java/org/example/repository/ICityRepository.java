package org.example.repository;

import org.example.entities.location.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);
}