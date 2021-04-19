package model;

public class BoardMemento implements Memento<Board> {
    private final String title;
    private final MemType memType;

    BoardMemento(String title, MemType memType) {
        this.memType = memType;
        this.title = title;
    }

    String get_title() {
        return title;
    }
}
