package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.User;

/**
 * PDyF: Mediator pattern - concrete colleague for user data.
 */
public class UserRegistrationColleague extends RegistrationColleague {
    private final User user;

    public UserRegistrationColleague(RegistrationMediator mediator, User user) {
        super(mediator);
        this.user = user;
    }

    public User getUser() { return user; }
}
