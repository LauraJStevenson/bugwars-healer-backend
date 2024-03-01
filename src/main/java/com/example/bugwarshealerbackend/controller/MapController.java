package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.MapRepository;
import com.example.bugwarshealerbackend.model.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MapController {

    @Autowired
    private MapRepository mapRepository;
    @GetMapping("/maps")
    public ResponseEntity<List<Map>> maps() {
        try {
            List<Map> maps = mapRepository.findAll();
            return ResponseEntity.ok(maps);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
