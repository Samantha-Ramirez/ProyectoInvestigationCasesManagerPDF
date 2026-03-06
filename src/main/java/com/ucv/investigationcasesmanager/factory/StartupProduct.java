package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.view.BaseView;

/**
 * PDyF: Factory Method - decide qué vista de inicio mostrar según el rol del usuario.
 */

// Producto abstracto
public abstract class StartupProduct {
    public abstract BaseView getView();
}


// Producto concreto para el rol Investigador
class InboxStartupProduct extends StartupProduct {
    @Override
    public BaseView getView() {
        return new com.ucv.investigationcasesmanager.view.InboxView();
    }
}


// Producto concreto para el rol Administrador
class BoardStartupProduct extends StartupProduct {
    @Override
    public BaseView getView() {
        return new com.ucv.investigationcasesmanager.view.BoardView();
    }
}
