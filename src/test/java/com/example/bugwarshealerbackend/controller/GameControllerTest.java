package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.CompileRequest;
import com.example.bugwarshealerbackend.dto.CompiledScriptResponse;
import com.example.bugwarshealerbackend.dto.ValidationResultResponse;
import com.example.bugwarshealerbackend.game.Compiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
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
        List<Integer> compiledScripts = Arrays.asList(1, 2, 3);
        when(compiler.compile(script)).thenReturn(compiledScripts);
        CompileRequest compileRequest = new CompileRequest(script);

        CompiledScriptResponse response = gameController.compile(compileRequest);

        assertNotNull(response);
        assertEquals(Arrays.asList(1,2,3), response.getCompiledScript());
        assertEquals(script, response.getScript());

        //testing the method that receives a script through the http post request using
        //a compiler class and returns a response containing the scripts.
    }

    @Test
    void testValidateEndpoint() {
        String script = "test script";
        when(compiler.validate(script)).thenReturn(true);

        ValidationResultResponse response = gameController.validate(new CompileRequest(script));

        assertNotNull(response);
        assertEquals(true, response.isValid());
        assertEquals(script, response.getScript());
    }
}
