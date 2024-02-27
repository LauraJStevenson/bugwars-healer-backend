package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.jpa.UserRepository;
import com.example.bugwarshealerbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenRefreshServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TokenRefreshService tokenRefreshService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testValidateRefreshToken_ValidToken() {
        // Arrange
        String username = "testuser";
        String refreshToken = "valid_refresh_token";
        User user = new User();
        user.setUsername(username);
        user.setRefreshToken(refreshToken);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        boolean isValid = tokenRefreshService.validateRefreshToken(username, refreshToken);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateRefreshToken_InvalidToken() {
        // Arrange
        String username = "testuser";
        String refreshToken = "invalid_refresh_token";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        boolean isValid = tokenRefreshService.validateRefreshToken(username, refreshToken);

        // Assert
        assertFalse(isValid);
    }

}

