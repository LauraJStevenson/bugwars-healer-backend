package com.example.bugwarshealerbackend.model;

import jakarta.persistence.*;
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
        private String username;
        @Column(name = "firstname", nullable = false)
        private String firstname;
        @Column(name = "lastname", nullable = false)
        private String lastname;
        @Column(name = "password_hash", nullable = false)
        private String password;
        @Column(name = "email", nullable = false)
        private String email;
        @Column(name = "counter", nullable = true)
        private int counter;
        @Column(name = "activated", nullable = true)
        private boolean activated;
        @Column(name="token", nullable = true)
        private String token;

}
