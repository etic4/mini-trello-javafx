package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Column extends Entitled implements Container<Card>, Movable<Column, Board> {

    private final Board board;
    private final ObservableList<Card> cards = FXCollections.observableArrayList();

    public Column(Board board, String title) {
        super(title);

        this.board = board;
        board.add(this);
    }

    public Column(Board board) {
        this(board, "");
    }

    @Override
    public Board getContainer() {
        return board;
    }

    @Override
    public void setContainer(Board board) {
        throw new UnsupportedOperationException("Une colonne n'a pas -pour l'instant- la capacité de changer de board");
    }

    @Override
    public Column getMovable() {
        return this;
    }


    @Override
    public ObservableList<Card> getMovables() {
        return cards;
    }

}