package com.example.bugwarshealerbackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptDtoTest {

    @Test
    void testGetName() {
        // Arrange
        ScriptDto scriptDto = new ScriptDto();
        scriptDto.setName("TestName");

        // Act
        String name = scriptDto.getName();

        // Assert
        assertEquals("TestName", name);
    }

    @Test
    void testGetRawCode() {
        // Arrange
        ScriptDto scriptDto = new ScriptDto();
        scriptDto.setRawCode("TestRawCode");

        // Act
        String rawCode = scriptDto.getRawCode();

        // Assert
        assertEquals("TestRawCode", rawCode);
    }

    @Test
    void testSetName() {
        // Arrange
        ScriptDto scriptDto = new ScriptDto();

        // Act
        scriptDto.setName("NewName");

        // Assert
        assertEquals("NewName", scriptDto.getName());
    }

    @Test
    void testSetRawCode() {
        // Arrange
        ScriptDto scriptDto = new ScriptDto();

        // Act
        scriptDto.setRawCode("NewRawCode");

        // Assert
        assertEquals("NewRawCode", scriptDto.getRawCode());
    }
}

