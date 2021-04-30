package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collections;

/*
* Un container qui étend Entitled
* */
public abstract class EntitledContainer<E> extends Entitled {
    private final ObservableList<E> movables = FXCollections.observableArrayList();

    public EntitledContainer(String title) {
        super(title);
    }

    ObservableList<E> getMovables() {
        return movables;
    };

    int size() {
        return getMovables().size();
    }

    void add(E e) {
        getMovables().add(e);
    }

    void add(int i, E e) {
        getMovables().add(i, e);
    }

    void remove(E e) {
        getMovables().remove(e);
    }

    int getPositionInArray(E e) {
        return getMovables().indexOf(e);
    }

    boolean isLast(E e) {
        return getPositionInArray(e) == size() - 1;
    }

    boolean isFirst(E e) {
        return getPositionInArray(e) == 0;
    }

    E getNext(E e) {
        if(!isLast(e)) {
            return getMovables().get(getPositionInArray(e) + 1);
        }
        return null;
    }

    E getPrevious(E e) {
        if(!isFirst(e)) {
            return getMovables().get(getPositionInArray(e) - 1);
        }
        return null;
    }

    E moveUp(E e) {
        E other = null;

        if(!isFirst(e)) {
            other = getPrevious(e);
            swap(e, other);
        }

        return other;
    }

    E moveDown(E e) {
        E other = null;

        if(!isLast(e)) {
            other = getNext(e);
            swap(other, e);
        }

        return other;
    }

    private void swap(E e1, E e2) {
        Collections.swap(getMovables(), getPositionInArray(e1), getPositionInArray(e2));
    }
}
