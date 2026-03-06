package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.User;

/**
 * PDyF: Patrón Mediator - colega concreto que porta los datos del usuario que realiza el registro.
 */
public class UserRegistrationColleague extends RegistrationColleague {
    private final User user;

    public UserRegistrationColleague(RegistrationMediator mediator, User user) {
        super(mediator);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean send(String message) {
        return mediator.send(message, this);
    }

    public void notify(String message) {
        System.out.println("UserRegistrationColleague notificado: " + message);
    }
}
