package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class LogoutControllerTest {

    @Mock
    private Authentication authentication;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LogoutController logoutController;

    @Test
    void testLogout() {
        // Arrange
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Mock SecurityContextHolder and authentication.getCredentials
        SecurityContextHolder.setContext(new TestSecurityContext(authentication));
        Mockito.when(authentication.getCredentials()).thenReturn("mockJwtToken");

        // Act
        logoutController.logout();

        // Assert
        Mockito.verify(jwtService).addToDenyList("mockJwtToken");
    }

    private static class TestSecurityContext implements SecurityContext {
        private final Authentication authentication;

        public TestSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {

        }
    }

}