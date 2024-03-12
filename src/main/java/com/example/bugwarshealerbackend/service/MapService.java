package com.example.bugwarshealerbackend.service;

import com.example.bugwarshealerbackend.jpa.MapRepository;
import com.example.bugwarshealerbackend.model.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public List<Map> getAllMaps() {
        return mapRepository.findAll();
    }

    public Optional<Map> getMapById(Long id) {
        return mapRepository.findById(id);
    }
}
