package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.dao.SeguimientoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Seguimiento;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para registro de seguimientos.
 */
public class SeguimientoController {
    private final SeguimientoDAO seguimientoDAO;
    private final CasoDAO casoDAO;

    public SeguimientoController() {
        this.seguimientoDAO = ServiceLocator.obtenerServicio(SeguimientoDAO.class);
        this.casoDAO = ServiceLocator.obtenerServicio(CasoDAO.class);
    }

    // Obtener seguimientos para un caso específico
    public List<Seguimiento> obtenerSeguimientosPorCaso(int idCaso) {
        return seguimientoDAO.obtenerSeguimientosPorCaso(idCaso);
    }

    // Obtener ID de caso por número de expediente
    public int obtenerIdCaso(String nroExpediente) {
        Caso caso = casoDAO.obtenerCasoPorNroExpediente(nroExpediente);
        return caso != null ? caso.getId() : -1;
    }

    // Registrar nuevo seguimiento y actualizar estatus del caso
    public boolean registrarSeguimiento(Seguimiento seguimiento, int idCaso, String nuevoEstatus) {
        boolean guardado = seguimientoDAO.guardarSeguimiento(seguimiento);
        if (!guardado) {
            return false;
        }
        seguimientoDAO.actualizarEstatusCaso(idCaso, nuevoEstatus);
        return true;
    }
}
