package com.ucv.investigationcasesmanager.mediator;

/**
 * PDyF: Patrón Mediator - colega abstracto que conoce al mediador pero no a los demás colegas.
 */
public abstract class RegistrationColleague {
    protected final RegistrationMediator mediator;

    protected RegistrationColleague(RegistrationMediator mediator) {
        this.mediator = mediator;
    }
}
