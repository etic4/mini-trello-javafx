package model;

public class CardMemento implements Memento {
    private Column column;
    private int position;
    private String title;
    private final Card card;
    private final MemType memType;


    CardMemento(Card card, MemType memType) {
        this.card = card;
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

    public void restore() {
        switch (memType) {
            case TITLE:
                card.setTitle(title);
                break;
            case ADD:
                column.remove(card);
                break;
            case DELETE:
                column.add(position, card);
            case POSITION:
                card.switchTo(column, card, position);
                break;
        }
    }
}
