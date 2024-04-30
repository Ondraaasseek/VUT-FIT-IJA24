/* @file Room.java
 * @brief Class for Environment
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.room;

import java.util.List;

import ija.ija2023.project.common.AbstractRobot;
import ija.ija2023.project.common.Environment;
import ija.ija2023.project.tool.common.Position;
import ija.ija2023.project.tool.common.ToolRobot;
import ija.ija2023.project.tool.view.RobotView;

public class Room extends Object implements Environment {
    int rows;
    int cols;
    char[][] room;
    List<AbstractRobot> robots = new java.util.ArrayList<AbstractRobot>();
    List<RobotView> robotViews = new java.util.ArrayList<RobotView>();

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

    public static Room create(int rows, int cols) {
        // check if room dimensions are positive
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Room dimensions must be positive");
        }
        // create room
        return new Room(rows, cols);
    }

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
        // add observer to the robot
        RobotView robotView = new RobotView();
        robotView.position = robot.getPosition();
        robot.addObserver(robotView);
        robotViews.add(robotView);
        return true;
    }

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
        // remove observer from the robot
        for (RobotView robotView : robotViews) {
            if (robotView.position.equals(robot.getPosition())) {
                robot.removeObserver(robotView);
                robotViews.remove(robotView);
                break;
            }
        }
        return true;
    }

    public boolean containsPosition(Position pos) {
        // check if position is null
        if (pos == null) {
            return false;
        }
        // check if position is within the room
        return pos.getRow() >= 0 && pos.getRow() < this.rows && pos.getCol() >= 0 && pos.getCol() < this.cols;
    }

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

    public boolean obstacleAt(int row, int col) {
        // check if position is within the room
        if (!this.containsPosition(new Position(row, col))) {
            return false;
        }
        // check if obstacle is at the position
        return room[row][col] == 'O';
    }

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

    public int rows() {
        // return number of rows
        return this.rows;
    }

    public int cols() {
        // return number of columns
        return this.cols;
    }

    public List<ToolRobot> robots() {
        // return new list of robots
        return new java.util.ArrayList<ToolRobot>(robots);
    }

    public List<RobotView> robotViews() {
        // return new list of robot views
        return this.robotViews;
    }
}
