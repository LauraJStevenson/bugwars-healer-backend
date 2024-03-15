package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.Script;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ScriptRepositoryTest {

    @Mock
    private ScriptRepository scriptRepository;

    @Test
    void findById() {
        // Arrange
        Long scriptId = 1L;
        Script expectedScript = new Script();
        expectedScript.setId(scriptId);
        when(scriptRepository.findById(scriptId)).thenReturn(Optional.of(expectedScript));

        // Act
        Optional<Script> actualScriptOptional = scriptRepository.findById(scriptId);

        // Assert
        assertTrue(actualScriptOptional.isPresent(), "Script should be found");
        assertEquals(expectedScript, actualScriptOptional.get(), "The found script should match the expected script");
    }
}
