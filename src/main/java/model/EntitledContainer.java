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

    int getPosition(E e) {
        return getMovables().indexOf(e);
    }

    boolean isLast(E e) {
        return getPosition(e) == size() - 1;
    }

    boolean isFirst(E e) {
        return getPosition(e) == 0;
    }

    E getNext(E e) {
        if(!isLast(e)) {
            return getMovables().get(getPosition(e) + 1);
        }
        return null;
    }

    E getPrevious(E e) {
        if(!isFirst(e)) {
            return getMovables().get(getPosition(e) - 1);
        }
        return null;
    }

    void moveUp(E e) {
        if(!isFirst(e)) {
            swap(e, getPrevious(e));
        }
    }

    void moveDown(E e) {
        if(!isLast(e)) {
            swap(getNext(e), e);
        }
    }

    private void swap(E e1, E e2) {
        Collections.swap(getMovables(), getPosition(e1), getPosition(e2));
    }
}
