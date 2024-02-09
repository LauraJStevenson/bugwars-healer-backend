package com.example.bugwarshealerbackend.game;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CompilerTest {

    @Test
    public void compiler_is_valid() {
        //Arrange
        String script = """
:START att
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertTrue(isValid);
    }
    @Test
    public void compiler_inValid_label_casing() {
        //Arrange
        String script = """
:start att
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_label_alphanumeric() {
        //Arrange
        String script = """
:START* att
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_label_defination_lenngth_3() {
        //Arrange
        String script = """
:START att att
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_label_defination_lenngth_1() {
        //Arrange
        String script = """
:START
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_label_starts_with_letter() {
        //Arrange
        String script = """
:1START att
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_label_invalid_command() {
        //Arrange
        String script = """
:START come
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_command_3() {
        //Arrange
        String script = """
:START att
att
ifFood EAT
mov
rotr
rotl
goto START att
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_compound_command_inValid_command() {
        //Arrange
        String script = """
:1START att
att
ifFood EAT
mov
rotr
rotl
come START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_inValid_command() {
        //Arrange
        String script = """
:START att
att
ifFood EAT
mov
rotr
come
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_inValid_missing_label() {
        //Arrange
        String script = """
:START att
att
ifFood EAT
mov
rotr
rotl
goto AND
:EAT eat
rotr
rotl
goto START""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertFalse(isValid);
    }
    @Test
    public void compiler_compiled() {
        //Arrange
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(13);
        expectedResult.add(13);
        expectedResult.add(32);
        expectedResult.add(9);
        expectedResult.add(10);
        expectedResult.add(11);
        expectedResult.add(12);
        expectedResult.add(35);
        expectedResult.add(0);
        expectedResult.add(14);
        expectedResult.add(11);
        expectedResult.add(12);
        expectedResult.add(35);
        expectedResult.add(0);

        String script = """
:START att
att
ifFood EAT
mov
rotr
rotl
goto START
:EAT eat
rotr
rotl
goto START""";
        //Act
        List<Integer> compiledScript = Compiler.compile(script);
        //Assert
        for(int i = 0; i <compiledScript.size(); i++) {
            Assert.assertEquals(expectedResult.get(i),compiledScript.get(i));
        }
    }

}