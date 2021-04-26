package model;

import javafx.collections.ObservableList;

public class Board extends EntitledContainer<Column> implements History<Board> {
    private int id;

    public Board(int id, String title) {
        super(title);
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

    @Override
    public Memento<Board> save(MemType memType) {
        return new BoardMemento(this.getTitle(), memType);
    }

    @Override
    public void restore(Memento<Board> memento) {
        var boardMemento = (BoardMemento) memento;
        setTitle(boardMemento.get_title());
    }

}