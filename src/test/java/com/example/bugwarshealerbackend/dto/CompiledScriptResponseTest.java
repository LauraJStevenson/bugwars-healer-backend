package com.example.bugwarshealerbackend.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompiledScriptResponseTest {

    @Test
    void getCompiledScript() {
        //Arrange
        List<Integer> scriptList = List.of(1,2,3);
        String script = "exampleScript";
        CompiledScriptResponse compiledScriptResponse = new CompiledScriptResponse(scriptList, script);

        //Act
        List<Integer> result = compiledScriptResponse.getCompiledScript();

        //Assert
        assertEquals(scriptList, result);
    }

    @Test
    void setCompiledScript() {
        //Arrange
        List<Integer> scriptList = List.of(1,2,3);
        String script = "exampleScript";
        CompiledScriptResponse compiledScriptResponse = new CompiledScriptResponse(scriptList, script);

        //Act
        List<Integer> newScriptList = List.of(4,5,6);
        compiledScriptResponse.setCompiledScript(newScriptList);

        //Assert
        List<Integer> result = compiledScriptResponse.getCompiledScript();
        assertEquals(newScriptList, result);
    }


}