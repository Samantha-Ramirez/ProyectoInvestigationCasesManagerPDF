package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Factory Method pattern - creators for startup view products.
 */

// Abstract creator
public abstract class StartupCreator {
    public abstract StartupProduct factoryMethod();
}


class InboxStartupCreator extends StartupCreator {
    @Override
    public StartupProduct factoryMethod() { return new InboxStartupProduct(); }
}


class BoardStartupCreator extends StartupCreator {
    @Override
    public StartupProduct factoryMethod() { return new BoardStartupProduct(); }
}
