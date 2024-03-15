package ija.ija2023.homework1.common;

public class Position extends Object {
    int row;
    int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position p = (Position) o;
        return this.row == p.row && this.col == p.col;
    }

    @Override
    public int hashCode() {
        return this.row * 31 + this.col;
    }

    @Override
    public String toString() {
        return "Position[" + this.row + ", " + this.col + "]";
    }
}
