package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public interface Movable<E, P extends Container<E>> {

    P getContainer();

    void setContainer(P container);

    E getMovable();

    default void moveUp() {
        getContainer().moveUp(getMovable());
    }

    default void moveDown() {
        getContainer().moveDown(getMovable());
    }

    default void delete() {
        getContainer().remove(getMovable());
    }

    default BooleanProperty isFirstProperty() {
        return new SimpleBooleanProperty(getContainer().isFirst(getMovable()));
    }

    default BooleanProperty isLastProperty() {
        return new SimpleBooleanProperty(getContainer().isLast(getMovable()));
    }

}