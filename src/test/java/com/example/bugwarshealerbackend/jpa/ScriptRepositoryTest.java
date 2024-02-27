package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.Script;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ScriptRepositoryTest {

    @Mock
    private ScriptRepository scriptRepository;

    @Test
    void findByUserId() {
        // Arrange
        Long userId = 1L;
        List<Script> expectedScripts = new ArrayList<>();
        expectedScripts.add(new Script());
        expectedScripts.add(new Script());

        when(scriptRepository.findByUserId(userId)).thenReturn(expectedScripts);

        // Act
        List<Script> actualScripts = scriptRepository.findByUserId(userId);

        // Assert
        assertNotNull(actualScripts);
        assertEquals(expectedScripts.size(), actualScripts.size());
    }

    @Test
    void findById() {
        // Arrange
        Long scriptId = 1L;
        Script expectedScript = new Script();

        when(scriptRepository.findById(scriptId)).thenReturn(Optional.of(expectedScript));

        // Act
        Optional<Script> actualScriptOptional = scriptRepository.findById(scriptId);

        // Assert
        assertNotNull(actualScriptOptional);
        assertEquals(expectedScript, actualScriptOptional.orElse(null));
    }
}
