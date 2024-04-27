/* @file Observable.java
 * @brief Interface for Observable
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.tool.common;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();

    // Observer interface
    public static interface Observer {
        void update(Observable o);
    }
}