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
public class ScriptTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testScriptIsValid() {
        // Arrange
        Script script = new Script();
        script.setName("Test Script");
        script.setRawCode("console.log('Hello, world!');");

        // Act
        Set<ConstraintViolation<Script>> violations = validator.validate(script);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testScriptNameNotBlank() {
        // Arrange
        Script script = new Script();
        script.setRawCode("console.log('Hello, world!');");

        // Act
        Set<ConstraintViolation<Script>> violations = validator.validate(script);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testScriptRawCodeNotBlank() {
        // Arrange
        Script script = new Script();
        script.setName("Test Script");

        // Act
        Set<ConstraintViolation<Script>> violations = validator.validate(script);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("rawCode", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testScriptNameSizeConstraint() {
        // Arrange
        Script script = new Script();
        script.setName("");
        script.setRawCode("console.log('Hello, world!');");

        // Act
        Set<ConstraintViolation<Script>> violations = validator.validate(script);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size()); // Violates @NotBlank and @Size
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void testScriptNameSizeConstraintExceeded() {
        // Arrange
        Script script = new Script();
        script.setName("ThisScriptNameIsExceedingFiftyCharactersAndShouldFailValidation");
        script.setRawCode("console.log('Hello, world!');");

        // Act
        Set<ConstraintViolation<Script>> violations = validator.validate(script);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }

}
