package com.example.bugwarshealerbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(name= "username", nullable = false)
        @Size(min = 3, max = 15)
        private String username;
        @Size (min = 3, max = 15)
        @Column(name = "firstname", nullable = false)
        private String firstname;
        @Size (min = 3, max = 15)
        @Column(name = "lastname", nullable = false)
        private String lastname;
        @Column(name = "password_hash", nullable = false)
        private String password;
        @Size (max = 40)
        @Column(name = "email", nullable = false)
        private String email;
        @Column(name = "counter", nullable = true)
        private int counter;
        @Column(name = "activated", nullable = true)
        private boolean activated;
}
