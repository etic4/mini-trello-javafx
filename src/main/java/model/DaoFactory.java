package model;


public class DaoFactory {
    private final DaoBackendType daoBackendType;

    public DaoFactory(DaoBackendType daoBackendType) {
        this.daoBackendType = daoBackendType;
    }

    // TODO: est-ce sa place ?
    public void seedData(){
        if (daoBackendType == DaoBackendType.SQLITE) {
            new SqliteDao().seedData();
        }
    }

    public Dao<Board> getBoardDao() {
        if (daoBackendType == DaoBackendType.SQLITE) {
            return new SqliteDao().getBoardDao();
        }
        return new JsonDao().getBoardDao();
    }

    public Dao<Column> getColumnDao() {
        if (daoBackendType == DaoBackendType.SQLITE) {
            return new SqliteDao().getColumnDao();
        }
        return new JsonDao().getColumnDao();
    }

    public Dao<Card> getCardDao() {
        if (daoBackendType == DaoBackendType.SQLITE) {
            return new SqliteDao().getCardDao();
        }
        return new JsonDao().getCardDao();
    }
}
