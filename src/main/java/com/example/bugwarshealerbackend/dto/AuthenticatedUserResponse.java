package com.example.bugwarshealerbackend.dto;

import lombok.Getter;
@Getter
public class AuthenticatedUserResponse extends UserLoginRequest {
    private final String token;
    public AuthenticatedUserResponse(Long id, String username, String passwordHash, String firstname, String lastname, String email, String token) {
        super(id, username, passwordHash, firstname, lastname, email);
        this.token = token;
    }

    public AuthenticatedUserResponse(Long id, String username, String passwordHash, String firstname, String lastname, String email) {
        this(id, username, passwordHash, firstname, lastname, email, null);
    }
}

