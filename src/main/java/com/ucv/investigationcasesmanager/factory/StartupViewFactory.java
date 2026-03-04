package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.view.BaseView;

/**
 * PDyF: Client that uses the Factory Method to obtain the startup view based on user role.
 */
public class StartupViewFactory {
    private StartupViewFactory() {}

    public static BaseView getStartView(String role) {
        StartupCreator creator;
        if (role.equalsIgnoreCase("Administrador")) {
            creator = new BoardStartupCreator();
        } else {
            creator = new InboxStartupCreator();
        }
        return creator.factoryMethod().getView();
    }
}
