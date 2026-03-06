package com.ucv.investigationcasesmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * PDyF: DAO - maneja cualquier tipo de entidad mediante herencia. Las clases hijas solo deben
 * proporcionar el SQL y el mapeador de filas.
 */
public abstract class BaseDAO<T> {

    // Ejecutar sentencias de modificación (INSERT, UPDATE, DELETE)
    protected int execute(String sql, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar sentencia: " + e.getMessage());
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            return 0;
        }
    }

    // Ejecutar una consulta SELECT y procesar el ResultSet con el handler proporcionado
    protected void query(String sql, ResultSetHandler<T> handler, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                handler.handle(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar consulta: " + e.getMessage());
            System.err.println("SQL: " + sql);
            e.printStackTrace();
        }
    }

    // Obtener una lista de objetos mapeados desde el ResultSet
    protected <R> List<R> queryList(String sql, RowMapper<R> mapper, Object... params) {
        List<R> results = new ArrayList<>();
        query(sql, rs -> {
            while (rs.next()) {
                results.add(mapper.mapRow(rs));
            }
        }, params);
        return results;
    }

    // Obtener un único objeto mapeado desde el ResultSet
    @SuppressWarnings("unchecked")
    protected <R> R queryOne(String sql, RowMapper<R> mapper, Object... params) {
        final Object[] result = new Object[1];
        query(sql, rs -> {
            if (rs.next()) {
                result[0] = mapper.mapRow(rs);
            }
        }, params);
        return (R) result[0];
    }

    // Asignar parámetros al PreparedStatement en orden posicional
    private void setParameters(PreparedStatement pstmt, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    // Interfaz funcional para procesar el ResultSet completo
    @FunctionalInterface
    public interface ResultSetHandler<T> {
        void handle(ResultSet rs) throws SQLException;
    }

    // Interfaz funcional para mapear una fila del ResultSet a un objeto
    @FunctionalInterface
    protected interface RowMapper<R> {
        R mapRow(ResultSet rs) throws SQLException;
    }
}
