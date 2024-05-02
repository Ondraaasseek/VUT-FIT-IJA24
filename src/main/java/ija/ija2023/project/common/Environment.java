/* @file Environment.java
 * @brief Interface for Environment
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common;

import ija.ija2023.project.common.robot.AbstractRobot;

/**
 * Interface for Environment
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see AbstractRobot
 */
public interface Environment {

    /**
     * Function for adding robot to environment
     * @param robot robot to be added
     * @return true if robot was added, false otherwise
     */
    boolean addRobot(AbstractRobot robot);

    /**
     * Function for creating obstacle at position
     * @param row Row where obstacle should be created
     * @param col Column where obstacle should be created
     * @return true if obstacle was created, false otherwise
     */
    boolean createObstacleAt(int row, int col);

    /**
     * Function for checking if obstacle is at position
     * @param row Row where obstacle should be
     * @param col Column where obstacle should be
     * @return true if obstacle is present, false otherwise
     */
    boolean obstacleAt(int row, int col);

    /**
     * Function for checking if robot is at position
     * @param p Position where robot should be
     * @return true if robot is present, false otherwise
     */
    boolean robotAt(Position p);

    /**
     * Function for getting robot from position
     * @param p Position where robot should be
     * @return robot at position
     */
    public AbstractRobot getRobotFromPosition(Position p);

    /**
     * Function for checking if position is in environment
     * @param pos Position to be checked
     * @return true if position is in environment, false otherwise
     */
    boolean containsPosition(Position pos);
}