package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Usuario;
import java.util.List;

/*
 * DAO para operaciones de acceso a datos de usuarios.
 */
public class UsuarioDAO extends BaseDAO<Usuario> {
    // Obtener todos los investigadores registrados en el sistema
    public List<Usuario> obtenerInvestigadores() {
        String sql = "SELECT id, nombre, apellido FROM usuario WHERE rol = 'Investigador'";
        return obtenerLista(sql, this::mapearUsuario);
    }

    // Mapear resultado de consulta a un objeto Usuario
    private Usuario mapearUsuario(java.sql.ResultSet rs) throws java.sql.SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        return u;
    }
}
