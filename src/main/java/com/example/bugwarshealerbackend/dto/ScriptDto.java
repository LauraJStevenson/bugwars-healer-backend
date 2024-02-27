package com.example.bugwarshealerbackend.dto;

public class ScriptDto {
    private String name;
    private String rawCode;

    public String getName() {
        return name;
    }

    public String getRawCode() {
        return rawCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }
}
