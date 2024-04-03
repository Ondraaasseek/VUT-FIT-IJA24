/* @file Observable.java
 * @brief Interface for Observable
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.homework2.tool.common;

public interface Observable {
    void addObserver(Observable.Observer o);
    void removeObserver(Observable.Observer o);
    void notifyObservers();

    // Observer interface
    public static interface Observer {
        void update(Observable o);
    }
}