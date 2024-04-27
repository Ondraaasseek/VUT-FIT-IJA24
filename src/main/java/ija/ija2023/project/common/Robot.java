/* @file Robot.java
 * @brief Interface for Robot
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common;

import ija.ija2023.project.tool.common.Position;
import ija.ija2023.project.tool.common.ToolRobot;

public interface Robot extends ToolRobot {
    void turn();
    int angle();
    boolean canMove();
    boolean move();
    Position getPosition();
}
