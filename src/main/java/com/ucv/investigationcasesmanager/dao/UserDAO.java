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

    // Registrar un nuevo investigador en la tabla user
    public boolean saveInvestigator(String firstName, String lastName, String idNumber,
            String email) {
        String sql =
                "INSERT INTO user (first_name, last_name, id_number, email, role) VALUES (?, ?, ?, ?, 'Investigador')";
        return execute(sql, firstName, lastName, idNumber, email) > 0;
    }

    // Actualizar nombre y apellido de un investigador existente
    public boolean updateName(int id, String firstName, String lastName) {
        String sql = "UPDATE user SET first_name = ?, last_name = ? WHERE id = ?";
        return execute(sql, firstName, lastName, id) > 0;
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
