package org.example.repository;


import org.example.entities.common.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByCityIsNull();
}