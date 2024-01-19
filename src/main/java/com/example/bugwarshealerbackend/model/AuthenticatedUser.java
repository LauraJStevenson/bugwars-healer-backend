package com.example.bugwarshealerbackend.model;

import lombok.Getter;

@Getter
public class AuthenticatedUser extends UserLogin{

    private final String token;

    public AuthenticatedUser(String username, String passwordHash, String token) {

        super(username, passwordHash);
        this.token = token;
    }
}

