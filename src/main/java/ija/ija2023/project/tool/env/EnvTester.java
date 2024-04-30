/* @file EnvTester.java
 * @brief Class for EnvTester
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.tool.env;

import ija.ija2023.project.tool.common.AbstractObservableRobot;
import ija.ija2023.project.tool.common.ToolRobot;
import ija.ija2023.project.tool.common.ToolEnvironment;

import java.util.List;

public class EnvTester extends Object {

    private final ToolEnvironment env;

    public EnvTester(ToolEnvironment env){
        // Konstruktor inicializující MazeTester.
        this.env = env;
    }
    public List<ToolRobot> checkEmptyNotification(){
        // Ověří, že žádný objekt (view) nebyl notifikován.
        List<ToolRobot> notifiedRobots = new java.util.ArrayList<ToolRobot>();
        for (ToolRobot robot : this.env.robots()){
            if (robot instanceof AbstractObservableRobot){
                AbstractObservableRobot observableRobot = (AbstractObservableRobot) robot;
                if (observableRobot.notificationCount > 0){
                    notifiedRobots.add(robot);
                }
            }
        }
        return notifiedRobots;
    }

    public boolean checkNotification(StringBuilder msg, ToolRobot obj){
        // Ověří správný průběh notifikace při akci nad objekty Observable. Observable (robot) informuje (notifikuje) o změně závislé objekty Observer (view, grafická podoba). Ověřuje, zda notifikaci zaslal správný objekt ve správném počtu. Po ověření smaže záznamy o notifikacích.
        if (!(obj instanceof AbstractObservableRobot)){
            msg.append("Objekt není typu AbstractObservableRobot.");
            return false;
        }
        AbstractObservableRobot observableRobot = (AbstractObservableRobot) obj;
        if (observableRobot.notificationCount != observableRobot.observers.size()){
            msg.append("Robot neinformoval všechny view.");
            return false;
        }
        observableRobot.notificationCount = 0;
        return true;
    }
}