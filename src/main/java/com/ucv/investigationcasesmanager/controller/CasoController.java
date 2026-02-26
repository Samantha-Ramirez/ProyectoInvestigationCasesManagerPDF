package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para casos.
 */
public class CasoController {
    private final CasoDAO casoDAO;

    public CasoController() {
        this.casoDAO = ServiceLocator.obtenerServicio(CasoDAO.class);
    }

    // Obtener casos para un investigador específico
    public List<Caso> obtenerCasosInvestigador(int idInvestigador) {
        return casoDAO.obtenerCasosInvestigador(idInvestigador);
    }

    // Obtener casos para un administrador específico
    public List<Caso> obtenerCasosAdministrador(int idAdministrador) {
        return casoDAO.obtenerCasosAdministrador(idAdministrador);
    }

    // Obtener caso por número de expediente
    public Caso obtenerCasoPorNroExpediente(String nroExpediente) {
        return casoDAO.obtenerCasoPorNroExpediente(nroExpediente);
    }

    // Guardar nuevo caso
    public boolean guardarCaso(Caso caso, Usuario usuarioActual, String duracion) {
        if (!com.ucv.investigationcasesmanager.mediator.RegistroMediatorClient
                .validarYPreparar(caso, usuarioActual, duracion)) {
            return false;
        }
        return casoDAO.guardarCaso(caso);
    }
}
