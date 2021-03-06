package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    private final String url = "jdbc:sqlite:trello.sqlite";

    public Connection getConnection() throws SQLException {
        var conn = DriverManager.getConnection(url);
        configDB(conn);
        return conn;
    }

    private void configDB(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        // Activation of checks FK
        String sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }
}
