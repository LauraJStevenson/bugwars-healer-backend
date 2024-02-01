package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.service.TokenRefreshService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TokenRefreshControllerTest {

    @Mock
    private TokenRefreshService tokenRefreshService;

    @InjectMocks
    private TokenRefreshController tokenRefreshController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void refreshAccessToken_success() throws Exception {
        // Arrange
        String fakeRefreshToken = "fakeRefreshToken";
        String fakeAccessToken = "fakeAccessToken";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(fakeRefreshToken);
        when(tokenRefreshService.rotateRefreshToken(fakeRefreshToken)).thenReturn(fakeAccessToken);

        // Act
        ResponseEntity<?> responseEntity = tokenRefreshController.refreshAccessToken(refreshTokenRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        TokenResponse tokenResponse = (TokenResponse) responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertEquals(fakeAccessToken, tokenResponse.getAccessToken());
        verify(tokenRefreshService, times(1)).rotateRefreshToken(fakeRefreshToken);
    }

    @Test
    void refreshAccessToken_failure() throws Exception {
        // Arrange
        String fakeRefreshToken = "fakeRefreshToken";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(fakeRefreshToken);
        when(tokenRefreshService.rotateRefreshToken(fakeRefreshToken)).thenThrow(new RuntimeException("Token refresh failed"));

        // Act
        ResponseEntity<?> responseEntity = tokenRefreshController.refreshAccessToken(refreshTokenRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals("Token refresh failed", errorResponse.getMessage());
        verify(tokenRefreshService, times(1)).rotateRefreshToken(fakeRefreshToken);
    }
}
