package com.example.bugwarshealerbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Size (min = 3, max = 15, message = "Username length must be between 3 and 15 characters.")
        @Column(name= "username", nullable = false, unique = true)
        private String username;

        @Size (min = 2, max = 15, message = "First name length must be between 2 and 15 characters.")
        @Column(name = "firstname", nullable = false)
        private String firstname;

        @Size (min = 2, max = 15, message = "Last name length must be between 2 and 15 characters.")
        @Column(name = "lastname", nullable = false)
        private String lastname;

        @Column(name = "password_hash", nullable = false)
        private String password;

        @Size(min = 5, max = 50, message = "Email length must be between 5 and 50 characters.")
        @Email(message = "Email should be valid.")
        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @Column(name = "refresh_token")
        private String refreshToken;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<Script> scripts = new ArrayList<>();

        @Column(name = "counter", nullable = true)
        private int counter;

        @Column(name = "activated", nullable = true)
        private boolean activated;
}
