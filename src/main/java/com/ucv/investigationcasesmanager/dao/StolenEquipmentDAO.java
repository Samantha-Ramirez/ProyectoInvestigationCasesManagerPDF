package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.StolenEquipment;

import java.util.List;

/**
 * PDyF: DAO para el registro de seriales de equipos reportados robados.
 */
public class StolenEquipmentDAO extends BaseDAO<StolenEquipment> {

    // Obtener todos los registros ordenados por serial
    public List<StolenEquipment> findAll() {
        String sql =
                "SELECT id, serial, equipment_type, brand, model, observations FROM stolen_equipment ORDER BY serial";
        return queryList(sql, rs -> {
            StolenEquipment e = new StolenEquipment();
            e.setId(rs.getInt("id"));
            e.setSerial(rs.getString("serial"));
            e.setEquipmentType(rs.getString("equipment_type"));
            e.setBrand(rs.getString("brand"));
            e.setModel(rs.getString("model"));
            e.setObservations(rs.getString("observations"));
            return e;
        });
    }

    // Obtener un registro por su id
    public StolenEquipment findById(int id) {
        String sql =
                "SELECT id, serial, equipment_type, brand, model, observations FROM stolen_equipment WHERE id = ?";
        return queryOne(sql, rs -> {
            StolenEquipment e = new StolenEquipment();
            e.setId(rs.getInt("id"));
            e.setSerial(rs.getString("serial"));
            e.setEquipmentType(rs.getString("equipment_type"));
            e.setBrand(rs.getString("brand"));
            e.setModel(rs.getString("model"));
            e.setObservations(rs.getString("observations"));
            return e;
        }, id);
    }

    // Insertar un nuevo registro
    public boolean save(StolenEquipment e) {
        String sql =
                "INSERT INTO stolen_equipment (serial, equipment_type, brand, model, observations) VALUES (?, ?, ?, ?, ?)";
        return execute(sql, e.getSerial(), e.getEquipmentType(), e.getBrand(), e.getModel(),
                e.getObservations()) > 0;
    }

    // Actualizar un registro existente
    public boolean update(StolenEquipment e) {
        String sql =
                "UPDATE stolen_equipment SET serial = ?, equipment_type = ?, brand = ?, model = ?, observations = ? WHERE id = ?";
        return execute(sql, e.getSerial(), e.getEquipmentType(), e.getBrand(), e.getModel(),
                e.getObservations(), e.getId()) > 0;
    }

    // Eliminar un registro
    public boolean delete(int id) {
        String sql = "DELETE FROM stolen_equipment WHERE id = ?";
        return execute(sql, id) > 0;
    }
}
