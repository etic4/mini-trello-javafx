package model;

import direction.Direction;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;


public class Column extends EntitledContainer<Card> implements History<Column> {
    private int id;
    private int position;

    private Board board;

    public Column(Board board, String title) {
        super(title);
        setBoard(board);
        board.add(this);
        setPositionInBoard();
        if (this.getTitle().equals("")) {
            setTitle("Column " + board.size());
        }
    }

    // constructeur pour backend
    Column(int id, String title, int position) {
        super(title);
        this.id = id;
        this.position = position;
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

    void setInBoard(Board board) {
        setBoard(board);
        board.add(this.getPosition(), this);

        updateAllColumnsPosition();
    }

    private void updateAllColumnsPosition() {
        for (var column : getBoard().getColumns()) {
            column.setPositionInBoard();
        }
    }

    void setPositionInBoard() {
        position = getBoard().getPositionInArray(this);
    }

    int getPosition() {
        return position;
    }

    ObservableList<Card> getCards() {
        return getMovables();
    }

    Column moveLeft() {
        var otherColumn = getBoard().moveUp(this);
        setPositionInBoard();
        otherColumn.setPositionInBoard();

        return otherColumn;
    }

    Column moveRight() {
        var otherColumn = getBoard().moveDown(this);
        setPositionInBoard();
        otherColumn.setPositionInBoard();

        return otherColumn;
    }

    void delete() {
        getBoard().remove(this);
        updateAllColumnsPosition();
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
                boardFacade.restoreColumn(this);
        }

        return newMemento;
    }
}