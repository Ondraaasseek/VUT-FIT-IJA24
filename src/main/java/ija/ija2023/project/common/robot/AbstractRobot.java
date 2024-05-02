package ija.ija2023.project.common.robot;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.PixelPosition;
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
    protected PixelPosition pixelPos;
    protected int angle;
    protected String id;

    /**
     * Constructor for AbstractRobot
     * @param env environment
     * @param pos position
     */
    public AbstractRobot(Environment env, Position pos) {
        // initialize environment, position and angle
        this.env = env;
        this.pos = pos;
        this.pixelPos = new PixelPosition(0, 0);
        this.angle = 0;
        this.id = pos.hashCode() + "";
    }

    /**
     * Constructor for AbstractRobot
     * @param id robot id
     * @param angle angle
     * @param pixelPos PixelPosition
     */
    public AbstractRobot(String id, int angle, PixelPosition pixelPos) {
        // initialize robot id, angle and pixel position
        this.id = id;
        this.angle = angle;
        this.pixelPos = pixelPos;
    }

    /**
     * Get robot id
     * @return
     */
    public String getId() {
        // return robot id
        return this.id;
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
     * Set angle of the robot
     * @param angle angle
     */
    public void setAngle(int angle) {
        // set angle
        this.angle = angle;
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
     * Get pixel position of the robot
     * @return
     */
    public PixelPosition getPixelPosition() {
        // return current pixel position
        return this.pixelPos;
    }

    /**
     * Set pixel position of the robot
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPixelPosition(double x, double y) {
        this.pixelPos = new PixelPosition(y, x);
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
