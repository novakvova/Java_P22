package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

@Entity
@Setter
@Getter
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 255)
    private String slug;

    @Column(nullable = true, length = 200)
    private String image;

    @Column
    private boolean isDeleted = false;
}
