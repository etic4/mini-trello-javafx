package model;


public class Board extends EntitledContainer<Column> implements History {

    public Board(String title) {
        super(title);
    }

    @Override
    public Memento save(MemType memType) {
        return new BoardMemento(this, memType);
    }

}