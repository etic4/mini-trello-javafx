package model;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Card extends Entitled implements History {

    private Column column;

    public Card(Column column, String title) {
        super(title);
        this.column = column;
        column.add(this);

        if (this.getTitle().equals("")) {
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

    public BooleanProperty isFirstProperty() {
        return new SimpleBooleanProperty(getColumn().isFirst(this));
    }

    public BooleanProperty isLastProperty() {
        return new SimpleBooleanProperty(getColumn().isLast(this));
    }

    public BooleanProperty isColumnFirstProperty() {
        return getColumn().isFirstProperty();
    }

    public BooleanProperty isColumnLastProperty() {
        return getColumn().isLastProperty();
    }

    private void switchTo(Column newColumn, Card card) {
        switchTo(newColumn, card, newColumn.size());
    }

    private void switchTo(Column newColumn, Card card, int position) {
        column.remove(card);
        newColumn.add(position, card);
        card.setColumn(newColumn);
    }

    @Override
    public String toString() {
        return "carte \"" + getTitle() + "\"";
    }

    @Override
    public CardMemento save(MemType memType) {
        return new CardMemento(this, memType);
    }

    @Override
    public void restore(Memento memento) {
        if (memento instanceof CardMemento) {
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
}