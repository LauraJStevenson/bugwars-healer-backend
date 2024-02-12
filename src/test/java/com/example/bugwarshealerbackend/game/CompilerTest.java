package com.example.bugwarshealerbackend.game;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class CompilerTest {

    @Test
    public void compiler_cleanScript() {
        //Arrange
        String script = """
# Head Spinner
:ANOTHER_LABEL
:START       att
             att  #I'm a comment
             ifFood EAT
             mov
             rotr
             rotl
             goto START
:EAT         eat
             rotr
             rotr
             goto START
             
#I'm a comment""";
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add(":ANOTHER_LABEL");
        expectedResult.add(":START att");
        expectedResult.add("att");
        expectedResult.add("ifFood EAT");
        expectedResult.add("mov");
        expectedResult.add("rotr");
        expectedResult.add("rotl");
        expectedResult.add("goto START");
        expectedResult.add(":EAT eat");
        expectedResult.add("rotr");
        expectedResult.add("rotr");
        expectedResult.add("goto START");
        //Act
        List<String> result = Compiler.cleanScript(script);
        //Assert
        Assert.assertArrayEquals(expectedResult.toArray(new String[0]),result.toArray(new String[0]));
    }
    @Test
    public void compiler_is_valid_forCleanScript() {
        //Arrange
        String script = """
# Head Spinner
:START       att
             att  #I'm a comment
             ifFood EAT
             mov
             rotr
             rotl
             goto START
:EAT         eat
             rotr
             rotr
             goto START
             
#I'm a comment""";
        //Act
        boolean isValid = Compiler.validate(script);
        //Assert
        Assert.assertTrue(isValid);
    }

    @Test
    public void compiler_compiled_for_cleanScript() {
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
        expectedResult.add(11);
        expectedResult.add(35);
        expectedResult.add(0);

        String script = """
# Head Spinner
:START       att
             att  #I'm a comment
             ifFood EAT
             mov
             rotr
             rotl
             goto START
:EAT         eat
             rotr
             rotr
             goto START
             
#I'm a comment""";
        //Act
        List<Integer> compiledScript = Compiler.compile(script);
        //Assert
        for(int i = 0; i <compiledScript.size(); i++) {
            Assert.assertEquals(expectedResult.get(i),compiledScript.get(i));
        }
    }

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
        Assert.assertArrayEquals(expectedResult.toArray(new Integer[0]),compiledScript.toArray(new Integer[0]));
    }
}