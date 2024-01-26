package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.CompileRequest;
import com.example.bugwarshealerbackend.dto.CompiledScript;
import com.example.bugwarshealerbackend.dto.ValidationResult;
import com.example.bugwarshealerbackend.game.Compiler;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/game")
public class GameController {

    @PostMapping("/compile")
    public CompiledScript compile(@Valid @RequestBody CompileRequest compileRequest) {
        String script = compileRequest.getScript();
        List<Integer> compiledScripts = Compiler.compile(script);
        return new CompiledScript(compiledScripts);
    }

    @PostMapping("/validate")
    public ValidationResult validate (@Valid @RequestBody CompileRequest compileRequest) {
        String script = compileRequest.getScript();
        boolean isValid = Compiler.validate(script);
        return new ValidationResult(isValid, script);
    }
}
