package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.User;

/*
 * PDyF: DAO for login operations.
 */
public class LoginDAO extends BaseDAO<String> {

    public User findByIdNumber(String idNumber) {
        String sql = "SELECT * FROM usuario WHERE cedula = ?";
        return queryOne(sql, this::mapUser, idNumber);
    }

    private User mapUser(java.sql.ResultSet rs) throws java.sql.SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("nombre"));
        user.setLastName(rs.getString("apellido"));
        user.setIdNumber(rs.getString("cedula"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("rol"));
        return user;
    }
}
