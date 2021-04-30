package model;

import main.Config;

public class DaoFactory {

    public Dao<Board> getBoardDao() {
        if (Config.BACKEND == DaoBackendType.SQLITE) {
            return new SqliteDao().getBoardDao();
        }
        return new JsonDao().getBoardDao();
    }

    public Dao<Column> getColumnDao() {
        if (Config.BACKEND == DaoBackendType.SQLITE) {
            return new SqliteDao().getColumnDao();
        }
        return new JsonDao().getColumnDao();
    }

    public Dao<Card> getCardDao() {
        if (Config.BACKEND == DaoBackendType.SQLITE) {
            return new SqliteDao().getCardDao();
        }
        return new JsonDao().getCardDao();
    }

    // TODO: est-ce sa place ?
    public void seedData(){
        if (Config.BACKEND == DaoBackendType.SQLITE) {
            new SqliteDao().seedData();
        } else {
            new JsonDao().seedData();
        }
    }


}
