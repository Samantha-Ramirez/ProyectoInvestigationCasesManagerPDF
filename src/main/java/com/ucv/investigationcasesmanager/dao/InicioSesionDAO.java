package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Usuario;

public class InicioSesionDAO extends GenericDAO<String> {

    public Usuario validarUsuario(String cedula) {
        String sql = "SELECT * FROM usuario WHERE cedula = ?";
        Usuario usuario = new Usuario();

        ejecutarConsulta(sql, rs -> {
            if (rs.next()) {
                usuario.setCedula(rs.getString("cedula"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setRol(rs.getString("rol"));
            }
        }, cedula);

        return (usuario.getRol() != null) ? usuario : null;
    }
}
