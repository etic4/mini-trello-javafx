package model;

public class ColumnMemento implements Memento {
    private Board board;
    private int position;
    private String title;
    private final Column column;
    private final MemType memType;

    ColumnMemento(Column column, MemType memType) {
        this.column = column;
        this.memType = memType;

        switch (memType) {
            case TITLE:
                title = column.getTitle();
                break;
            case ADD:
            case DELETE:
            case POSITION:
                board = column.getBoard();
                position = board.getPosition(column);
        }
    }

    public void restore() {
        switch (memType) {
            case TITLE:
                column.setTitle(title);
                break;
            case POSITION:
                board.remove(column);
                board.add(position, column);
                break;
            case ADD:
                board.remove(column);
                break;
            case DELETE:
                board.add(position, column);
        }
    }
}
