package com.ucv.investigationcasesmanager.mediator;

/**
 * PDyF: Mediator pattern - abstract colleague.
 */
public abstract class RegistrationColleague {
    protected final RegistrationMediator mediator;

    protected RegistrationColleague(RegistrationMediator mediator) {
        this.mediator = mediator;
    }
}
