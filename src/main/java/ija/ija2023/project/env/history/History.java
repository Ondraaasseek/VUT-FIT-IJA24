/** 
 * @file History.java
 * @brief Class for History
 * @author Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.project.env.history;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Pair> history = new ArrayList<Pair>();
    private int index = -1;

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

    public void push(Date d, Memento m) {
        if (index < history.size() - 1) {
            history = history.subList(0, index + 1);
        }
        history.add(new Pair(d, m));
        index++;
    }

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

    public int size() {
        return history.size();
    }

    public void removeAfter(int index) {
        if (index < 0 || index >= history.size()) {
            return;
        }
        history = history.subList(0, index + 1);
        this.index = index;
    }
}
