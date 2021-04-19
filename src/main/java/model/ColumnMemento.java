package model;

public class ColumnMemento implements Memento<Column> {
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

    Board getBoard() {
        return board;
    }

    int getPosition() {
        return position;
    }

    String getTitle() {
        return title;
    }

    Column getColumn() {
        return column;
    }

    MemType getMemType() {
        return memType;
    }
}
