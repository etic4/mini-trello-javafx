package model;

public class BoardMemento implements Memento {
    private final String title;
    private final MemType memType;

    BoardMemento(Board board, MemType memType) {
        this.memType = memType;
        this.title = board.getTitle();
    }

    String getTitle() {
        return title;
    }

    MemType getMemType() {
        return memType;
    }
}
