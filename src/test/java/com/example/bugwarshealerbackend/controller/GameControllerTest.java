package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.CompileRequest;
import com.example.bugwarshealerbackend.dto.CompiledScriptResponse;
import com.example.bugwarshealerbackend.dto.ValidationResultResponse;
import com.example.bugwarshealerbackend.game.Compiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GameControllerTest {

    @Mock
    private Compiler compiler;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCompilerEndpoint() throws Exception {
        String script = "test script";
        when(compiler.compile(any(String.class))).thenReturn(Arrays.asList(1, 2, 3));

        CompileRequest compileRequest = new CompileRequest(script);
        CompiledScriptResponse response = gameController.compile(compileRequest);

        assertNotNull(response);
        assertEquals(Arrays.asList(1, 2, 3), response.getCompiledScript());
        assertEquals(script, response.getScript());
    }

    @Test
    void validate() {
        String script = "test script";
        when(compiler.validate(any(String.class))).thenReturn(true);

        ValidationResultResponse response = gameController.validate(new CompileRequest(script));

        assertNotNull(response);
        assertEquals(true, response.isValid());
        assertEquals(script, response.getScript());
    }
}
