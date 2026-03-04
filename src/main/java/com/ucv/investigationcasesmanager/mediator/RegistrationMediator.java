package com.ucv.investigationcasesmanager.mediator;

/**
 * PDyF: Patrón Mediator - mediador abstracto que define el contrato de comunicación entre colegas.
 */
public abstract class RegistrationMediator {
    public abstract boolean send(String event, RegistrationColleague colleague);
}
