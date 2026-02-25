package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Usuario;

/*
 * PDyF: Este DAO maneja las operaciones de acceso a datos relacionadas con el inicio de sesión,
 * específicamente la consulta de usuarios por cédula para validar credenciales.
 */
public class InicioSesionDAO extends BaseDAO<String> {
    // Consultar usuario por cédula para iniciar sesión
    public Usuario consultarUsuario(String cedula) {
        String sql = "SELECT * FROM usuario WHERE cedula = ?";
        Usuario usuario = new Usuario();

        ejecutarConsulta(sql, rs -> {
            if (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
            }
        }, cedula);

        return (usuario.getRol() != null) ? usuario : null;
    }
}
