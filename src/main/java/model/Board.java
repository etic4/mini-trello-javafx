package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Board extends Entitled implements Container<Column> {

    private final ObservableList<Column> columns = FXCollections.observableArrayList();

    public Board(String title) {
        super(title);
    }

    @Override
    public ObservableList<Column> getMovables() {
        return columns;
    }

}