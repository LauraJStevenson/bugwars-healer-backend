package com.example.bugwarshealerbackend.model;

import lombok.Getter;

@Getter
public class AuthenticatedUser extends UserLogin{

    private final String token;

    public AuthenticatedUser(Long id, String username, String passwordHash, String firstname, String lastname, String email, String token) {

        super(id, username, passwordHash, firstname, lastname, email);
        this.token = token;
    }
}

