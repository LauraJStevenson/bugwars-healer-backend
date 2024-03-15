package com.example.bugwarshealerbackend.dto;

public class ScriptDto {

    private Long id;
    private String name;
    private String rawCode;
    private Integer[] bytecode;
    private Long userId;

    public String getName() {
        return name;
    }

    public String getRawCode() {
        return rawCode;
    }

    public Integer[] getBytecode() {
        return bytecode;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }

    public void setBytecode(Integer[] bytecode) {
        this.bytecode = bytecode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
