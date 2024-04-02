package ija.ija2023.homework2.tool.common;

import java.util.List;

public abstract class AbstractObservableRobot extends Object implements ToolRobot {

    List<Observable.Observer> observers;

    public AbstractObservableRobot() {
        this.observers = new java.util.ArrayList<Observable.Observer>();
    }

    public void addObserver(Observable.Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observable.Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        for (Observable.Observer o : this.observers) {
            o.update(this);
        }
    }
}