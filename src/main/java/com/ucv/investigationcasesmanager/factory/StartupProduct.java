package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.view.BaseView;

/**
 * PDyF: Factory Method pattern - decides which startup view to show based on user role.
 */

// Abstract product
public abstract class StartupProduct {
    public abstract BaseView getView();
}


// Concrete product for Investigator role
class InboxStartupProduct extends StartupProduct {
    @Override
    public BaseView getView() {
        return new com.ucv.investigationcasesmanager.view.InboxView();
    }
}


// Concrete product for Administrator role
class BoardStartupProduct extends StartupProduct {
    @Override
    public BaseView getView() {
        return new com.ucv.investigationcasesmanager.view.BoardView();
    }
}
