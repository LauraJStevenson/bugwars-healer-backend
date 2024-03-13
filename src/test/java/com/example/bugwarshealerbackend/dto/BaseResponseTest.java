package com.example.bugwarshealerbackend.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class BaseResponseTest {

    @Test
    void getStatus() {
        //Arrange
        BaseResponse baseResponse = new BaseResponse();
        //Act
        String status = baseResponse.getStatus();
        //Assert
        assertEquals(HttpStatus.OK.toString(), status);
    }

    @Test
    void getErrorMessage() {
        //Arrange
        BaseResponse baseResponse = new BaseResponse();
        //Act
        String errorMessage = baseResponse.getErrorMessage();
        //Assert
        assertNull(errorMessage);
    }

    @Test
    void setStatus() {
        //Arrange
        BaseResponse baseResponse = new BaseResponse();
        //Act
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST.toString(), baseResponse.getStatus());
    }

    @Test
    void setErrorMessage() {
        //Arrange
        BaseResponse baseResponse = new BaseResponse();
        String newErrorMessage = "error message";
        //Act
        baseResponse.setErrorMessage(newErrorMessage);
        //Assert
        assertEquals(newErrorMessage, baseResponse.getErrorMessage());
    }
}