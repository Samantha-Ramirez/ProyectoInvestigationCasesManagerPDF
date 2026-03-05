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

    // Por qué: send() permite que el colega de usuario también pueda enviar mensajes al
    // mediador cuando sea necesario, completando la interfaz del patrón del profesor.
    public boolean send(String message) {
        return mediator.send(message, this);
    }

    // Por qué: notify() recibe mensajes del mediador dirigidos a este colega, cerrando
    // el ciclo de comunicación bidireccional descrito en el patrón del profesor.
    public void notify(String message) {
        System.out.println("UserRegistrationColleague notificado: " + message);
    }
}
