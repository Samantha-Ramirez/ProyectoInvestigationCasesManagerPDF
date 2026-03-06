package com.ucv.investigationcasesmanager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * PDyF: Singleton - gestiona la conexión a la base de datos SQLite. Se reutiliza la misma instancia
 * mientras la conexión esté activa.
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
                System.out.println("Conexión a la base de datos establecida.");
            } catch (ClassNotFoundException e) {
                throw new SQLException(
                        "No se encontró el driver de SQLite en el classpath de Maven.", e);
            }
        }
        return instance;
    }

    // Activar claves foráneas en SQLite (desactivadas por defecto)
    private static void configurePragmas(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
    }

    public static void close() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
