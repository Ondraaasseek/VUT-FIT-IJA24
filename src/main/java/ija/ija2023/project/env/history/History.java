/** 
 * @file History.java
 * @brief Class for History
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env.history;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for History
 *
 * @version 1.0
 * @since 2024-05-02
 */
public class History {
    private List<Pair> history = new ArrayList<Pair>();
    private int index = -1;

    /**
     * Pair class for History
     * @version 1.0
     * @since 2024-05-02
     * 
     * @see Date
     * @see Memento
     */
    private class Pair {
        // date and time of a memento
        Date date;
        Memento memento;
        Pair(Date d, Memento m) {
            date = d;
            memento = m;
        }

        private Date getDate() {
            return date;
        }

        private Memento getMemento() {
            return memento;
        }
    }

    /**
     * Push a new memento to the history
     * @param d Date
     * @param m Memento
     */
    public void push(Date d, Memento m) {
        if (index < history.size() - 1) {
            history = history.subList(0, index + 1);
        }
        history.add(new Pair(d, m));
        index++;
    }

    /**
     * Restore previous memento
     * @return Date of the restored memento
     */
    public Date undo() {
        if (index < 0) {
            return null;
        }
        Pair pair = history.get(index--);
        if (pair == null) {
            return null;
        }
        pair.getMemento().restore();
        return pair.getDate();
    }

    /**
     * Restore next memento
     * @return Date of the restored memento
     */
    public Date redo() {
        if (index >= history.size() - 1) {
            return null;
        }
        Pair pair = history.get(++index);
        if (pair == null) {
            return null;
        }
        pair.getMemento().restore();
        return pair.getDate();
    }

    /**
     * Get size of the history
     * @return Size of the history
     */
    public int size() {
        return history.size();
    }

    /**
     * Remove all mementos after the given index
     * @param index Index
     */
    public void removeAfter(int index) {
        if (index < 0 || index >= history.size()) {
            return;
        }
        history = history.subList(0, index + 1);
        this.index = index;
    }
}
