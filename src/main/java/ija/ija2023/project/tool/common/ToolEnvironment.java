/* @file ToolEnvironment.java
 * @brief Interface for ToolEnvironment
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.tool.common;

import java.util.List;

public interface ToolEnvironment {
    boolean obstacleAt(Position p);
    int rows();
    int cols();
    List<ToolRobot> robots();
}