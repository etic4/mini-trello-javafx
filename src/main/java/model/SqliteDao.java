package model;

import java.sql.*;

class SqliteDao extends SqliteConnection implements Backend {

    public Dao<Board> getBoardDao() {
        return new SqLiteBoardDao();
    }

    public Dao<Column> getColumnDao() {
        return new SqLiteColumnDao();
    }

    public Dao<Card> getCardDao() {
        return new SqLiteCardDao();
    }

    public void seedData() {
        try {
            Connection conn = getConnection();
            dropTables(conn);
            createTables(conn);

            // --- Board ---

            String sql = "INSERT INTO Board(title) VALUES(?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "Seul et unique board");
            preparedStatement.execute();


            // --- Columns ---

            String[][] columns = {
                    {"Première col", "0", "1"},
                    {"Deuxième col", "1", "1"},
                    {"Autre col", "2", "1"}
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
                    {"Une carte", "0", "1"},
                    {"À faire", "1", "1"},
                    {"Une seule carte", "0", "2"},
                    {"Première carte", "0", "3"},
                    {"Quelque chose", "1", "3"},
                    {"Fini", "2", "3"}
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

    private void createTables(Connection conn) throws SQLException {
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
                + " FOREIGN  KEY(`column`) REFERENCES `Column`(column_id)"
                + " ON DELETE CASCADE" +
                ")";
        stmt.execute(sql);

    }

    private void dropTables(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql;

        sql = "DROP TABLE IF EXISTS Card;";
        statement.executeUpdate(sql);

        sql = "DROP TABLE IF EXISTS `Column`;";
        statement.executeUpdate(sql);

        sql = "DROP TABLE IF EXISTS Board;";
        statement.executeUpdate(sql);
    }


    public static void main(String[] args) {
        new SqliteDao().seedData();
    }

}
