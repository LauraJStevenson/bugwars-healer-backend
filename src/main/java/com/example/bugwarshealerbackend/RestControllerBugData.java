package com.example.bugwarshealerbackend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/bug-data")

public class RestControllerBugData {
    private static final List<BugDataTemporary> BUG_DATA_TEMPORARIES = List.of(
            new BugDataTemporary("Venus", "Red", "Turret attacker, cannot move, can rotate", 1),
            new BugDataTemporary("Dizzy", "Blue", "Spins and attacks", 2),
            new BugDataTemporary("Runner", "Green", "Runs around the outside of map and attacks", 3),
            new BugDataTemporary("SmackLol", "Yellow", "Attack, only attack", 4)
    );

    @GetMapping
    public List<BugDataTemporary> getBugData(){
        return BUG_DATA_TEMPORARIES;
    }

}