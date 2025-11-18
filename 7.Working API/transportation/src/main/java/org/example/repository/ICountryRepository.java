package org.example.repository;

import org.example.entities.location.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);
}