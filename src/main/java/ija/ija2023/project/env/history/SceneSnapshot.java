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

public class SceneSnapshot {
    public List<AbstractRobot> robots = new java.util.ArrayList<AbstractRobot>();

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
