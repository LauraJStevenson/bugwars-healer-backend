package com.example.bugwarshealerbackend.model;

import lombok.Getter;

public class Bug extends Cell{

    @Getter
    private char direction;

    public Bug (int x, int y) {
        super(x, y);
        direction = 'N';
        this.scriptIndex = 0;
    }

    private int[] bugScript;
    private int scriptIndex;

    public void setBugScript(int[] script) {
        this.bugScript = script;
    }

    @Override
    public Cell clone() {
        return new Bug(this.getRow(), this.getColumn());
    }

    public void execute(Cell[][] map, int tick) {

        int command = this.bugScript[this.scriptIndex];
        switch (command) {
            case 0 :
                break;
            case 10 :
                Cell destination = null;
                switch (this.direction) {
                    case 'W' :
                        if(this.getColumn() != 0) {
                            destination = map[this.getRow()][this.getColumn()-1];
                        }
                        break;
                    case 'E' :
                        if(this.getColumn() != map.length -1) {
                            destination = map[this.getRow()][this.getColumn()+1];
                        }
                        break;
                    case 'N' :
                        if(this.getRow() != 0) {
                            destination = map[this.getRow()-1][this.getColumn()];
                        }
                        break;
                    case 'S' :
                        if(this.getRow() != map[this.getRow()].length -1) {
                            destination = map[this.getRow()+1][this.getColumn()];
                        }
                        break;
                }
                if(destination != null && destination instanceof EmptySpace) {
                    //switch the places of the object references for the bug and empty space
                    map[destination.getRow()][destination.getColumn()] = this;
                    map[this.getRow()][this.getColumn()] = destination;
                    //update the internal locations of the bug and the empty spaces
                    int destinationX = destination.getRow();
                    int destinationY = destination.getColumn();
                    destination.setRow(this.getRow());
                    destination.setColumn(this.getColumn());
                    this.setRow(destinationX);
                    this.setColumn(destinationY);
                }
            case 11 :
                if(direction == 'N') {
                    this.direction = 'E';
                } else if(direction == 'E') {
                    this.direction = 'S';
                } else if(direction == 'S') {
                    this.direction = 'W';
                } else if(direction == 'W') {
                    this.direction = 'N';
                }
                break;
            case 12:
                if(direction == 'N') {
                    this.direction = 'W';
                } else if(direction == 'W') {
                    this.direction = 'S';
                } else if(direction == 'S') {
                    this.direction = 'E';
                } else if(direction == 'E') {
                    this.direction = 'N';
                }
                break;
            case 13:

                break;

        }
    }

}
