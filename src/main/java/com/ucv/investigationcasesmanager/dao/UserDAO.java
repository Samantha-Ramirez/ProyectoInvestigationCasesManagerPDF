package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.User;
import java.util.List;

/*
 * PDyF: DAO para operaciones de acceso a datos de usuarios del sistema.
 */
public class UserDAO extends BaseDAO<User> {

    // Obtener todos los investigadores registrados en el sistema
    public List<User> findInvestigators() {
        String sql = "SELECT id, first_name, last_name FROM user WHERE role = 'Investigador'";
        return queryList(sql, this::mapUser);
    }

    // Mapear una fila del ResultSet a un objeto User
    private User mapUser(java.sql.ResultSet rs) throws java.sql.SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setFirstName(rs.getString("first_name"));
        u.setLastName(rs.getString("last_name"));
        return u;
    }
}
