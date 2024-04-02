package ija.ija2023.homework2.tool.common;

import java.util.List;

public abstract class AbstractObservableRobot extends Object implements ToolRobot {

    List<Observable.Observer> observers;

    public AbstractObservableRobot() {
        this.observers = new java.util.ArrayList<Observable.Observer>();
    }

    public void addObserver(Observable.Observer o) {}

    public void removeObserver(Observable.Observer o) {}

    public void notifyObservers() {}
}