package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Usuario;

/*
 * DAO específico para inicio de sesión.
 */
public class InicioSesionDAO extends GenericDAO<String> {
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
