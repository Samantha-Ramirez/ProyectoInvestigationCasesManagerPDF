package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.LoginDAO;
import com.ucv.investigationcasesmanager.model.User;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

/*
 * Controller for login operations.
 */
public class LoginController {
    private final LoginDAO loginDAO;

    public LoginController() {
        this.loginDAO = ServiceLocator.get(LoginDAO.class);
    }

    public User authenticate(String idNumber) {
        return loginDAO.findByIdNumber(idNumber);
    }
}
