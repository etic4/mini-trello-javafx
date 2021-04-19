package model;


public class Board extends EntitledContainer<Column> implements History<Board> {

    public Board(String title) {
        super(title);
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

    public boolean isRestorable(Memento<Board> memento) {
        return true;
    }

}