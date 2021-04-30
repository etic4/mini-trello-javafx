package model;

public class CardMemento implements Memento<Card> {
    private final Card card;
    private Column column;
    private int position;
    private String title;
    private final MemType memType;

    CardMemento(Card card, MemType memType) {
        this.memType = memType;
        this.card = card;

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

    Card getCard() {
        return card;
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
