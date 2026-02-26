package com.ucv.investigationcasesmanager.mediator;

/**
 * PDyF: Este código implementa el patrón Mediator para gestionar la comunicación entre los colegas
 * que participan en el proceso de registro de casos, centralizando la lógica de validación y
 * preparación del caso.
 */

// Colega abstracto
public abstract class RegistroColleague {
    protected final RegistroMediator mediator;

    protected RegistroColleague(RegistroMediator mediator) {
        this.mediator = mediator;
    }
}
