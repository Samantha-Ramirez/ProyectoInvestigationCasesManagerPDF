package com.ucv.investigationcasesmanager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * PDyF: Este código implementa el patrón Singleton para asegurar una única instancia de conexión.
 */
public class ConexionBD {

    // Instancia única de la conexión
    private static Connection instancia = null;

    // Ruta a la carpeta db
    private static final String URL = "jdbc:sqlite:db/investigationcasesmanager.db";

    private ConexionBD() {}

    // Retornar la instancia de conexión actual. Si no existe o está cerrada, la crea.
    public static Connection getInstancia() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");

                instancia = DriverManager.getConnection(URL);

                configurarPragmas(instancia);

                System.out.println("Conexión exitosa a la base de datos local.");
            } catch (ClassNotFoundException e) {
                throw new SQLException(
                        "No se encontró el driver de SQLite en el Classpath de Maven.", e);
            }
        }
        return instancia;
    }

    // Configurar opciones específicas de SQLite.
    private static void configurarPragmas(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
    }

    // Cerrar la conexión de forma segura al finalizar la aplicación.
    public static void cerrarConexion() {
        if (instancia != null) {
            try {
                instancia.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
