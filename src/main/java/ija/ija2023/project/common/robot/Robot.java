/** 
 * @file Robot.java
 * @brief Interface for Robot
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.common.robot;

import ija.ija2023.project.common.Position;

/**
 * Interface for Robot
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see Position
 */
public interface Robot {

    /**
     * Rotate the robot by 45 degrees to the right
     */
    void turn();

    /**
     * Rotate the robot by n*45 degrees to the right
     * @param n number of 45 degrees rotations
     */
    void turn(int n);

    /**
     * Get robots angle
     * @return angle
     */
    int getAngle();

    /**
     * Sets robots angle
     * @param angle angle to set
     */
    void setAngle(int angle);

    /**
     * Get robots position
     * @return position
     */
    Position getPosition();
}
