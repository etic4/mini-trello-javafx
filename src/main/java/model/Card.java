package model;


public class Card extends Entitled implements Movable<Card, Column> {

    private Column column;

    public Card(Column column, String title) {
        super(title);

        this.column = column;
        column.add(this);
    }

    public Card(Column column) {
        this(column, "");
    }

    @Override
    public Card getMovable() {
        return this;
    }

    @Override
    public Column getContainer() {
        return column;
    }

    @Override
    public void setContainer(Column column) {
        this.column = column;
    }

    public void moveLeft() {
        var board = column.getContainer();
        var previous = board.getPrevious(column);

        if (previous != null) {
            switchTo(previous, this);
        }
    }

    public void moveRight() {
        var board = column.getContainer();
        var next = board.getNext(column);

        if (next != null) {
            switchTo(next, this);
        }
    }

    private void switchTo(Column newColumn, Card card) {
        column.remove(card);
        newColumn.add(card);
        card.setContainer(newColumn);

    }

}