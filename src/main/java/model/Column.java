package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Column extends EntitledContainer<Card> implements History {

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

    public BooleanProperty isFirstProperty() {
        return new SimpleBooleanProperty(getBoard().isFirst(this));
    }

    public BooleanProperty isLastProperty() {
        return new SimpleBooleanProperty(getBoard().isLast(this));
    }

    @Override
    public String toString() {
        return "colonne \"" + getTitle() +  "\"";
    }

    @Override
    public Memento save(MemType memType) {
        return new ColumnMemento(this, memType);
    }

    @Override
    public void restore(Memento memento) {
        if (memento instanceof ColumnMemento) {
            var columnMemento = (ColumnMemento) memento;
            columnMemento.restore();
        }
    }
}