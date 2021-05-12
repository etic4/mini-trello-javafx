package model;

public interface Backend {
    Dao<Board> getBoardDao();
    Dao<Column> getColumnDao();
    Dao<Card> getCardDao();
    void seedData();
}
