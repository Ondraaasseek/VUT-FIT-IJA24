package ija.ija2023.project.common.robot;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.Position;

public abstract class AbstractRobot implements Robot{
    protected Environment env;
    protected Position pos;
    protected int angle;

    public AbstractRobot(Environment env, Position pos) {
        // initialize environment, position and angle
        this.env = env;
        this.pos = pos;
        this.angle = 0;
    }

    public int getAngle() {
        // return current angle
        return this.angle;
    }

    public Position getPosition() {
        // return current position
        return this.pos;
    }

    public void turn(int n) {
        // normalize angle
        if (n > 0){
            n = n % 8;
        } else if (n < 0) {
            n = 8 + (n % 8);
        }
        // turn robot by 45 degrees n times
        for (int i = 0; i < n; i++) {
            this.angle = (this.angle + 45) % 360;
        }
    }

    public void turn() {
        // turn robot by 45 degrees
        this.turn(1);
    }
}
