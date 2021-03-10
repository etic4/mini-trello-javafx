package model;

import direction.Direction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardFacade {

    private final Board board;

    public BoardFacade(Board board) {
        this.board = board;
    }

    public BoardFacade(Column column) {
        this.board = column.getBoard();
    }

    public BoardFacade(Card card) {
        this.board = card.getColumn().getBoard();
    }

    public Board getBoard() {
        return board;
    }

    public ObservableList<Column> getColumns(Board board) {
        return FXCollections.unmodifiableObservableList(board.getMovables());
    }

    public ObservableList<Card> getCards(Column column) {
        return FXCollections.unmodifiableObservableList(column.getMovables());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  BOARD

    public Column addColumn(Board board) {
        return new Column(board);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  COLUMN

    public Card addCard(Column column) {
        return new Card(column);
    }

    public void delete(Column column) {
        column.delete();
    }

    public void move(Column column, Direction direction) {
        switch (direction) {
            case LEFT:
                column.moveLeft();
                break;
            case RIGHT:
                column.moveRight();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CARD

    public Column getColumn(Card card) {
        return card.getColumn();
    }

    public void delete(Card card) {
        card.delete();
    }

    public void move(Card card, Direction direction) {
        switch (direction) {
            case UP:
                card.moveUp();
                break;
            case DOWN:
                card.moveDown();
                break;
            case LEFT:
                card.moveLeft();
                break;
            case RIGHT:
                card.moveRight();
        }
    }

    public Column getMoveDestinationColumn(Card card, Direction direction) {
        if (direction.equals(Direction.LEFT)) {
            return card.getPreviousColumn();
        } else if (direction.equals(Direction.RIGHT)) {
            return card.getNextColumn();
        }
        return null;
    }

}