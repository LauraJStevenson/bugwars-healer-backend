package com.example.bugwarshealerbackend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BugDataTemporary {
    private String name;
    private String color;
    private String characteristics;
    private int id;
}