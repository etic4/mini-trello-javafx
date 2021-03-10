package model;

public class CardMemento implements Memento {
    private Column column;
    private int position;
    private String title;
    private Card card;
    private final MemType memType;

    CardMemento(Card card, MemType memType) {
        this.memType = memType;

        switch (memType) {
            case TITLE:
                this.title = card.getTitle();
                break;
            case ADD:
            case DELETE:
                this.card = card;
            case POSITION:
                this.column = card.getColumn();
                this.position = card.getPosition();
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

    Card getCard() {
        return card;
    }

    MemType getMemType() {
        return memType;
    }
}
