package com.ucv.investigationcasesmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * PDyF: Generic DAO that handles any entity type.
 */
public abstract class BaseDAO<T> {

    protected int execute(String sql, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in execute: " + e.getMessage());
            System.err.println("SQL: " + sql);
            e.printStackTrace();
            return 0;
        }
    }

    protected void query(String sql, ResultSetHandler<T> handler, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                handler.handle(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error in query: " + e.getMessage());
            System.err.println("SQL: " + sql);
            e.printStackTrace();
        }
    }

    protected <R> List<R> queryList(String sql, RowMapper<R> mapper, Object... params) {
        List<R> results = new ArrayList<>();
        query(sql, rs -> {
            while (rs.next()) {
                results.add(mapper.mapRow(rs));
            }
        }, params);
        return results;
    }

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

    private void setParameters(PreparedStatement pstmt, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    @FunctionalInterface
    public interface ResultSetHandler<T> {
        void handle(ResultSet rs) throws SQLException;
    }

    @FunctionalInterface
    protected interface RowMapper<R> {
        R mapRow(ResultSet rs) throws SQLException;
    }
}
