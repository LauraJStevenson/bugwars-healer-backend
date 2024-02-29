package com.example.bugwarshealerbackend.model;

public class GameMap {

    private Cell[][] cells;

    private GameMap(Cell[][] cells) {
        this.cells = cells;
    }

    public GameMap(String asciiMap) {
        String[] lines = asciiMap.split("\n");
        for(int i = 0; i < lines.length; i++) {
            String line = lines[i];
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
                        currentCell = new Bug(i,j);
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
        //copy each cell and create new area
        for(int i = 0 ; i< cells.length; i++) {
            cells[i] = new Cell[this.cells[i].length];
            for(int j = 0 ; j < cells[i].length; j++ ) {
                cells[i][j] = this.cells[i][j].clone();
            }
        }
        GameMap newMap = new GameMap(cells);
        return newMap;
    }
}
