package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.*;
import com.example.bugwarshealerbackend.game.Compiler;
import com.example.bugwarshealerbackend.game.GameEngine;
import com.example.bugwarshealerbackend.model.GameMap;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("api/v1/game")
public class GameController {

    @PostMapping("/compile")
    public CompiledScriptResponse compile(@Valid @RequestBody CompileRequest compileRequest) {
        String script = compileRequest.getScript();
        List<Integer> compiledScripts = Compiler.compile(script);
        return new CompiledScriptResponse(compiledScripts,script);
    }

    @PostMapping("/validate")
    public ValidationResultResponse validate (@Valid @RequestBody CompileRequest compileRequest) {
        String script = compileRequest.getScript();
        boolean isValid = Compiler.validate(script);
        return new ValidationResultResponse(isValid, script);
    }

    @PostMapping("play")
    public CompletedGameResponse play(@Valid @RequestBody StartGameRequest startGameRequest)
    {
        String encodedInitialMap = startGameRequest.getMap();
        int ticks = startGameRequest.getTicks();
        int[] script1 = startGameRequest.getScript1();
        int[] script2 = startGameRequest.getScript2();
        int[] script3 = startGameRequest.getScript3();
        int[] script4 = startGameRequest.getScript4();

        GameMap initialMap = new GameMap(encodedInitialMap);

        List<GameMap> maps = GameEngine.play(initialMap, script1, script2, script3, script4, ticks);
        return new CompletedGameResponse(maps);
    }
}
