package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.SystemEntity;

import java.util.List;

/**
 * PDyF: DAO genérico para los catálogos del sistema. Reutiliza BaseDAO con SQL dinámico basado en
 * el nombre de tabla, permitiendo gestionar todos los catálogos con una sola clase DAO.
 */
public class SystemEntityDAO extends BaseDAO<SystemEntity> {

    // Obtener todos los registros de una tabla catálogo, ordenados por nombre
    public List<SystemEntity> findAll(String tableName) {
        validateTableName(tableName);
        String sql = "SELECT id, name FROM " + tableName + " ORDER BY name";
        return queryList(sql, rs -> {
            SystemEntity entity = new SystemEntity();
            entity.setId(rs.getInt("id"));
            entity.setName(rs.getString("name"));
            return entity;
        });
    }

    // Insertar un nuevo registro en la tabla catálogo
    public boolean save(String tableName, String name) {
        validateTableName(tableName);
        String sql = "INSERT INTO " + tableName + " (name) VALUES (?)";
        return execute(sql, name) > 0;
    }

    // Actualizar el nombre de un registro existente
    public boolean update(String tableName, int id, String name) {
        validateTableName(tableName);
        String sql = "UPDATE " + tableName + " SET name = ? WHERE id = ?";
        return execute(sql, name, id) > 0;
    }

    // Eliminar un registro de la tabla catálogo
    public boolean delete(String tableName, int id) {
        validateTableName(tableName);
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        return execute(sql, id) > 0;
    }

    // Por qué: valida que el nombre de tabla provenga de la lista blanca (EntityType) para
    // prevenir inyección SQL, ya que el nombre de tabla no puede parametrizarse en SQL estándar.
    private void validateTableName(String tableName) {
        for (com.ucv.investigationcasesmanager.model.EntityType type :
                com.ucv.investigationcasesmanager.model.EntityType.values()) {
            if (tableName.equals(type.getTableName())) {
                return;
            }
        }
        throw new IllegalArgumentException("Nombre de tabla no permitido: " + tableName);
    }
}
