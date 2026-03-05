package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Patrón Factory Method - creadores concretos para cada vista de inicio según el rol.
 */

// Creador abstracto
public abstract class StartupCreator {
    public abstract StartupProduct factoryMethod();
}


class InboxStartupCreator extends StartupCreator {
    @Override
    public StartupProduct factoryMethod() {
        return new InboxStartupProduct();
    }
}


class BoardStartupCreator extends StartupCreator {
    @Override
    public StartupProduct factoryMethod() {
        return new BoardStartupProduct();
    }
}
