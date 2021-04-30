package model;

import direction.Direction;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Card extends Entitled implements History<Card> {
    private int id;
    private Column column;


    public Card(Column column, String title) {
        super(title);
        column.addCard(this);

        if (getTitle().equals("")) {
            setTitle("Card " + column.size());
        }
    }

    // constructeur pour backend
    Card(int id, String title) {
        super(title);
        this.id = id;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    int getColumnId() {
        return column.getId();
    }

    Column getColumn() {
        return column;
    }

    void setColumn(Column column) {
        this.column = column;
    }

    int getPosition() {
        return getColumn().getPositionInArray(this);
    }

    public Board getBoard() {
        return getColumn().getBoard();
    }

    Column getNextColumn() {
        return getBoard().getNext(getColumn());
    }

    Column getPreviousColumn() {
        return getBoard().getPrevious(getColumn());
    }

    Card moveUp() {
        return getColumn().moveUp(this);
    }

    Card moveDown() {
        return getColumn().moveDown(this);
    }

    Column moveLeft() {
        var destColumn = getPreviousColumn();

        if (destColumn != null) {
            switchTo(destColumn, this);
        }

        return destColumn;
    }

    Column moveRight() {
        var destColumn = getNextColumn();

        if (destColumn != null) {
            switchTo(destColumn, this);
        }

        return destColumn;
    }

    void delete() {
        var column = getColumn();
        column.remove(this);
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
    public Memento<Card> getMemento(MemType memType) {
        return new CardMemento(this, memType);
    }

    @Override
    public Memento<Card> restore(Memento<Card> memento) {
        var cardMemento = (CardMemento) memento;
        var boardFacade = new BoardFacade(this.getBoard());

        var newMemento = getNewMemento(cardMemento.getMemType());

        switch (cardMemento.getMemType()) {
            case TITLE:
                boardFacade.setTitle(this, cardMemento.getTitle());
                break;
            case POSITION:
                if (this.getColumn() == cardMemento.getColumn()) {
                    if (this.getPosition() > cardMemento.getPosition()) {
                        boardFacade.move(this, Direction.UP);
                    } else {
                        boardFacade.move(this, Direction.DOWN);
                    }
                } else {
                    boardFacade.delete(this);
                    boardFacade.restoreCard(this, cardMemento.getColumn(), cardMemento.getPosition());
                }
                break;
            case ADD:
                boardFacade.delete(this);
                break;
            case DELETE:
                boardFacade.restoreCard(this, cardMemento.getColumn(), cardMemento.getPosition());
        }

        return newMemento;
    }

}