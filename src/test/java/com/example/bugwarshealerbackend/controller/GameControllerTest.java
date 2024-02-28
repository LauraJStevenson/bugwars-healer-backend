package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.CompileRequest;
import com.example.bugwarshealerbackend.dto.CompiledScriptResponse;
import com.example.bugwarshealerbackend.dto.ValidationResultResponse;
import com.example.bugwarshealerbackend.game.Compiler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

class GameControllerTest {


    @Mock
    private Compiler compiler;
    @InjectMocks
    private GameController gameController;


    @Test
    void testCompilerEndpoint() throws Exception {
        String script = "test script";
        when(compiler.compile(any(String.class))).thenReturn(Arrays.asList(1, 2, 3));

        CompileRequest compileRequest = new CompileRequest(script);
        CompiledScriptResponse response = gameController.compile(compileRequest);

        assertEquals(Arrays.asList(1, 2, 3), response.getCompiledScript());
        assertEquals(script, response.getScript());
    }

    @Test
    void validate() {
        String script = "test script";
        when(Compiler.validate(any(String.class))).thenReturn(true);

        // When
        ValidationResultResponse response = gameController.validate(new CompileRequest(script));

        // Then
        assertEquals(true, response.isValid());
        assertEquals(script, response.getScript());


    }
}