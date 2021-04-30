package model;

import javafx.collections.ObservableList;

public class Board extends EntitledContainer<Column> implements History<Board> {
    private int id;

    public Board(int id, String title) {
        super(title);
        setId(id);
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    ObservableList<Column> getColumns() {
        return getMovables();
    }

    void addColumn(Column column) {
        column.setBoard(this);
        add(column);
    }

    void addColumn(int position, Column column) {
        column.setBoard(this);
        add(position, column);
    }

    @Override
    public Memento<Board> getMemento(MemType memType) {
        return new BoardMemento(this.getTitle(), memType);
    }

    @Override
    public Memento<Board> restore(Memento<Board> memento) {
        var boardMemento = (BoardMemento) memento;
        var boardFacade = new BoardFacade(this);

        var newMemento = getNewMemento(MemType.TITLE);

        boardFacade.setTitle(this, boardMemento.get_title());

        return newMemento;
    }

}