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

    private GameEngine gameEngine;

    @PostMapping("/compile")
    public CompiledScriptResponse compile(@Valid @RequestBody CompileRequest compileRequest) {
        String script = compileRequest.getScript();
        List<Integer> compiledScripts = Compiler.compile(script);
        return new CompiledScriptResponse(compiledScripts, script);
    }

    @PostMapping("/validate")
    public ValidationResultResponse validate(@Valid @RequestBody CompileRequest compileRequest) {
        String script = compileRequest.getScript();
        boolean isValid = Compiler.validate(script);
        return new ValidationResultResponse(isValid, script);
    }

    // Initialize the game with the selected map and scripts
    @PostMapping("/game/start")
    public ResponseEntity<GameMap> startGame(@Valid @RequestBody StartGameRequest startGameRequest) {
        GameMap initialMap = new GameMap(startGameRequest.getMap());
        this.gameEngine = new GameEngine(initialMap); // Initialize the game engine with the initial map

        // Assuming scripts are sent in order, if not you need to adjust how they are assigned
        int[] script1 = startGameRequest.getScript1();
        int[] script2 = startGameRequest.getScript2();
        int[] script3 = startGameRequest.getScript3();
        int[] script4 = startGameRequest.getScript4();

        // Initialize bugs with scripts, it's essential for the game's first state
        this.gameEngine.playOneTick(script1, script2, script3, script4);
        return ResponseEntity.ok(initialMap); // Return the initial game state
    }

    // Advance the game by one tick and return the new state
    @PostMapping("/advance")
    public ResponseEntity<GameMap> advanceGame(@Valid @RequestBody AdvanceGameRequestDTO advanceGameRequestDTO) {
        int[] script1 = advanceGameRequestDTO.getScript1();
        int[] script2 = advanceGameRequestDTO.getScript2();
        int[] script3 = advanceGameRequestDTO.getScript3();
        int[] script4 = advanceGameRequestDTO.getScript4();

        GameMap newMap = gameEngine.playOneTick(script1, script2, script3, script4);
        return ResponseEntity.ok(newMap);
    }
}
