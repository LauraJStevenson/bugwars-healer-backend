package com.example.bugwarshealerbackend.model;

public class Food extends Cell{

    public Food(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell clone() {
        return new Food(this.getRow(),this.getColumn());
    }
}
