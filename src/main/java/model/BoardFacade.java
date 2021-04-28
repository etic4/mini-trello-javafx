package model;

import direction.Direction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardFacade {
    private final DaoFactory dao;
    private final Board board;

    public BoardFacade(Board board) {
        this.board = board;
        this.dao = new DaoFactory(DaoBackendType.SQLITE);
    }

    public Board getBoard() {
        return board;
    }

    public ObservableList<Column> getColumns(Board board) {
        return FXCollections.unmodifiableObservableList(board.getColumns());
    }

    public ObservableList<Card> getCards(Column column) {
        return FXCollections.unmodifiableObservableList(column.getCards());
    }


    // --- Title ---

    public void setTitle(Entitled entitled, String title) {
        entitled.setTitle(title);

        if (entitled instanceof Board) {
            dao.getBoardDao().update(board);
        } else if (entitled instanceof Column) {
            var column = (Column) entitled;
            dao.getColumnDao().update(column);
        } else {
            var card = (Card) entitled;
            dao.getCardDao().update(card);
        }
    }

    // --- Board ---

    // TODO: changer ça

    public Column addColumn() {
        var column = new Column(board);
        return dao.getColumnDao().save(column);
    }


    // ---  Column ---

    public Card addCard(Column column) {
        var card = new Card(column);
        return dao.getCardDao().save(card);
    }

    public void delete(Column column) {
        column.delete();
        dao.getColumnDao().delete(column);
    }

    public void move(Column column, Direction direction) {
        switch (direction) {
            case LEFT:
                column.moveLeft();
                break;
            case RIGHT:
                column.moveRight();
        }
        dao.getColumnDao().update(column);
    }

    // ---  Card ---

    public Column getColumn(Card card) {
        return card.getColumn();
    }

    public void delete(Card card) {
        card.delete();
        dao.getCardDao().delete(card);
    }

    public Column move(Card card, Direction direction) {
        var column = card.getColumn();

        switch (direction) {
            case UP:
                card.moveUp();
                break;
            case DOWN:
                card.moveDown();
                break;
            case LEFT:
                column = card.moveLeft();
                break;
            case RIGHT:
                column = card.moveRight();
        }

        dao.getCardDao().update(card);

        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            dao.getColumnDao().update(column);
        }

        return column;
    }

    public Column getMoveDestinationColumn(Card card, Direction direction) {
        if (direction.equals(Direction.LEFT)) {
            return card.getPreviousColumn();
        }
        else if (direction.equals(Direction.RIGHT)) {
            return card.getNextColumn();
        }
        return null;
    }
}