package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.dto.ScriptDto;
import com.example.bugwarshealerbackend.jpa.ScriptRepository;
import com.example.bugwarshealerbackend.model.Script;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScriptServiceTest {

    @Mock
    private ScriptRepository scriptRepository;

    @InjectMocks
    private ScriptService scriptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createScriptTest() {
        ScriptDto scriptDto = new ScriptDto();
        scriptDto.setName("Test Name");
        scriptDto.setRawCode("Test Raw Code");


        Script expectedScript = new Script();
        expectedScript.setName(scriptDto.getName());
        expectedScript.setRawCode(scriptDto.getRawCode());
        expectedScript.setBytecode(scriptDto.getBytecode());

        when(scriptRepository.save(any(Script.class))).thenReturn(expectedScript);

        Script created = scriptService.createScript(scriptDto);

        assertNotNull(created);
        assertEquals(expectedScript, created);
        verify(scriptRepository).save(any(Script.class));
    }


    @Test
    void getAllScriptsByUserIdTest() {
        Script script = new Script();
        when(scriptRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(script));

        List<Script> scripts = scriptService.getAllScriptsByUserId(1L);

        assertFalse(scripts.isEmpty());
        verify(scriptRepository).findByUserId(1L);
    }

    @Test
    void getScriptByIdTest() {
        Optional<Script> script = Optional.of(new Script());
        when(scriptRepository.findById(anyLong())).thenReturn(script);

        Script found = scriptService.getScriptById(1L);

        assertNotNull(found);
        verify(scriptRepository).findById(1L);
    }

    @Test
    void updateScriptTest() {
        // Given
        Long scriptId = 1L;
        Script existingScript = new Script();
        existingScript.setId(scriptId);
        existingScript.setName("Old Name");
        existingScript.setRawCode("Old Code");

        ScriptDto scriptDetails = new ScriptDto();
        scriptDetails.setName("New Name");
        scriptDetails.setRawCode("New Code");

        when(scriptRepository.findById(scriptId)).thenReturn(Optional.of(existingScript));
        when(scriptRepository.save(any(Script.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Script updatedScript = scriptService.updateScript(scriptId, scriptDetails);

        // Then
        assertNotNull(updatedScript);
        assertEquals("New Name", updatedScript.getName());
        assertEquals("New Code", updatedScript.getRawCode());
        verify(scriptRepository).findById(scriptId);
        verify(scriptRepository).save(any(Script.class));
    }




    @Test
    void deleteScriptTest() {
        scriptService.deleteScript(1L);

        verify(scriptRepository).deleteById(1L);
    }
}

