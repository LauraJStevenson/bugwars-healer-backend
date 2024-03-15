package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.MapRepository;
import com.example.bugwarshealerbackend.model.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/maps")
public class MapController {

    @Autowired
    private MapRepository mapRepository;
    @GetMapping("/")
    public List<Map> maps () {
        return mapRepository.findAll();
    }
}
