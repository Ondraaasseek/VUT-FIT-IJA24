/* @file ControlledRobot.java
 * @brief Class for ControlledRobot
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.room;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.Position;
import ija.ija2023.project.common.robot.AbstractRobot;

/**
 * Class for AutonomousRobot
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see AbstractRobot
 * @see Environment
 * @see Position
 */
public class AutonomousRobot extends AbstractRobot {

    /**
     * Constructor for AutonomousRobot
     * @param env Environment where the robot should be created
     * @param pos Position where the robot should be created
     */
    public AutonomousRobot(Environment env, Position pos) {
        super(env, pos);
    }
}
