/* @file Robot.java
 * @brief Interface for Robot
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common.robot;

import ija.ija2023.project.common.Position;

public interface Robot {
    void turn();
    void turn(int n);
    int getAngle();
    Position getPosition();
}
