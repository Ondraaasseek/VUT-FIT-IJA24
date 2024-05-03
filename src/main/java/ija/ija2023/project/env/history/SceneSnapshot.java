/** 
 * @file SceneSnapshot.java
 * @brief Class for SceneSnapshot
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env.history;

import java.util.List;

import ija.ija2023.project.common.robot.AbstractRobot;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.room.AutonomousRobot;

/**
 * Class for SceneSnapshot
 * 
 * @version 1.0
 * @since 2024-05-02
 * 
 * @see AbstractRobot
 * @see ControlledRobot
 * @see AutonomousRobot
 */
public class SceneSnapshot {
    /** List of robots */
    public List<AbstractRobot> robots = new java.util.ArrayList<AbstractRobot>();

    /**
     * Constructor for SceneSnapshot
     * @param robots List of robots
     */
    public SceneSnapshot(List<AbstractRobot> robots) {
        // for each robot in robots, create new robot and add it to the list
        for (AbstractRobot robot : robots) {
            if (robot instanceof ControlledRobot) {
                this.robots.add(new ControlledRobot(robot.getId(), robot.getAngle(), robot.getPixelPosition()));
            } else {
                this.robots.add(new AutonomousRobot(robot.getId(), robot.getAngle(), robot.getPixelPosition()));
            }
        }
    }
}
