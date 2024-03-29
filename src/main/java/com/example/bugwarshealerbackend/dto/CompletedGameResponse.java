package com.example.bugwarshealerbackend.dto;

import com.example.bugwarshealerbackend.model.GameMap;
import com.example.bugwarshealerbackend.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CompletedGameResponse extends BaseResponse{

    private List<GameMap> maps;
    public CompletedGameResponse(List<GameMap> maps) {
        super();
        this.maps = maps;
    }
}
