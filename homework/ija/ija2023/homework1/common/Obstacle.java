package ija.ija2023.homework1.common;

public class Obstacle extends Object {
    Environment env;
    Position pos;

    public Obstacle(Environment env, Position pos) {
        this.env = env;
        this.pos = pos;
    }

    public Position getPosition() {
        return this.pos;
    }
}
