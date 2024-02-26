package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
}
