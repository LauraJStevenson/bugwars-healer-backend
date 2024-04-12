package com.example.bugwarshealerbackend.model;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private Cell[][] cells;

    // Constructor for creating a GameMap from ASCII map representation
    public GameMap(String asciiMap) {
        String[] lines = asciiMap.split("\n");
        cells = new Cell[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            cells[i] = new Cell[lines[i].length()];
            for (int j = 0; j < lines[i].length(); j++) {
                cells[i][j] = createCellFromChar(lines[i].charAt(j), i, j);
            }
        }
    }

    // Helper method to create cells based on character
    private Cell createCellFromChar(char ch, int x, int y) {
        switch (ch) {
            case 'X':
                return new Wall(x, y);
            case ' ':
                return new EmptySpace(x, y);
            case 'a': case 'b': case 'c': case 'd': // Assume these are different types of bugs
                return new Bug(x, y, Character.toString(ch));  // Directly pass the char
            case 'f':
                return new Food(x, y);
            default:
                throw new IllegalArgumentException("Unsupported character in map: " + ch);
        }
    }

    // Method to clone the GameMap for simulation without affecting the original
    public GameMap clone() {
        Cell[][] newCells = new Cell[cells.length][];
        for (int i = 0; i < cells.length; i++) {
            newCells[i] = new Cell[cells[i].length];
            for (int j = 0; j < cells[i].length; j++) {
                newCells[i][j] = cells[i][j].clone(); // Ensure Cell class has a clone method
            }
        }
        return new GameMap(newCells);
    }

    // Private constructor used by clone method
    private GameMap(Cell[][] cells) {
        this.cells = cells;
    }

    // Gets all bugs on the map
    public List<Bug> getBugs() {
        List<Bug> bugs = new ArrayList<>();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell instanceof Bug) {
                    bugs.add((Bug) cell);
                }
            }
        }
        return bugs;
    }

    // Moves the game to the next round, resetting states as necessary
    public void nextRound() {
        for (Bug bug : getBugs()) {
            bug.resetExecuted(); // Assuming Bug class has a method to reset its state per round
        }
    }

    // Getter for cells
    public Cell[][] getCells() {
        return cells;
    }
}
