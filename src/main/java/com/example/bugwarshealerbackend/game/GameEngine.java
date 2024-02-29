package com.example.bugwarshealerbackend.game;

import com.example.bugwarshealerbackend.model.Bug;
import com.example.bugwarshealerbackend.model.Cell;
import com.example.bugwarshealerbackend.model.GameMap;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    public static List<GameMap> play (GameMap initialMap, int[] script1, int[] script2, int[] script3, int[] script4, int ticks) {
        int[][] scripts = new int[4][];
        scripts[0] = script1;
        scripts[1] = script2;
        scripts[2] = script3;
        scripts[3] = script4;
        int counter = 0;

        // Assign scripts to bugs
        Cell[][] cells = initialMap.getCells();
        for (Cell[] row : cells) {
            for(Cell cell : row) {
                if(cell instanceof Bug) {
                    Bug bug = (Bug) cell;

                    //Scripts are assigned to bugs in scanning order
                    bug.setBugScript(scripts[counter]);
                    counter++;
                }
            }
        }

        //play the game until it ends or for the given amount of ticks
        List<GameMap> result = new ArrayList<>();
        result.add(initialMap);

        GameMap currentMap = initialMap;
        for (int i = 0; i < ticks; i++) {
            GameMap updatedMap = playRound(currentMap, i);
            result.add(updatedMap);
            currentMap = updatedMap;
        }
        return result;
    }

    private static GameMap playRound(GameMap currentMap, int tick) {

        Cell[][] cells = currentMap.getCells();
        for (int i = 0; i < cells.length; i++) {
            for(int j = 0; j < cells[i].length; j++) {
                if(cells[i][j] instanceof Bug) {
                    Bug bug = (Bug) cells[i][j];
                    bug.execute(cells, tick);


                }
            }
        }
    }
}
