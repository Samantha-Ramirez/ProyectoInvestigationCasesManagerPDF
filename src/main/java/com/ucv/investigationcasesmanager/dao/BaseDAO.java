package com.ucv.investigationcasesmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * PDyF: Este DAO genérico maneja cualquier tipo de entidad.
 */
public abstract class BaseDAO<T> {
    // Ejecutar actualizaciones (INSERT, UPDATE, DELETE)
    protected int ejecutarActualizacion(String sql, Object... parametros) {
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

    // Ejecutar consultas (SELECT)
    protected void ejecutarConsulta(String sql, ResultSetHandler<T> handler, Object... parametros) {
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

    // Ejecutar consulta y retornar una lista mapeada
    protected <R> List<R> consultarLista(String sql, RowMapper<R> mapper, Object... parametros) {
        List<R> resultados = new ArrayList<>();
        ejecutarConsulta(sql, rs -> {
            while (rs.next()) {
                resultados.add(mapper.mapRow(rs));
            }
        }, parametros);
        return resultados;
    }

    // Ejecutar consulta y retornar un solo resultado
    @SuppressWarnings("unchecked")
    protected <R> R consultarUno(String sql, RowMapper<R> mapper, Object... parametros) {
        final Object[] resultado = new Object[1];
        ejecutarConsulta(sql, rs -> {
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
