package com.example.bugwarshealerbackend.model;

public class Bug extends Cell{
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
        return new Bug(this.getX(), this.getY());
    }

    public void execute(Cell[][] map, int tick) {

        int command = this.bugScript[this.scriptIndex];
        switch (command) {
            case 0 :
                break;
            case 10 :
                Cell destination = null;
                switch (this.direction) {
                    case 'N' :
                        if(this.getY() != 0) {
                            destination = map[this.getX()][this.getY()-1];
                        }
                        break;
                    case 'S' :
                        if(this.getY() != map.length -1) {
                            destination = map[this.getX()][this.getY()+1];
                        }
                        break;
                    case 'W' :
                        if(this.getX() != 0) {
                            destination = map[this.getX()-1][this.getY()];
                        }
                        break;
                    case 'E' :
                        if(this.getX() != map[this.getX()].length -1) {
                            destination = map[this.getX()+1][this.getY()];
                        }
                        break;
                }
                if(destination != null && destination instanceof EmptySpace) {
                    //switch the places of the object references for the bug and empty space
                    map[destination.getX()][destination.getY()] = this;
                    map[this.getX()][this.getY()] = destination;
                    //update the internal locations of the bug and the empty spaces
                    int destinationX = destination.getX();
                    int destinationY = destination.getY();
                    destination.setX(this.getX());
                    destination.setY(this.getY());
                    this.setX(destinationX);
                    this.setY(destinationY);
                }
        }
    }
}
