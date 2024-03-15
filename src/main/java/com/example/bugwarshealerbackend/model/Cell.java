package com.example.bugwarshealerbackend.model;

public abstract class Cell {
    public abstract Cell clone();

    private int row;
    private int column;

    protected Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    protected int getRow() {return row;}
    protected int getColumn() {return column;}
    protected void setRow(int row) {this.row = row;}
    protected void setColumn(int column) {this.column = column;}
}
