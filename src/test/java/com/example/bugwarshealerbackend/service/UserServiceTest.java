package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.dto.UserResponse;
import com.example.bugwarshealerbackend.exceptions.ResourceNotFoundException;
import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersTest() {
        // Arrange
        User user = new User(1L, "TestUser", "Test", "User", "password", "test@test.com", null, new ArrayList<>(), 0, false);
        List<User> expectedUsers = List.of(user);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdTest() throws ResourceNotFoundException {
        // Arrange
        User expectedUser = new User(1L, "TestUser", "Test", "User", "password", "test@test.com", null, new ArrayList<>(), 0, false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));

        // Act
        UserResponse response = userService.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(expectedUser, response.getUser());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void createUserTest() {
        // Arrange
        User newUser = new User(0L, "newUser", "New", "User", "P@ssw0rd", "newuser@test.com", null, new ArrayList<>(), 0, false);
        when(passwordEncoder.encode("P@ssw0rd")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        UserResponse createdUser = userService.createUser(newUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals("newUser", createdUser.getUser().getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserTest() throws ResourceNotFoundException {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        User updatedUserDetails = new User();
        updatedUserDetails.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUserDetails);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        UserResponse result = userService.updateUser(userId, updatedUserDetails);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals(userId, result.getUser().getId());
    }



    @Test
    void deleteUserTest() throws ResourceNotFoundException {
        // Arrange
        User userToDelete = new User(1L, "deleteUser", "Delete", "User", "password", "deleteuser@test.com", null, new ArrayList<>(), 0, false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userToDelete));

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).delete(userToDelete);
    }
}
