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

    @Transactional
    public Script createScript(Script script) {
        return scriptRepository.save(script);
    }

    @Transactional(readOnly = true)
    public List<Script> getAllScriptsByUserId(Long userId) {
        return scriptRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Script getScriptById(Long scriptId) {
        return scriptRepository.findById(scriptId)
                .orElseThrow(() -> new RuntimeException("Script not found for this id :: " + scriptId));
    }

    @Transactional
    public Script updateScript(Long scriptId, Script scriptDetails) {
        Script script = scriptRepository.findById(scriptId)
                .orElseThrow(() -> new RuntimeException("Script not found for this id :: " + scriptId));
        script.setName(scriptDetails.getName());
        script.setRawCode(scriptDetails.getRawCode());
        return scriptRepository.save(script);
    }

    @Transactional
    public void deleteScript(Long scriptId) {
        scriptRepository.deleteById(scriptId);
    }
}
