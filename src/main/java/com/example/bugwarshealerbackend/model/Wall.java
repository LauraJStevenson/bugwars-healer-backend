package com.example.bugwarshealerbackend.model;

public class Wall extends Cell{

    public Wall(int x, int y) {
        super(x, y);
    }
    @Override
    public Cell clone() {
        return new Wall(this.getX(), this.getY());
    }
}
