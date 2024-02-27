package com.example.bugwarshealerbackend.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@Transactional
public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setRefreshToken(null);
        user.setCounter(0);
        user.setActivated(false);

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidUsername() {
        // Arrange
        User user = new User();
        user.setUsername("a");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setRefreshToken(null);
        user.setCounter(0);
        user.setActivated(false);

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Username length must be between 3 and 15 characters.", violations.iterator().next().getMessage());
    }

    @Test
    void testValidEmail() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setRefreshToken(null);
        user.setCounter(0);
        user.setActivated(false);

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPassword("password123");
        user.setEmail("invalid_email");
        user.setRefreshToken(null);
        user.setCounter(0);
        user.setActivated(false);

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Email should be valid.", violations.iterator().next().getMessage());
    }
}
