package ija.ija2023.project.common;

import ija.ija2023.project.room.AutonomousRobot;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.tool.common.Position;

public class RobotFactory {
    public static AbstractRobot create(Environment env, Position pos) {
        return create(env, pos, false);
    }

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
        // return new controlled robot
        return robot;
    }
}
