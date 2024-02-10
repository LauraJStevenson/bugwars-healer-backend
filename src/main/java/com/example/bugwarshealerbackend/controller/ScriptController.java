package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.model.Script;
import com.example.bugwarshealerbackend.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/scripts")
public class ScriptController {

    /**
     * Controller for managing scripts.
     * This controller provides endpoints for CRUD operations on scripts.
     */
    @Autowired
    private ScriptService scriptService;

    /**
     * Create a new script.
     *
     * @param script the script to create
     * @return the created script
     */
    @PostMapping("/")
    public Script createScript(@RequestBody Script script) {
        return scriptService.createScript(script);
    }

    /**
     * Get all scripts for a given user.
     *
     * @param userId the ID of the user whose scripts to retrieve
     * @return a list of scripts for the specified user
     */
    @GetMapping("/user/{userId}")
    public List<Script> getAllScriptsByUserId(@PathVariable Long userId) {
        return scriptService.getAllScriptsByUserId(userId);
    }

    /**
     * Get a script by its ID.
     *
     * @param scriptId the ID of the script to retrieve
     * @return the requested script
     */
    @GetMapping("/{scriptId}")
    public Script getScriptById(@PathVariable Long scriptId) {
        return scriptService.getScriptById(scriptId);
    }

    /**
     * Update a script.
     *
     * @param scriptId      the ID of the script to update
     * @param scriptDetails the new details of the script
     * @return the updated script
     */
    @PutMapping("/{scriptId}")
    public Script updateScript(@PathVariable Long scriptId, @RequestBody Script scriptDetails) {
        return scriptService.updateScript(scriptId, scriptDetails);
    }

    /**
     * Delete a script by its ID.
     *
     * @param scriptId the ID of the script to delete
     * @return a ResponseEntity indicating the outcome of the operation
     */
    @DeleteMapping("/{scriptId}")
    public ResponseEntity<?> deleteScript(@PathVariable Long scriptId) {
        scriptService.deleteScript(scriptId);
        return ResponseEntity.ok().build();
    }
}
