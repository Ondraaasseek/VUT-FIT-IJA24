package ija.ija2023.project.common.robot;

import ija.ija2023.project.common.Environment;
import ija.ija2023.project.common.Position;
import ija.ija2023.project.room.AutonomousRobot;
import ija.ija2023.project.room.ControlledRobot;

/**
 * Class for RobotFactory
 *
 * @version 1.0
 * @since 2024-05-02
 *
 * @see AbstractRobot
 * @see Environment
 * @see Position
 */
public class RobotFactory {
    public static AbstractRobot create(Environment env, Position pos) {
        return create(env, pos, false);
    }

    /**
     * Create robot in the environment at the specified position with the
     * specified control type (controlled or autonomous)
     * @param env Environment
     * @param pos Position
     * @param controlled boolean which specified if the robot is controlled or autonomous
     * @return Robot or null if the robot could not be created
     */
    public static AbstractRobot create(Environment env, Position pos, boolean controlled) {
        // check if env or pos is null
        if (env == null || pos == null) {
            return null;
        }
        // check if position is within the environment
        if (!env.containsPosition(pos)) {
            return null;
        }
        // create robot
        AbstractRobot robot;
        if (controlled) {
            // create controlled robot
            robot = new ControlledRobot(env, pos);
        } else {
            // create robot
            robot = new AutonomousRobot(env, pos);
        }
        // add robot to the environment
        if (!env.addRobot(robot)) {
            return null;
        }
        // return new robot
        return robot;
    }
}
