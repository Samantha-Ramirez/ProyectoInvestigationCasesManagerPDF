package com.ucv.investigationcasesmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * PDyF: Este DAO genérico maneja cualquier tipo de entidad.
 */
public abstract class BaseDAO<T> {
    // Actualizar (INSERT, UPDATE, DELETE)
    protected int actualizar(String sql, Object... parametros) {
        try (Connection conn = ConexionBD.obtenerInstancia();
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

    // Obtener (SELECT)
    protected void obtener(String sql, ResultSetHandler<T> handler, Object... parametros) {
        try (Connection conn = ConexionBD.obtenerInstancia();
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

    // Obtener una lista mapeada
    protected <R> List<R> obtenerLista(String sql, RowMapper<R> mapper, Object... parametros) {
        List<R> resultados = new ArrayList<>();
        obtener(sql, rs -> {
            while (rs.next()) {
                resultados.add(mapper.mapRow(rs));
            }
        }, parametros);
        return resultados;
    }

    // Obtener un solo resultado mapeado
    @SuppressWarnings("unchecked")
    protected <R> R obtenerUno(String sql, RowMapper<R> mapper, Object... parametros) {
        final Object[] resultado = new Object[1];
        obtener(sql, rs -> {
            if (rs.next()) {
                resultado[0] = mapper.mapRow(rs);
            }
        }, parametros);
        return (R) resultado[0];
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

    // Mapear una fila del ResultSet a un objeto
    @FunctionalInterface
    protected interface RowMapper<R> {
        R mapRow(ResultSet rs) throws SQLException;
    }
}
