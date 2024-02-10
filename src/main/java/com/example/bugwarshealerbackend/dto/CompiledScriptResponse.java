package com.example.bugwarshealerbackend.dto;

import lombok.Data;
import java.util.List;
@Data
public class CompiledScriptResponse extends BaseCompilationResponse {

    private List<Integer> compiledScript;
    public CompiledScriptResponse(List<Integer> compiledScript, String script) {
        super(script);
        this.compiledScript = compiledScript;
    }
}
