package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Card extends Entitled implements History {

    private Column column;

    public Card(Column column, String title) {
        super(title);
        this.column = column;
        column.add(this);

        if (getTitle().equals("")) {
            setTitle("Card " + column.size());
        }
    }

    public Card(Column column) {
        this(column, "");
    }

    Column getColumn() {
        return column;
    };

    void setColumn(Column column) {
        this.column = column;
    };

    int getPosition() {
        return getColumn().getPosition(this);
    }

    Board getBoard() {
        return getColumn().getBoard();
    }

    Column getNextColumn() {
        return getBoard().getNext(getColumn());
    }

    Column getPreviousColumn() {
        return getBoard().getPrevious(getColumn());
    }

    void moveUp() {
        getColumn().moveUp(this);
    }

    void moveDown() {
        getColumn().moveDown(this);
    }

    void moveLeft() {
        var previous = getPreviousColumn();

        if (previous != null) {
            switchTo(previous, this);
        }
    }

    void moveRight() {
        var next = getNextColumn();

        if (next != null) {
            switchTo(next, this);
        }
    }

    void delete() {
        getColumn().remove(this);
    }

    public ReadOnlyBooleanProperty isFirstProperty() {
        return new SimpleBooleanProperty(getColumn().isFirst(this));
    }

    public ReadOnlyBooleanProperty isLastProperty() {
        return new SimpleBooleanProperty(getColumn().isLast(this));
    }

    public ReadOnlyBooleanProperty isColumnFirstProperty() {
        return getColumn().isFirstProperty();
    }

    public ReadOnlyBooleanProperty isColumnLastProperty() {
        return getColumn().isLastProperty();
    }

    void switchTo(Column newColumn, Card card) {
        switchTo(newColumn, card, newColumn.size());
    }

    void switchTo(Column newColumn, Card card, int position) {
        column.remove(card);
        newColumn.add(position, card);
        card.setColumn(newColumn);
    }

    @Override
    public String toString() {
        return "carte \"" + getTitle() + "\"";
    }

    @Override
    public Memento save(MemType memType) {
        return new CardMemento(this, memType);
    }

}