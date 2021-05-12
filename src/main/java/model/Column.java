package model;

import direction.Direction;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;


public class Column extends EntitledContainer<Card> implements History<Column> {
    private int id;

    private Board board;

    public Column(Board board, String title) {
        super(title);
        board.addColumn(this);

        if (this.getTitle().equals("")) {
            setTitle("Column " + board.size());
        }
    }

    // constructeur pour backend
    Column(int id, String title) {
        super(title);
        this.id = id;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    int getBoardId() {
        return this.board.getId();
    }

    public Board getBoard() {
        return board;
    };

    void setBoard(Board board) {
        this.board = board;
    }

    int getPosition() {
        return getBoard().getPositionInArray(this);
    }

    ObservableList<Card> getCards() {
        return getMovables();
    }

    void addCard(Card card) {
        card.setColumn(this);
        add(card);
    }

    void addCard(int position, Card card) {
        card.setColumn(this);
        add(position, card);
    }

    Column moveLeft() {
        return getBoard().moveUp(this);
    }

    Column moveRight() {
        return getBoard().moveDown(this);
    }

    void delete() {
        getBoard().remove(this);
    }

    public ReadOnlyBooleanProperty isFirstProperty() {
        return new SimpleBooleanProperty(getBoard().isFirst(this));
    }

    public ReadOnlyBooleanProperty isLastProperty() {
        return new SimpleBooleanProperty(getBoard().isLast(this));
    }

    @Override
    public String toString() {
        return "colonne \"" + getTitle() +  "\"";
    }

    @Override
    public Memento<Column> getMemento(MemType memType) {
        return new ColumnMemento(this, memType);
    }

    @Override
    public Memento<Column> restore(Memento<Column> memento) {
        var columnMemento = (ColumnMemento) memento;
        var boardFacade = new BoardFacade(this.getBoard());

        var newMemento = getNewMemento(columnMemento.getMemType());

        switch (columnMemento.getMemType()) {
            case TITLE:
                boardFacade.setTitle(this, columnMemento.getTitle());
                break;
            case POSITION:
                if (this.getPosition() > columnMemento.getPosition()) {
                    boardFacade.move(this, Direction.LEFT);
                } else {
                    boardFacade.move(this, Direction.RIGHT);
                }
                break;
            case ADD:
                boardFacade.delete(this);
                break;
            case DELETE:
                boardFacade.restoreColumn(this, columnMemento.getPosition());
        }

        return newMemento;
    }
}