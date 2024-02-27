package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.ScriptDto;
import com.example.bugwarshealerbackend.model.Script;
import com.example.bugwarshealerbackend.service.ScriptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


class ScriptControllerTest {

    @Mock
    private ScriptService scriptService;

    @InjectMocks
    private ScriptController scriptController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createScript() {
        // Arrange
        Script script = new Script();
        when(scriptService.createScript(any(Script.class))).thenReturn(script);

        // Act
        Script returnedScript = scriptController.createScript(script);

        // Assert
        assertNotNull(returnedScript);
        assertEquals(script, returnedScript);
        verify(scriptService, times(1)).createScript(any(Script.class));
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
        // Arrange
        Long scriptId = 1L;
        Script script = new Script();
        when(scriptService.getScriptById(scriptId)).thenReturn(script);

        // Act
        Script returnedScript = scriptController.getScriptById(scriptId);

        // Assert
        assertNotNull(returnedScript);
        assertEquals(script, returnedScript);
        verify(scriptService, times(1)).getScriptById(scriptId);
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
