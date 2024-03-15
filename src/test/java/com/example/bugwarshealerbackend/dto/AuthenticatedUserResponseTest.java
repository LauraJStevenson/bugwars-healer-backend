package com.example.bugwarshealerbackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatedUserResponseTest {
    @Test
    public void testConstructorWithToken() {
        // Arrange
        Long id = 1L;
        String username = "TestUser";
        String passwordHash = "hashed_password";
        String firstname = "user";
        String lastname = "user";
        String email = "user@mail.com";
        String token = "sample_token";

        // Act
        AuthenticatedUserResponse response = new AuthenticatedUserResponse(id, username, passwordHash, firstname, lastname, email, token);

        // Assert
        assertEquals(id, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals(firstname, response.getFirstname());
        assertEquals(lastname, response.getLastname());
        assertEquals(email, response.getEmail());
        assertEquals(token, response.getToken());
    }



}