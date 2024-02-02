package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.model.Script;
import com.example.bugwarshealerbackend.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/scripts")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @PostMapping("/")
    public Script createScript(@RequestBody Script script) {
        return scriptService.createScript(script);
    }

    @GetMapping("/user/{userId}")
    public List<Script> getAllScriptsByUserId(@PathVariable Long userId) {
        return scriptService.getAllScriptsByUserId(userId);
    }

    @GetMapping("/{scriptId}")
    public Script getScriptById(@PathVariable Long scriptId) {
        return scriptService.getScriptById(scriptId);
    }

    @PutMapping("/{scriptId}")
    public Script updateScript(@PathVariable Long scriptId, @RequestBody Script scriptDetails) {
        return scriptService.updateScript(scriptId, scriptDetails);
    }

    @DeleteMapping("/{scriptId}")
    public ResponseEntity<?> deleteScript(@PathVariable Long scriptId) {
        scriptService.deleteScript(scriptId);
        return ResponseEntity.ok().build();
    }
}
