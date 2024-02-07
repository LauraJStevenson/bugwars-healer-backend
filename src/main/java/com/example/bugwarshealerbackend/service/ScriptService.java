package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.jpa.ScriptRepository;
import com.example.bugwarshealerbackend.model.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    /**
     * Creates and saves a new script in the repository.
     *
     * @param script The script to be created and saved.
     * @return The saved script entity.
     */
    @Transactional
    public Script createScript(Script script) {
        return scriptRepository.save(script);
    }

    /**
     * Retrieves all scripts associated with a specific user ID.
     *
     * @param userId The user ID whose scripts are to be retrieved.
     * @return A list of scripts associated with the given user ID.
     */
    @Transactional(readOnly = true)
    public List<Script> getAllScriptsByUserId(Long userId) {
        return scriptRepository.findByUserId(userId);
    }

    /**
     * Retrieves a script by its ID.
     *
     * @param scriptId The ID of the script to retrieve.
     * @return The script associated with the given ID.
     * @throws RuntimeException if no script is found for the provided ID.
     */
    @Transactional(readOnly = true)
    public Script getScriptById(Long scriptId) {
        return scriptRepository.findById(scriptId)
                .orElseThrow(() -> new RuntimeException("Script not found for this id :: " + scriptId));
    }

    /**
     * Updates an existing script identified by the given ID with new details.
     *
     * @param scriptId      The ID of the script to update.
     * @param scriptDetails The new details to be applied to the script.
     * @return The updated script entity.
     * @throws RuntimeException if no script is found for the provided ID.
     */
    @Transactional
    public Script updateScript(Long scriptId, Script scriptDetails) {
        Script script = scriptRepository.findById(scriptId)
                .orElseThrow(() -> new RuntimeException("Script not found for this id :: " + scriptId));
        script.setName(scriptDetails.getName());
        script.setRawCode(scriptDetails.getRawCode());
        return scriptRepository.save(script);
    }

    /**
     * Deletes a script identified by the given ID.
     *
     * @param scriptId The ID of the script to delete.
     */

    @Transactional
    public void deleteScript(Long scriptId) {
        scriptRepository.deleteById(scriptId);
    }
}
