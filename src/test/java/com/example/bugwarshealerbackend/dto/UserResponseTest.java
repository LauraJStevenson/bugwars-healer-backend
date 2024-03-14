package com.example.bugwarshealerbackend.dto;

import com.example.bugwarshealerbackend.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testGetUser() {
        User user = new User(1L, "TestUser", "Test", "User", "password", "test@test.com", null, new ArrayList<>(), 0, false);
        UserResponse userResponse = new UserResponse(user);

        //Act
        User retrieveUser = userResponse.getUser();

        //Assert
        assertEquals(user, retrieveUser);

    }

    @Test
    void testSetUser() {
        User user = new User(1L, "TestUser", "Test", "User", "password", "test@test.com", null, new ArrayList<>(), 0, false);
        User newUser = new User (2L, "TestUser2", "Test2", "User2", "password2", "tes2t@test.com", null, new ArrayList<>(), 0, false);
        UserResponse userResponse = new UserResponse(user);

        userResponse.setUser(newUser);

        assertEquals(newUser, userResponse.getUser());

    }
}