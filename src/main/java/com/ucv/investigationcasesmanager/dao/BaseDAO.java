package com.ucv.investigationcasesmanager.dao;

import java.sql.*;

/*
 * DAO genérico.
 */
public abstract class BaseDAO<T> {

protected int ejecutarActualizacion(String sql, Object... parametros) {
    try (Connection conn = ConexionBD.getInstancia();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        System.out.println("Ejecutando update en BD...");
        configurarParametros(pstmt, parametros);
        
        int resultado = pstmt.executeUpdate();
        System.out.println("Update ejecutado, filas afectadas: " + resultado);
        return resultado;

    } catch (SQLException e) {
        System.err.println("Error en Update Genérico: " + e.getMessage());
        System.err.println("SQL: " + sql);
        e.printStackTrace(); // ← IMPORTANTE: Ver la traza completa
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
