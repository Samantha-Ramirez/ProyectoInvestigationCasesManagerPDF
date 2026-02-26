package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.InicioSesionDAO;
import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

/*
 * Controlador para inicio de sesión.
 */
public class InicioSesionController {
    private final InicioSesionDAO inicioSesionDAO;

    public InicioSesionController() {
        this.inicioSesionDAO = ServiceLocator.obtenerServicio(InicioSesionDAO.class);
    }

    // Autenticar usuario por cédula
    public Usuario autenticar(String cedula) {
        return inicioSesionDAO.obtenerUsuario(cedula);
    }
}
