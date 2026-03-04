package com.ucv.investigationcasesmanager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * PDyF: Singleton that manages the database connection.
 */
public class DatabaseConnection {
    private static Connection instance = null;
    private static final String URL = "jdbc:sqlite:db/InvestigationCasesManager.db";

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                instance = DriverManager.getConnection(URL);
                configurePragmas(instance);
                System.out.println("Database connection established.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite driver not found in Maven classpath.", e);
            }
        }
        return instance;
    }

    private static void configurePragmas(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
    }

    public static void close() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
