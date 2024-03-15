package com.example.bugwarshealerbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartGameRequest {
    private String map;
    private int[] script1;
    private int[] script2;
    private int[] script3;
    private int[] script4;
    private int ticks;

}

