package model;

public class BoardMemento implements Memento {
    private final Board board;
    private final String title;
    private final MemType memType;

    BoardMemento(Board board, MemType memType) {
        this.board = board;
        this.memType = memType;
        this.title = board.getTitle();
    }

    void restore() {
        board.setTitle(title);
    }
}
