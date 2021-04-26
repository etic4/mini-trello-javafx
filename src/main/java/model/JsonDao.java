package model;


class JsonDao {

    public Dao<Board> getBoardDao() {
        return new JsonBoardDao();
    }

    public Dao<Column> getColumnDao() {
        return new JsonColumnDao();
    }

    public Dao<Card> getCardDao() {
        return new JsonCardDao();
    }

    public void seedData() {

    }
}
