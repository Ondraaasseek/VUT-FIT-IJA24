package ija.ija2023.homework1.common;

public interface Robot {
    void turn();
    int angle();
    boolean canMove();
    boolean move();
    Position getPosition();
}
