package com.example.bugwarshealerbackend.dto;

import lombok.Data;
@Data
public class BaseCompilationResponse extends BaseResponse{
    private String script;
    public BaseCompilationResponse(String script) {
        super();
        this.script = script;
    }
    public BaseCompilationResponse() {}
}
