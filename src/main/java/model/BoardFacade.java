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
        var columns = dao.getColumnDao().get_all(board.getId());
        var observableColumns = FXCollections.observableArrayList(columns);
        return FXCollections.unmodifiableObservableList(observableColumns);
    }

    public ObservableList<Card> getCards(Column column) {
        dao.getColumnDao().get_all(column.getId());
        return FXCollections.unmodifiableObservableList(column.getCards());
    }

    // reçoit une instance de column et retourne True si elle existe
    //   dans le board
    // permet déterminer si une carte est restorable (cas d'une colonne supprimée)

    public boolean isInBoard(Column column) {
        return board.getColumns().contains(column);
    }

    public boolean isInBoard(Card card) {
        var column = card.getColumn();
        return board.getColumns().contains(column) && column.getCards().contains(card);
    }

    // --- Title ---

    public void setTitle(Entitled entitled, String title) {
        entitled.setTitle(title);
    }

    // --- Board ---

    // TODO: changer ça

    public Column addColumn() {
        return new Column(board);
    }


    // ---  Column ---

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


    // ---  Card ---

    public Column getColumn(Card card) {
        return card.getColumn();
    }

    public void delete(Card card) {
        card.delete();
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