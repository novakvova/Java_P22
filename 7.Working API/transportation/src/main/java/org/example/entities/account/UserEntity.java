package org.example.entities.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Invalid email")
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
