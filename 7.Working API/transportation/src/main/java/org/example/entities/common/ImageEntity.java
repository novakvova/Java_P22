package org.example.entities.common;

import jakarta.persistence.*;
import lombok.Data;
import org.example.entities.location.CityEntity;

@Entity
@Data
@Table(name = "images")
public class ImageEntity extends BaseEntity<Long> {
    @Column(nullable = false)
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;
}
