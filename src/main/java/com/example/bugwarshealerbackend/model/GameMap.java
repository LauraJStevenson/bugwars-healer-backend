package com.example.bugwarshealerbackend.model;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private Cell[][] cells;

    private List<Bug> bugs;

    private GameMap(Cell[][] cells, List<Bug> bugs) {
        this.cells = cells;
        this.bugs = bugs;
    }

    public List<Bug> getBugs() {
        return this.bugs;
    }

    public GameMap(String asciiMap) {
        this.bugs = new ArrayList<>();
        String[] lines = asciiMap.split("\n");
        this.cells = new Cell[lines.length][];
        for(int i = 0; i < lines.length; i++) {
            String line = lines[i];
            this.cells[i] = new Cell[line.length()];
            for (int j = 0; j < line.length(); j++) {
                char character = line.charAt(j);
                Cell currentCell = null;
                // create current cell
                switch (character) {
                    case 'X' :
                        currentCell = new Wall(i,j);
                        break;
                    case ' ' :
                        currentCell = new EmptySpace(i,j);
                        break;
                    case 'a' :
                    case 'b' :
                    case 'c' :
                    case 'd' :
                        Bug bug = new Bug(i,j, character);
                        currentCell = bug;
                        this.bugs.add(bug);
                        break;
                    case 'f' :
                        currentCell = new Food(i,j);
                        break;
                    default: throw new RuntimeException("Unexpected character in serialized map!");
                }
                cells[i][j] = currentCell;
            }
        }
    }

    // Get all cells
    public Cell[][] getCells () {
        return this.cells;
    }

    //Copy gameMap method
    public GameMap clone() {
        Cell[][] cells = new Cell[this.cells.length][];
        List<Bug> bugs = new ArrayList<>();
        //copy each cell and create new area
        for(int i = 0 ; i< cells.length; i++) {
            cells[i] = new Cell[this.cells[i].length];
            for(int j = 0 ; j < cells[i].length; j++ ) {
                cells[i][j] = this.cells[i][j].clone();
                if(cells[i][j] instanceof Bug) {
                    Bug bug = (Bug) cells[i][j];
                    bugs.add(bug);
                }
            }
        }
        GameMap newMap = new GameMap(cells,bugs);
        return newMap;
    }
}
