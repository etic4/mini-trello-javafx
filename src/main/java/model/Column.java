package model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Column extends EntitledContainer<Card> implements History<Column> {

    private final Board board;

    public Column(Board board, String title) {
        super(title);
        this.board = board;
        board.add(this);

        if (this.getTitle().equals("")) {
            setTitle("Column " + board.size());
        }
    }

    public Column(Board board) {
        this(board, "");
    }

    Board getBoard() {
        return board;
    };

    void moveLeft() {
        getBoard().moveUp(this);
    }

    void moveRight() {
        getBoard().moveDown(this);
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
    public Memento<Column> save(MemType memType) {
        return new ColumnMemento(this, memType);
    }

    @Override
    public void restore(Memento<Column> memento) {
        var columnMemento = (ColumnMemento) memento;

        switch (columnMemento.getMemType()) {
            case TITLE:
                this.setTitle(columnMemento.getTitle());
                break;
            case POSITION:
                board.remove(this);
                board.add(columnMemento.getPosition(), this);
                break;
            case ADD:
                columnMemento.getBoard().remove(columnMemento.getColumn());
                break;
            case DELETE:
                columnMemento.getBoard().add(columnMemento.getPosition(), columnMemento.getColumn());
        }
    }
}