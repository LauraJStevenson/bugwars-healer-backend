package com.example.bugwarshealerbackend.model;

public class EmptySpace extends Cell{

    public EmptySpace(int x, int y) {
        super(x, y);
    }
    @Override
    public Cell clone() {
        return new EmptySpace(this.getX(), this.getY());
    }
}
