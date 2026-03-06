package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.User;

/*
 * PDyF: DAO - maneja las operaciones de acceso a datos para el inicio de sesión, específicamente la
 * búsqueda de usuarios por cédula para validar credenciales.
 */
public class LoginDAO extends BaseDAO<String> {

    // Buscar un usuario por su número de cédula
    public User findByIdNumber(String idNumber) {
        String sql = "SELECT * FROM user WHERE id_number = ?";
        return queryOne(sql, this::mapUser, idNumber);
    }

    // Mapear una fila del ResultSet a un objeto User
    private User mapUser(java.sql.ResultSet rs) throws java.sql.SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setIdNumber(rs.getString("id_number"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        return user;
    }
}
