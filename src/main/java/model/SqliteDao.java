package model;

import java.sql.*;

class SqliteDao {
    private final static String url = "jdbc:sqlite:trello.sqlite";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        // TODO: mauvaise idée, mais demande trop de réflexion maintenant pour imaginer comment gérer ça.
        return null;
    }

    public Dao<Board> getBoardDao() {
        return new SqLiteBoardDao(getConnection());
    }

    public Dao<Column> getColumnDao() {
        return new SqLiteColumnDao(getConnection());
    }

    public Dao<Card> getCardDao() {
        return new SqLiteCardDao(getConnection());
    }

    private static void configDB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql;

        // Activation of checks FK
        sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }

    private static void createTables(Connection conn) throws SQLException {
        String sql;
        Statement stmt = conn.createStatement();

        // création table board
        sql = "CREATE TABLE IF NOT EXISTS `Board` " +
                "("
                + "	board_id integer PRIMARY KEY,"
                + "	title TEXT NOT NULL" +
                ");";
        stmt.execute(sql);

        // création table colonnes
        sql = "CREATE TABLE IF NOT EXISTS `Column` " +
                "("
                + "	column_id integer PRIMARY KEY,"
                + "	title TEXT NOT NULL,"
                + " `position` integer NOT NULL,"
                + " board integer NOT NULL,"
                + " FOREIGN  KEY('board') REFERENCES `Board`(board_id)" +
                ")";
        stmt.execute(sql);

        // création table cartes
        sql = "CREATE TABLE IF NOT EXISTS `Card` " +
                "("
                + "	card_id integer PRIMARY KEY,"
                + "	title TEXT NOT NULL,"
                + " `position` integer NOT NULL,"
                + " `column` integer NOT NULL,"
                + " FOREIGN  KEY(`column`) REFERENCES `Column`(column_id)" +
                ")";
        stmt.execute(sql);

    }

    private static void dropTables(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql;

        sql = "DROP TABLE IF EXISTS Card;";
        statement.executeUpdate(sql);

        sql = "DROP TABLE IF EXISTS `Column`;";
        statement.executeUpdate(sql);

        sql = "DROP TABLE IF EXISTS Board;";
        statement.executeUpdate(sql);

    }

    public void seedData() {
        try {
            Connection conn = DriverManager.getConnection(url);
            configDB(conn);
            dropTables(conn);
            createTables(conn);

            // --- Board ---

            String sql = "INSERT INTO Board(title) VALUES(?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "Board 1");
            preparedStatement.execute();


            // --- Columns ---

            String[][] columns = {
                    {"Column1", "0", "1"},
                    {"Column2", "1", "1"},
                    {"Column3", "2", "1"}
            };

            sql = "INSERT INTO `Column`(title, `position`, `board`) VALUES(?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            for (var col : columns) {
                preparedStatement.setString(1, col[0]);
                preparedStatement.setInt(2, Integer.parseInt(col[1]));
                preparedStatement.setInt(3, Integer.parseInt(col[2]));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            // --- Columns ---

            String[][] cards = {
                    {"Card1 Col1", "0", "1"},
                    {"Card2 Col1", "1", "1"},
                    {"Card1 Col2", "0", "2"},
                    {"Card1 Col3", "0", "3"},
                    {"Card2 Col3", "1", "3"},
                    {"Card3 Col3", "2", "3"}
            };

            sql = "INSERT INTO `Card`(title, `position`, `column`) VALUES(?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            for (var col : cards) {
                preparedStatement.setString(1, col[0]);
                preparedStatement.setInt(2, Integer.parseInt(col[1]));
                preparedStatement.setInt(3, Integer.parseInt(col[2]));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SqliteDao().seedData();
    }

}
