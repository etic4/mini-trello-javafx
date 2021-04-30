package model;

import direction.Direction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardFacade {
    private final DaoFactory dao;
    private final Board board;


    public BoardFacade(Board board) {
        this.board = board;
        this.dao = new DaoFactory();
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
            dao.getColumnDao().update((Column) entitled);
        } else {
            dao.getCardDao().update((Card) entitled);
        }
    }

    // ---  Column ---

    // ajout d'une nouvelle colonne
    public Column newColumn() {
        var column = new Column(board, "");
        return dao.getColumnDao().save(column);
    }

    // undo delete column
    public void restoreColumn(Column column, int position) {
        board.addColumn(position, column);

        var colId = dao.getColumnDao().save(column).getId();
        column.setId(colId);

        dao.getColumnDao().updatePositions(board.getColumns());

        for (var card : column.getCards()) {
            var cardId = dao.getCardDao().save(card).getId();
            card.setId(cardId);
        }
    }

    public void delete(Column column) {
        column.delete();
        dao.getColumnDao().delete(column);
        dao.getColumnDao().updatePositions(board.getColumns());
    }

    public void move(Column column, Direction direction) {
        Column otherColumn = null;

        switch (direction) {
            case LEFT:
                otherColumn = column.moveLeft();
                break;
            case RIGHT:
                otherColumn = column.moveRight();
        }

        dao.getColumnDao().update(column);
        dao.getColumnDao().update(otherColumn);
    }

    // ---  Card ---

    // ajout d'une nouvelle carte
    public Card newCard(Column column) {
        var card = new Card(column, "");
        return dao.getCardDao().save(card);
    }

    // undo delete et changement de colonne
    public void restoreCard(Card card, Column column, int position) {
        column.addCard(position, card);

        var id = dao.getCardDao().save(card).getId();
        card.setId(id);

        dao.getCardDao().updatePositions(column.getCards());
    }

    public void delete(Card card) {
        card.delete();
        dao.getCardDao().delete(card);

        var column = card.getColumn();
        dao.getCardDao().updatePositions(column.getCards());
    }

    public Column move(Card card, Direction direction) {
        var column = card.getColumn();

        Column destColumn = null;
        Card otherCard = null;

        switch (direction) {
            case UP:
                otherCard = card.moveUp();
                break;
            case DOWN:
                otherCard = card.moveDown();
                break;
            case LEFT:
                destColumn = card.moveLeft();
                break;
            case RIGHT:
                destColumn = card.moveRight();
        }

        dao.getCardDao().update(card);

        if (otherCard != null) {
            dao.getCardDao().update(otherCard);
        }

        if (destColumn != null) {
            dao.getCardDao().updatePositions(column.getCards());
            dao.getCardDao().updatePositions(destColumn.getCards());
            column = destColumn;
        }

        return column;
    }

    public Column getColumn(Card card) {
        return card.getColumn();
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