package model;

public class ColumnMemento implements Memento<Column> {
    private int position;
    private String title;
    private final MemType memType;


    ColumnMemento(Column column, MemType memType) {
        this.memType = memType;

        switch (memType) {
            case TITLE:
                title = column.getTitle();
                break;
            case ADD:
            case DELETE:
            case POSITION:
                position = column.getPosition();
        }
    }

    int getPosition() {
        return position;
    }

    String getTitle() {
        return title;
    }

    MemType getMemType() {
        return memType;
    }
}
