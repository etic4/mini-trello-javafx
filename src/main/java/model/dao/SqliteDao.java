package model.dao;

import java.sql.*;

public abstract class SqliteDao {
    private static String url = "jdbc:sqlite:dbsample.db";

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

        // SQL statement for client table
        sql = "CREATE TABLE IF NOT EXISTS client ("
                + "	idClient integer PRIMARY KEY,"
                + "	clientName text NOT NULL);";
        stmt.execute(sql);

        // SQL statement for invoice table
        sql = "CREATE TABLE IF NOT EXISTS invoice ("
                + "	idInvoice integer PRIMARY KEY,"
                + "	idClient integer NOT NULL,"
                + "	amount NUMERIC NOT NULL,"
                + " CONSTRAINT fk_client FOREIGN KEY (idClient) "
                + " REFERENCES client(idClient));";
        stmt.execute(sql);

    }
}
