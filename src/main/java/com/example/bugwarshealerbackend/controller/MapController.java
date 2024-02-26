package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.MapRepository;
import com.example.bugwarshealerbackend.model.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class MapController {

    @Autowired
    private MapRepository mapRepository;
    @GetMapping("/maps")
    public List<Map> maps () {
        return mapRepository.findAll();
    }
}
