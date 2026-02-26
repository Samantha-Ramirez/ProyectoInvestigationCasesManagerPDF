package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Usuario;

/**
 * PDyF: Este código implementa el patrón Mediator para gestionar la comunicación entre los colegas
 * que participan en el proceso de registro de casos, centralizando la lógica de validación y
 * preparación del caso.
 */

// Cliente
public final class RegistroMediatorClient {
    private RegistroMediatorClient() {}

    public static boolean validarYPreparar(Caso caso, Usuario usuario, String duracionTexto) {
        RegistroConcreteMediator mediator = new RegistroConcreteMediator();
        CasoRegistroColleague actorCaso = new CasoRegistroColleague(mediator, caso, duracionTexto);
        UsuarioRegistroColleague actorUsuario = new UsuarioRegistroColleague(mediator, usuario);

        mediator.setCasoColleague(actorCaso);
        mediator.setUsuarioColleague(actorUsuario);

        return actorCaso.solicitarValidacionYPreparacion();
    }
}
