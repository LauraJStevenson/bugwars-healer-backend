package com.example.bugwarshealerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationResultResponse extends BaseCompilationResponse {
    private boolean isValid;
    public ValidationResultResponse(boolean isValid, String script) {
        super(script);
        this.isValid = isValid;
    }
}
