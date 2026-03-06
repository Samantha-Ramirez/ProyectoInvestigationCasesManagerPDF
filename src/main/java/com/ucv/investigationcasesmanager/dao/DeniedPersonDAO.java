package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.DeniedPerson;

import java.util.List;

/**
 * PDyF: DAO para el registro de personal amonestado o desincorporado.
 */
public class DeniedPersonDAO extends BaseDAO<DeniedPerson> {

    // Obtener todos los registros de personal amonestado ordenados por apellido
    public List<DeniedPerson> findAll() {
        String sql =
                "SELECT id, ci, first_name, last_name, company FROM denied_person ORDER BY last_name, first_name";
        return queryList(sql, rs -> {
            DeniedPerson p = new DeniedPerson();
            p.setId(rs.getInt("id"));
            p.setCi(rs.getString("ci"));
            p.setFirstName(rs.getString("first_name"));
            p.setLastName(rs.getString("last_name"));
            p.setCompany(rs.getString("company"));
            return p;
        });
    }

    // Obtener un registro por su id
    public DeniedPerson findById(int id) {
        String sql = "SELECT id, ci, first_name, last_name, company FROM denied_person WHERE id = ?";
        return queryOne(sql, rs -> {
            DeniedPerson p = new DeniedPerson();
            p.setId(rs.getInt("id"));
            p.setCi(rs.getString("ci"));
            p.setFirstName(rs.getString("first_name"));
            p.setLastName(rs.getString("last_name"));
            p.setCompany(rs.getString("company"));
            return p;
        }, id);
    }

    // Insertar un nuevo registro
    public boolean save(DeniedPerson p) {
        String sql =
                "INSERT INTO denied_person (ci, first_name, last_name, company) VALUES (?, ?, ?, ?)";
        return execute(sql, p.getCi(), p.getFirstName(), p.getLastName(), p.getCompany()) > 0;
    }

    // Actualizar un registro existente
    public boolean update(DeniedPerson p) {
        String sql =
                "UPDATE denied_person SET ci = ?, first_name = ?, last_name = ?, company = ? WHERE id = ?";
        return execute(sql, p.getCi(), p.getFirstName(), p.getLastName(), p.getCompany(),
                p.getId()) > 0;
    }

    // Eliminar un registro
    public boolean delete(int id) {
        String sql = "DELETE FROM denied_person WHERE id = ?";
        return execute(sql, id) > 0;
    }
}
