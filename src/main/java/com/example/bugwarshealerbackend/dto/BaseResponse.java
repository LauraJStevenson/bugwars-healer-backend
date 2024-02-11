package com.example.bugwarshealerbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class BaseResponse {
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
    public BaseResponse() {
        this.status = HttpStatus.OK.toString();
        this.errorMessage = null;
    }
}
