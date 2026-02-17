package com.ucv.investigationcasesmanager.view;

/*
 * PDyF: Este código implementa el patrón Factory Method para decidir qué vista de inicio mostrar
 * según el rol del usuario (Investigador o Administrador)
 */

// Creador
abstract class InicioCreator {
    public abstract InicioView FactoryMethod();
}


// Creador concreto especializado en crear la vista de Investigador
class BandejaInicioCreator extends InicioCreator {
    @Override
    public InicioView FactoryMethod() {
        return new BandejaInicioView();
    }
}


// Creador concreto especializado en crear la vista de Administrador
class CarteleraInicioCreator extends InicioCreator {
    @Override
    public InicioView FactoryMethod() {
        return new CarteleraInicioView();
    }
}
