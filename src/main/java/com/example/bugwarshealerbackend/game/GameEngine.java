package com.example.bugwarshealerbackend.game;

import com.example.bugwarshealerbackend.model.Bug;
import com.example.bugwarshealerbackend.model.Cell;
import com.example.bugwarshealerbackend.model.GameMap;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private GameMap currentMap;

    public GameEngine(GameMap initialMap) {
        this.currentMap = initialMap;
        initializeBugs(initialMap);
    }

    // Initializes bugs with their scripts.
    private void initializeBugs(GameMap map, int[]... scripts) {
        int counter = 0;

        Cell[][] cells = map.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell instanceof Bug) {
                    Bug bug = (Bug) cell;

                    // Wrap around the scripts array if there are more bugs than scripts.
                    int scriptIndex = counter % scripts.length;
                    bug.setBugScript(scripts[scriptIndex]);

                    counter++;
                }
            }
        }
    }

    // Advances the game by one tick and returns the new game state.
    public GameMap playOneTick(int[]... scripts) {
        // Reassign scripts if necessary. This can be adjusted based on game logic.
        // For example, you might only want to reassign scripts at certain intervals,
        // or based on specific game events, etc.
        initializeBugs(currentMap, scripts);

        // Advance the game by one tick.
        currentMap = playRound(currentMap, 0);

        return currentMap;
    }

    // Advances the game state by one tick.
    private static GameMap playRound(GameMap currentMap, int tick) {
        GameMap result = currentMap.clone();
        Cell[][] cells = result.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] instanceof Bug) {
                    Bug bug = (Bug) cells[i][j];
                    bug.execute(cells, tick);
                }
            }
        }

        // Prepare for the next round.
        result.nextRound();
        return result;
    }
}
