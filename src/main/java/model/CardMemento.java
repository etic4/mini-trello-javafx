package model;

public class CardMemento implements Memento<Card> {
    private Column column;
    private int position;
    private String title;
    private final MemType memType;


    CardMemento(Card card, MemType memType) {
        this.memType = memType;

        switch (memType) {
            case TITLE:
                title = card.getTitle();
                break;
            case ADD:
            case DELETE:
            case POSITION:
                column = card.getColumn();
                position = card.getPosition();
        }
    }

    Column getColumn() {
        return column;
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
