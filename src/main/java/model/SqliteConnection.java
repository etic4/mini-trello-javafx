package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    private final static String url = "jdbc:sqlite:trello.sqlite";

    public static Connection getConnection() throws SQLException {
        var conn = DriverManager.getConnection(url);
        configDB(conn);
        return conn;
    }

    private static void configDB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        // Activation of checks FK
        String sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }
}
