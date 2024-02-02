package com.example.bugwarshealerbackend.jpa;

import com.example.bugwarshealerbackend.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    // Find all scripts by the user_id foreign key
    List<Script> findByUserId(Long userId);

    // You can add more custom queries as needed, for example:
    // Find a script by its name and the owner's ID, useful for ensuring name uniqueness per user
    Script findByNameAndUserId(String name, Long userId);

    // Additional custom methods can be added here
}

