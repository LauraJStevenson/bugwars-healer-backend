package com.example.bugwarshealerbackend.model;

public class GameMap {

    private Cell[][] cells;
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
                        currentCell = new Wall();
                        break;
                    case ' ' :
                        currentCell = new EmptySpace();
                        break;
                    case 'a' :
                    case 'b' :
                    case 'c' :
                    case 'd' :
                        currentCell = new Bug();
                        break;
                    case 'f' :
                        currentCell = new Food();
                        break;
                    default: throw new RuntimeException("Unexpected character in serialized map!");
                }
                cells[i][j] = currentCell;
            }
        }
    }
}
