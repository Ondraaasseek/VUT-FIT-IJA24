package ija.ija2023.project.env.history;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Pair> history = new ArrayList<Pair>();
    private int virtualSize = 0;

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
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(new Pair(d, m));
        virtualSize = history.size();
    }

    public Date undo() {
        Pair pair = getUndo();
        if (pair == null) {
            return null;
        }
        pair.getMemento().restore();
        return pair.getDate();
    }

    public Date redo() {
        Pair pair = getRedo();
        if (pair == null) {
            return null;
        }
        pair.getMemento().restore();
        return pair.getDate();
    }
    
    private Pair getUndo() {
        if (virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    private Pair getRedo() {
        if (virtualSize == history.size()) {
            return null;
        }
        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }
}
