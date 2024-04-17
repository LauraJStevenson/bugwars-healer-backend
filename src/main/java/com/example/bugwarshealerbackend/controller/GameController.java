package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.dto.*;
import com.example.bugwarshealerbackend.game.Compiler;
import com.example.bugwarshealerbackend.game.GameEngine;
import com.example.bugwarshealerbackend.model.GameMap;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PostMapping("/start")
    public ResponseEntity<?> startGame(@Valid @RequestBody StartGameRequest startGameRequest) {
        if (startGameRequest.getMap() == null || startGameRequest.getMap().isEmpty()) {
            return ResponseEntity.badRequest().body("Map data must not be null or empty.");
        }
        try {
            GameMap initialMap = new GameMap(startGameRequest.getMap());
            this.gameEngine = new GameEngine(initialMap); // Initialize the game engine with the map

            // Extract scripts from request, but handle them dynamically
            List<int[]> scripts = new ArrayList<>();
            if (startGameRequest.getScript1() != null && startGameRequest.getScript1().length > 0) {
                scripts.add(startGameRequest.getScript1());
            }
            if (startGameRequest.getScript2() != null && startGameRequest.getScript2().length > 0) {
                scripts.add(startGameRequest.getScript2());
            }
            if (startGameRequest.getScript3() != null && startGameRequest.getScript3().length > 0) {
                scripts.add(startGameRequest.getScript3());
            }
            if (startGameRequest.getScript4() != null && startGameRequest.getScript4().length > 0) {
                scripts.add(startGameRequest.getScript4());
            }

            // Ensure at least one script has been provided
            if (scripts.isEmpty()) {
                return ResponseEntity.badRequest().body("At least one valid script must be provided.");
            }

            int[][] scriptsArray = scripts.toArray(new int[scripts.size()][]);
            this.gameEngine.playOneTick(scriptsArray);
            return ResponseEntity.ok(initialMap);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting the game: " + e.getMessage());
        }
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
