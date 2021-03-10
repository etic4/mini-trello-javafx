package model;

import javafx.collections.ObservableList;
import java.util.Collections;


interface Container<E> {

    ObservableList<E> getMovables();

    default int size() {
        return getMovables().size();
    }

    default void add(E e) {
        getMovables().add(e);
    }

    default void remove(E e) {
        getMovables().remove(e);
    }

    default int getPosition(E e) {
        return getMovables().indexOf(e);
    }

    default boolean isLast(E e) {
        return getPosition(e) == size() - 1;
    }

    default boolean isFirst(E e) {
        return getPosition(e) == 0;
    }

    default E getNext(E e) {
        if(!isLast(e)) {
            return getMovables().get(getPosition(e) + 1);
        }
        return null;
    }

    default E getPrevious(E e) {
        if(!isFirst(e)) {
            return getMovables().get(getPosition(e) - 1);
        }
        return null;
    }

    default void moveUp(E e) {
        if(!isFirst(e)) {
            swap(e, getPrevious(e));
        }
    }

    default void moveDown(E e) {
        if(!isLast(e)) {
            swap(getNext(e), e);
        }
    }

    private void swap(E e1, E e2) {
        Collections.swap(getMovables(), getPosition(e1), getPosition(e2));
    }

}