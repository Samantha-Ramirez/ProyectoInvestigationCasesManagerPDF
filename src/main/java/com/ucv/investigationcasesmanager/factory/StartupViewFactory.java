package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.view.BaseView;

/**
 * PDyF: Cliente que utiliza el Factory Method para obtener la vista de inicio según el rol del
 * usuario autenticado.
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
