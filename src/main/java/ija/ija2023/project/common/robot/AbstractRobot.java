package ija.ija2023.project.common.robot;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.Position;

/**
 * Abstract class for Robot
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see Environment
 * @see Position
 */
public abstract class AbstractRobot implements Robot{
    protected Environment env;
    protected Position pos;
    protected int angle;

    /**
     * Constructor for AbstractRobot
     * @param env environment
     * @param pos position
     */
    public AbstractRobot(Environment env, Position pos) {
        // initialize environment, position and angle
        this.env = env;
        this.pos = pos;
        this.angle = 0;
    }

    /**
     * Get angle of the robot
     * @return
     */
    public int getAngle() {
        // return current angle
        return this.angle;
    }

    /**
     * Get position of the robot
     * @return
     */
    public Position getPosition() {
        // return current position
        return this.pos;
    }

    /**
     * Move robot by n steps
     * @param n number of steps
     */
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

    /**
     * Move robot by 1 step
     */
    public void turn() {
        // turn robot by 45 degrees
        this.turn(1);
    }
}
