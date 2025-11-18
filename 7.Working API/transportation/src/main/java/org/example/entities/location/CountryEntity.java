package org.example.entities.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.example.entities.common.BaseEntity;

@Data
@Entity
@Table(name = "countries")
public class CountryEntity extends BaseEntity<Long> {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String slug;

    private String image;
}
