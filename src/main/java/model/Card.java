package model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Card extends Entitled implements History<Card> {
    private int id;

    private Column column;

    public Card(int id, String title, int position, int column_id) {
        super(title);
    }

    public Card(Column column, String title) {
        super(title);
        this.column = column;
        column.add(this);

        if (getTitle().equals("")) {
            setTitle("Card " + column.size());
        }
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
    public Card(Column column) {
        this(column, "");
    }


    public Board getBoard() {
        return getColumn().getBoard();
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

    Column moveLeft() {
        var previous = getPreviousColumn();

        if (previous != null) {
            switchTo(previous, this);
        }

        return previous;
    }

    Column moveRight() {
        var next = getNextColumn();

        if (next != null) {
            switchTo(next, this);
        }

        return next;
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
    public Memento<Card> save(MemType memType) {
        return new CardMemento(this, memType);
    }

    @Override
    public void restore(Memento<Card> memento) {
        var cardMemento = (CardMemento) memento;

        switch (cardMemento.getMemType()) {
            case POSITION:
                switchTo(cardMemento.getColumn(), this, cardMemento.getPosition());
                break;
            case TITLE:
                setTitle(cardMemento.getTitle());
                break;
            case ADD:
                cardMemento.getColumn().remove(cardMemento.getCard());
                break;
            case DELETE:
                cardMemento.getColumn().add(cardMemento.getPosition(), cardMemento.getCard());
        }
    }
}