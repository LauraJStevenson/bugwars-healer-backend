package com.example.bugwarshealerbackend.model;

import lombok.Getter;

public class Bug extends Cell{

    @Getter
    private char direction;

    private char bugType;

    private boolean executed;

    public Bug (int x, int y, char bugType) {
        super(x, y);
        direction = 'N';
        this.scriptIndex = 0;
        this.bugType = bugType;
        this.executed = false;
    }

    private boolean isAlly(char otherBugType){
        return bugType == otherBugType;
    }

    private int[] bugScript;
    private int scriptIndex;

    public void setBugScript(int[] script) {
        this.bugScript = script;
    }

    @Override
    public Cell clone() {
        Bug result = new Bug(this.getRow(), this.getColumn(), bugType);
        result.setBugScript(this.bugScript);
        result.scriptIndex = this.scriptIndex;
        result.direction = this.direction;
        return result;
    }

    public Cell getForwardCell(Cell[][] cells){
        int rowFrontBug = 0;
        int columnFrontBug = 0;
        if(direction == 'N') {
            rowFrontBug = this.getRow() - 1;
            columnFrontBug = this.getColumn();
        }
        else if(direction == 'E') {
            rowFrontBug = this.getRow() ;
            columnFrontBug = this.getColumn() + 1;
        }
        else if(direction == 'S') {
            rowFrontBug = this.getRow() + 1;
            columnFrontBug = this.getColumn();
        }
        else if(direction == 'W') {
            rowFrontBug = this.getRow();
            columnFrontBug = this.getColumn() - 1;
        }
        return cells[rowFrontBug][columnFrontBug];
    }

    public void resetExecuted(){
        this.executed = false;
    }

    public void execute(Cell[][] map, int tick) {

        if(this.executed) {
            return;
        }

        this.executed = true;

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
                break;
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
                Cell forwardCellAtt= getForwardCell(map);
                if(forwardCellAtt instanceof Bug) {
                    Bug otherBug = (Bug) forwardCellAtt;
                    if(!isAlly(otherBug.bugType)){
                        Food newFood = new Food(forwardCellAtt.getRow(), forwardCellAtt.getColumn());
                        map[forwardCellAtt.getRow()][forwardCellAtt.getColumn()] = newFood;
                    }
                }
                break;
            case 14:
                Cell forwardCellFood = getForwardCell(map);
                if(forwardCellFood instanceof Food){
                    Bug newBug = new Bug(forwardCellFood.getRow(), forwardCellFood.getColumn(), bugType);
                    newBug.setBugScript(this.bugScript);
                    map[forwardCellFood.getRow()][forwardCellFood.getColumn()] = newBug;
                }
                break;
            case 30:
                Cell forwardCellIfEnemy = getForwardCell(map);
                if(forwardCellIfEnemy instanceof Bug) {
                    Bug otherBug = (Bug) forwardCellIfEnemy;
                    if(!isAlly(otherBug.bugType)){
                        scriptIndex++;
                        execute(map, tick);
                    }
                }
                break;
            case 31:
                Cell forwardCellIfAlly = getForwardCell(map);
                if(forwardCellIfAlly instanceof Bug) {
                    Bug otherBug = (Bug) forwardCellIfAlly;
                    if(isAlly(otherBug.bugType)){
                        scriptIndex++;
                        execute(map, tick);
                    }
                }
                break;
            case 32:
                Cell forwardCellIfFood = getForwardCell(map);
                if(forwardCellIfFood instanceof Food) {
                    scriptIndex++;
                    execute(map, tick);
                }
                break;
            case 33:
                Cell forwardCellIfEmpty= getForwardCell(map);
                if(forwardCellIfEmpty instanceof EmptySpace) {
                    scriptIndex++;
                    execute(map, tick);
                }
                break;
            case 34:
                Cell forwardCellIfWall = getForwardCell(map);
                if(forwardCellIfWall instanceof Wall) {
                    scriptIndex++;
                    execute(map, tick);
                }
                break;
            case 35:
                int destinationIndex = bugScript[scriptIndex+1];
                scriptIndex = destinationIndex;
                execute(map, tick);
                break;

        }
        scriptIndex++;
    }

}
