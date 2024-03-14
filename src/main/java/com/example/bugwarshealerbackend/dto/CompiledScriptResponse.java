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


//the CompiledScriptResponse class is designed to represent a response to the compilation of a script.
// It inherits from a base class (BaseCompilationResponse) and contains a List of Integers (compiledScript).
// The constructor initializes the fields with the provided values.