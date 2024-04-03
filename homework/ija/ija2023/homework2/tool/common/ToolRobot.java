/* @file ToolRobot.java
 * @brief Interface for ToolRobot
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.homework2.tool.common;

public interface ToolRobot extends Observable {
    int angle();
    void turn(int n);
    Position getPosition();
}