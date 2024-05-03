/** 
 * @file Room.java
 * @brief Class for Room
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.room;

import java.util.List;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.Position;
import ija.ija2023.project.common.robot.AbstractRobot;

/**
 * Class for Environment
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see AbstractRobot
 * @see Position
 */
public class Room implements Environment {
    int rows;
    int cols;
    /** Room */
    public char[][] room;
    List<AbstractRobot> robots = new java.util.ArrayList<AbstractRobot>();

    /**
     * Constructor for Room
     * @param rows number of rows of the room
     * @param cols number of columns of the room
     */
    private Room(int rows, int cols) {
        // initialize rows and cols
        this.rows = rows;
        this.cols = cols;
        this.room = new char[rows][cols];
        // initialize room with Free spaces
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                room[i][j] = 'F';
            }
        }
    }

    /**
     * Create new room
     * @param rows number of rows of the room
     * @param cols number of columns of the room
     * @return new room
     */
    public static Room create(int rows, int cols) {
        // check if room dimensions are positive
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Room dimensions must be positive");
        }
        // create room
        return new Room(rows, cols);
    }

    /**
     * Add robot to the room
     * @param robot robot to be added
     * @return true if robot was added, false otherwise
     */
    public boolean addRobot(AbstractRobot robot) {
        // check if robot is null
        if (robot == null) {
            return false;
        }
        // check if robot has a position
        if (robot.getPosition() == null) {
            return false;
        }
        // get robot position
        Position robotPosition = robot.getPosition();
        // check if robot position is within the room
        if (!this.containsPosition(robotPosition)) {
            return false;
        }
        // check if robot position is not occupied
        if (room[robotPosition.getRow()][robotPosition.getCol()] != 'F') {
            return false;
        }
        // add robot to the room
        room[robotPosition.getRow()][robotPosition.getCol()] = 'R';
        // add robot to the list of robots
        robots.add(robot);
        return true;
    }

    /**
     * Remove robot from the room
     * @param row row of the robot
     * @param col column of the robot
     * @return true if robot was removed, false otherwise
     */
    public boolean removeRobotFrom(int row, int col) {
        // get robot from the position
        AbstractRobot robot = this.getRobotFromPosition(new Position(row, col));
        // check if robot is null
        if (robot == null) {
            return false;
        }
        // check if robot has a position
        if (robot.getPosition() == null) {
            return false;
        }
        // get robot position
        Position robotPosition = robot.getPosition();
        // check if robot position is within the room
        if (!this.containsPosition(robotPosition)) {
            return false;
        }
        // check if robot is at the position
        if (room[robotPosition.getRow()][robotPosition.getCol()] != 'R') {
            return false;
        }
        // remove robot from the room
        room[robotPosition.getRow()][robotPosition.getCol()] = 'F';
        // remove robot from the list of robots
        robots.remove(robot);
        return true;
    }


    /**
     * Check if position is within the room
     * @param pos Position to be checked
     * @return true if position is within the room, false otherwise
     */
    public boolean containsPosition(Position pos) {
        // check if position is null
        if (pos == null) {
            return false;
        }
        // check if position is within the room
        return pos.getRow() >= 0 && pos.getRow() < this.rows && pos.getCol() >= 0 && pos.getCol() < this.cols;
    }

    /**
     * Create obstacle at the specified position
     * @param row Row where obstacle should be created
     * @param col Column where obstacle should be created
     * @return true if obstacle was created, false otherwise
     */
    public boolean createObstacleAt(int row, int col) {
        // check if position is within the room
        if (!this.containsPosition(new Position(row, col))) {
            return false;
        }
        // check if obstacle position is not occupied
        if (room[row][col] != 'F') {
            return false;
        }
        // add obstacle to the room
        room[row][col] = 'O';
        return true;
    }

    /**
     * Remove obstacle from the specified position
     * @param row Row where obstacle should be removed
     * @param col Column where obstacle should be removed
     * @return true if obstacle was removed, false otherwise
     */
    public boolean removeObstacleFrom(int row, int col) {
        // check if position is within the room
        if (!this.containsPosition(new Position(row, col))) {
            return false;
        }
        // check if obstacle is at the position
        if (room[row][col] != 'O') {
            return false;
        }
        // remove obstacle from the room
        room[row][col] = 'F';
        return true;
    }

    /**
     * Check if obstacle is at the specified position
     * @param row Row where obstacle should be
     * @param col Column where obstacle should be
     * @return true if obstacle is at the position, false otherwise
     */
    public boolean obstacleAt(int row, int col) {
        // check if position is within the room
        if (!this.containsPosition(new Position(row, col))) {
            return false;
        }
        // check if obstacle is at the position
        return room[row][col] == 'O';
    }

    /**
     * Check if robot is at the specified position
     * @param p Position where robot should be
     * @return true if robot is at the position, false otherwise
     */
    public boolean obstacleAt(Position p) {
        // check if position is null
        if (p == null) {
            return false;
        }
        // check if position is within the room
        if (!this.containsPosition(p)) {
            return false;
        }
        // check if obstacle is at the position
        return room[p.getRow()][p.getCol()] == 'O';
    }

    /**
     * Check if robot is at the specified position
     * @param p Position where robot should be
     * @return true if robot is at the position, false otherwise
     */
    public boolean robotAt(Position p) {
        // check if position is null
        if (p == null) {
            return false;
        }
        // check if position is within the room
        if (!this.containsPosition(p)) {
            return false;
        }
        // check if robot is at the position
        for(AbstractRobot robot : robots) {
            if (robot.getPosition().equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get robot from the specified position
     * @param p Position where robot should be
     * @return robot at the position or null if there is no robot
     */
    public AbstractRobot getRobotFromPosition(Position p) {
        // check if position is null
        if (p == null) {
            return null;
        }
        // check if position is within the room
        if (!this.containsPosition(p)) {
            return null;
        }
        // check if robot is at the position
        for(AbstractRobot robot : robots) {
            if (robot.getPosition().equals(p)) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Get robot from the specified id
     * @param id robot id
     * @return robot with the id or null if there is no robot
     */
    public AbstractRobot getRobotFromId(String id) {
        // check if id is null
        if (id == null) {
            return null;
        }
        // check if id is empty
        if (id.isEmpty()) {
            return null;
        }
        // check if robot is in the list of robots
        for(AbstractRobot robot : robots) {
            if (robot.getId().equals(id)) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Get number of rows of the room
     * @return number of rows
     */
    public int rows() {
        // return number of rows
        return this.rows;
    }

    /**
     * Get number of columns of the room
     * @return number of columns
     */
    public int cols() {
        // return number of columns
        return this.cols;
    }

    /**
     * Get list of robots in the room
     * @return list of robots
     */
    public List<AbstractRobot> robots() {
        // return new list of robots
        return new java.util.ArrayList<AbstractRobot>(robots);
    }

    /**
     * Get room as a string
     * @return room as a string
     */
    @Override
    public String toString() {
        // create new string builder
        StringBuilder sb = new StringBuilder();

        // add room dimensions to the string builder
        sb.append(cols + ";" + rows + '\n');

        // add room to the string builder
        for (int y = 0; y < this.rows; y++) {
            for (int x = 0; x < this.cols; x++) {
                // Add obstacle to the string builder
                if (room[y][x] == 'O') {
                    sb.append("O;" + x + ';' + y + '\n');
                }

                // Add robot to the string builder
                if (room[y][x] == 'R') {
                    // Get robot from the position
                    AbstractRobot robot = this.getRobotFromPosition(new Position(y, x));

                    // Get robot type
                    String robotType = robot instanceof ControlledRobot ? "CR" : "AR";

                    // Add robot to the string builder
                    int angle = robot.getAngle();
                    angle = -angle;
                    if (angle != 0) angle += 360;
                    angle %= 360;
                    sb.append(robotType + ";" + x + ';' + y + ';' + angle + '\n');
                }
            }
        }
        // return string builder as a string
        return sb.toString();
    }
}
