package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.AuditLog;

import java.util.List;

/**
 * PDyF: DAO - registra quién, cuándo y qué acción fue realizada.
 */
public class AuditLogDAO extends BaseDAO<AuditLog> {

    // Obtener todos los registros de auditoría en orden descendente (más reciente primero)
    public List<AuditLog> findAll() {
        String sql =
                "SELECT id, username, action, action_date FROM audit_log ORDER BY action_date DESC";
        return queryList(sql, rs -> {
            AuditLog log = new AuditLog();
            log.setId(rs.getInt("id"));
            log.setUsername(rs.getString("username"));
            log.setAction(rs.getString("action"));
            log.setActionDate(rs.getString("action_date"));
            return log;
        });
    }

    // Insertar un nuevo registro de auditoría
    public boolean save(String username, String action) {
        String sql =
                "INSERT INTO audit_log (username, action, action_date) VALUES (?, ?, datetime('now','localtime'))";
        return execute(sql, username, action) > 0;
    }
}
