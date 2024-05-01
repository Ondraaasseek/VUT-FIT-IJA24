/* @file Environment.java
 * @brief Interface for Environment
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common;

import ija.ija2023.project.common.robot.AbstractRobot;

public interface Environment {
    boolean addRobot(AbstractRobot robot);
    boolean createObstacleAt(int row, int col);
    boolean obstacleAt(int row, int col);
    boolean robotAt(Position p);
    public AbstractRobot getRobotFromPosition(Position p);
    boolean containsPosition(Position pos);
}