package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Caso;

/**
 * PDyF: Este código implementa el patrón Mediator para gestionar la comunicación entre los colegas
 * que participan en el proceso de registro de casos, centralizando la lógica de validación y
 * preparación del caso.
 */

// Colega concreto
public class CasoRegistroColleague extends RegistroColleague {
    private final Caso caso;
    private final String duracionTexto;

    public CasoRegistroColleague(RegistroMediator mediator, Caso caso, String duracionTexto) {
        super(mediator);
        this.caso = caso;
        this.duracionTexto = duracionTexto;
    }

    public boolean solicitarValidacionYPreparacion() {
        return mediator.enviar("VALIDAR_Y_PREPARAR", this);
    }

    public Caso getCaso() {
        return caso;
    }

    public String getDuracionTexto() {
        return duracionTexto;
    }
}
