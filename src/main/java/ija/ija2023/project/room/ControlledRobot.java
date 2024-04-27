/* @file ControlledRobot.java
 * @brief Class for ControlledRobot
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.room;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.Robot;
import ija.ija2023.project.tool.common.Position;
import ija.ija2023.project.tool.common.AbstractObservableRobot;

public class ControlledRobot extends AbstractObservableRobot implements Robot {
    Environment env;
    Position pos;
    int angle;

    private ControlledRobot(Environment env, Position pos) {
        // initialize environment, position and angle
        this.env = env;
        this.pos = pos;
        this.angle = 0;
    }

    public static ControlledRobot create(Environment env, Position pos) {
        // check if env or pos is null
        if (env == null || pos == null) {
            return null;
        }
        // check if position is within the environment
        if (!env.containsPosition(pos)) {
            return null;
        }
        // create controlled robot
        ControlledRobot robot = new ControlledRobot(env, pos);
        // add robot to the environment
        if (!env.addRobot(robot)) {
            return null;
        }
        // return new controlled robot
        return robot;
    }

    public int angle() {
        // return current angle
        return this.angle;
    }

    public boolean canMove() {
        // get new position based on the current angle
        Position p = this.getNewPosition();
        // check if new position is within the environment and not occupied
        if (!this.env.containsPosition(p) || this.env.obstacleAt(p) || this.env.robotAt(p)) {
            return false;
        }
        // robot can move
        return true;
    }

    public Position getPosition() {
        // return current position
        return this.pos;
    }

    public boolean move() {
        // check if robot can move
        if (!this.canMove()) {
            return false;
        }
        // move robot
        this.pos = this.getNewPosition();
        // notify observers
        this.notifyObservers();
        return true;
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
        // notify observers
        this.notifyObservers();
    }

    public void turn() {
        // turn robot by 45 degrees
        this.turn(1);
    }

    // private method for getting new position based on the current angle
    private Position getNewPosition() {
        Position p;
        switch (this.angle) {
            // set new position to the north
            case 0:
                p = new Position(this.pos.getRow() - 1, this.pos.getCol());
                break;
            // set new position to the northeast
            case 45:
                p = new Position(this.pos.getRow() - 1, this.pos.getCol() + 1);
                break;
            // set new position to the east
            case 90:
                p = new Position(this.pos.getRow(), this.pos.getCol() + 1);
                break;
            // set new position to the southeast
            case 135:
                p = new Position(this.pos.getRow() + 1, this.pos.getCol() + 1);
                break;
            // set new position to the south
            case 180:
                p = new Position(this.pos.getRow() + 1, this.pos.getCol());
                break;
            // set new position to the southwest
            case 225:
                p = new Position(this.pos.getRow() + 1, this.pos.getCol() - 1);
                break;
            // set new position to the west
            case 270:
                p = new Position(this.pos.getRow(), this.pos.getCol() - 1);
                break;
            // set new position to the northwest
            case 315:
                p = new Position(this.pos.getRow() - 1, this.pos.getCol() - 1);
                break;
            // set new position to the default
            default:
                p = new Position(this.pos.getRow(), this.pos.getCol());
                break;
        }
        // return new position
        return p;
    }
}
