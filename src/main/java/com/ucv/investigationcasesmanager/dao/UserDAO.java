package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.User;
import java.util.List;

/*
 * DAO for user-related operations.
 */
public class UserDAO extends BaseDAO<User> {

    public List<User> findInvestigators() {
        String sql = "SELECT id, nombre, apellido FROM usuario WHERE rol = 'Investigador'";
        return queryList(sql, this::mapUser);
    }

    private User mapUser(java.sql.ResultSet rs) throws java.sql.SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setFirstName(rs.getString("nombre"));
        u.setLastName(rs.getString("apellido"));
        return u;
    }
}
