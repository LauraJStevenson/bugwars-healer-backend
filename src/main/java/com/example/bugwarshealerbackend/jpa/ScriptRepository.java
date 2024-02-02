package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    List<Script> findByUserId(Long userId);

    Script findByNameAndUserId(String name, Long userId);
}

