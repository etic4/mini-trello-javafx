package model;


class JsonDao implements Backend {

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
