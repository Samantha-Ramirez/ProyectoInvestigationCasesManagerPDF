package com.ucv.investigationcasesmanager.dao;

import java.sql.*;

/*
 * PDyF: Este código implementa un DAO genérico que maneja cualquier tipo de entidad.
 */
public abstract class BaseDAO<T> {
    // Ejecutar actualizaciones (INSERT, UPDATE, DELETE)
    protected int ejecutarActualizacion(String sql, Object... parametros) {
        try (Connection conn = ConexionBD.getInstancia();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            configurarParametros(pstmt, parametros);

            int resultado = pstmt.executeUpdate();
            return resultado;

        } catch (SQLException e) {
            System.err.println("Error en Update Genérico: " + e.getMessage());
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            return 0;
        }
    }

    // Ejecutar consultas (SELECT)
    protected void ejecutarConsulta(String sql, ResultSetHandler<T> handler, Object... parametros) {
        try (Connection conn = ConexionBD.getInstancia();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            configurarParametros(pstmt, parametros);
            try (ResultSet rs = pstmt.executeQuery()) {
                handler.map(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error en Consulta Genérica: " + e.getMessage());
            System.err.println("SQL: " + sql);
            e.printStackTrace();
        }
    }

    // Configurar parámetros de cualquier tipo
    private void configurarParametros(PreparedStatement pstmt, Object[] parametros)
            throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            pstmt.setObject(i + 1, parametros[i]);
        }
    }

    // Procesar el ResultSet
    @FunctionalInterface
    public interface ResultSetHandler<T> {
        void map(ResultSet rs) throws SQLException;
    }
}
