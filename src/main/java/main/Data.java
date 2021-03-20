package main;

import model.Board;
import model.Card;
import model.Column;

//TODO: bien revoir editable label
public class Data {

    public static Board init() {

        Board board = new Board("Board 1");

        Column column1 = new Column(board, "Column 1");
        Column column2 = new Column(board, "Column 2");
        Column column3 = new Column(board, "Column 3");

        new Card(column1, "Card 1 col1");
        new Card(column1, "Card 2 col1");

        new Card(column2, "Card 1 col2");

        new Card(column3, "Card 1 col3");
        new Card(column3, "Card 2 col3");
        new Card(column3, "Card 3 col3");

        return board;

    }

}