package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.ScriptDto;
import com.example.bugwarshealerbackend.model.Script;
import com.example.bugwarshealerbackend.service.ScriptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
class ScriptControllerTest {

    @Mock
    private ScriptService scriptService;

    @InjectMocks
    private ScriptController scriptController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(scriptService);
    }

    @Test
    void createScript() {
        // Arrange
        ScriptDto scriptDto = new ScriptDto();
        scriptDto.setName("Test Script");
        scriptDto.setRawCode("Test Code");

        Script script = new Script();
        script.setName(scriptDto.getName());
        script.setRawCode(scriptDto.getRawCode());

        when(scriptService.createScript(any(ScriptDto.class))).thenReturn(script);

        // Act
        Script returnedScript = scriptController.createScript(scriptDto);

        // Assert
        assertNotNull(returnedScript);
        assertEquals(script, returnedScript);
    }


    @Test
    void getAllScriptsByUserId() {
        // Arrange
        Long userId = 1L;
        List<Script> scripts = new ArrayList<>();
        when(scriptService.getAllScriptsByUserId(userId)).thenReturn(scripts);

        // Act
        List<Script> returnedScripts = scriptController.getAllScriptsByUserId(userId);

        // Assert
        assertNotNull(returnedScripts);
        assertEquals(scripts.size(), returnedScripts.size());
        assertEquals(scripts, returnedScripts);
        verify(scriptService, times(1)).getAllScriptsByUserId(userId);
    }

    @Test
    void getScriptById() {
        Long scriptId = 1L;
        Script script = new Script();
        script.setId(scriptId);
        script.setName("Sample Name");
        script.setRawCode("Sample Code");

        when(scriptService.getScriptById(scriptId)).thenReturn(script);

        Script returnedScript = scriptController.getScriptById(scriptId);

        assertNotNull(returnedScript);
        assertEquals(script.getId(), returnedScript.getId());
    }



    @Test
    void updateScript() {
        // Arrange
        Long scriptId = 1L;
        ScriptDto scriptDto = new ScriptDto();
        Script updatedScript = new Script();
        when(scriptService.updateScript(scriptId, scriptDto)).thenReturn(updatedScript);

        // Act
        ResponseEntity<Script> responseEntity = scriptController.updateScript(scriptId, scriptDto);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedScript, responseEntity.getBody());
        verify(scriptService, times(1)).updateScript(scriptId, scriptDto);
    }

    @Test
    void deleteScript() {
        // Arrange
        Long scriptId = 1L;

        // Act
        ResponseEntity<?> responseEntity = scriptController.deleteScript(scriptId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(scriptService, times(1)).deleteScript(scriptId);
    }
}
