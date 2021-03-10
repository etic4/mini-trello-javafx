package model;

import direction.Direction;
import javafx.collections.ObservableList;
import main.Data;

public class BoardFacade {

    private final Board board;

    public BoardFacade() {
        board = Data.init();
    }

    public Board getBoard() {
        return board;
    }

    public ObservableList<Column> getColumns(Board board) {
        return board.getMovables();
    }

    public ObservableList<Card> getCards(Column column) {
        return column.getMovables();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  BOARD

    public void addColumn(Board board) {
        new Column(board);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  COLUMN

    public void addCard(Column column) {
        new Card(column);
    }

    public void delete(Column column) {
        column.delete();
    }

    public void move(Column column, Direction direction) {
        switch (direction) {
            case LEFT:
                column.moveUp();
                break;
            case RIGHT:
                column.moveDown();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CARD

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
}