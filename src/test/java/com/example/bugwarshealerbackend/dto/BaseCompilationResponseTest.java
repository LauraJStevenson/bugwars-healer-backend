package com.example.bugwarshealerbackend.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseCompilationResponseTest {

    @Test
    public void testConstructor(){
        String testScript = "test script";

        BaseCompilationResponse response = new BaseCompilationResponse(testScript);

        assertEquals(testScript,response.getScript());
    }

    @Test
    void testGetScript() {
        String testScript = "test script";
        BaseCompilationResponse response = new BaseCompilationResponse("initial script");
        String result = response.getScript();

        assertEquals(testScript,result);
    }

    @Test
    void testSetScript() {
        BaseCompilationResponse response = new BaseCompilationResponse("initial script");
        String newScript = "new script";

        response.setScript(newScript);

        assertEquals(newScript, response.getScript());
    }
}