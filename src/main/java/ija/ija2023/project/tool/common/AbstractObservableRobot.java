/* @file AbstractObservableRobot.java
 * @brief class for AbstractObservableRobot
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.tool.common;

import java.util.List;

import ija.ija2023.project.tool.view.RobotView;

public abstract class AbstractObservableRobot extends Object implements ToolRobot {

    public List<RobotView> observers;
    public int notificationCount = 0;

    public AbstractObservableRobot() {
        this.observers = new java.util.ArrayList<RobotView>();
    }

    public void addObserver(Observable.Observer o) {
        this.observers.add((RobotView)o);
    }

    public void removeObserver(Observable.Observer o) {
        this.observers.remove((RobotView)o);
    }

    public void notifyObservers() {
        for (RobotView o : this.observers) {
            notificationCount++;
            o.update(this);
        }
    }
}