package com.ucv.investigationcasesmanager.mediator;

/**
 * PDyF: Mediator pattern - abstract mediator.
 */
public abstract class RegistrationMediator {
    public abstract boolean send(String event, RegistrationColleague colleague);
}
