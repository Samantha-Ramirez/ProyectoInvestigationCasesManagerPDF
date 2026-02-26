package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Este código implementa el patrón Factory Method para decidir qué vista de inicio mostrar
 * según el rol del usuario (Investigador o Administrador)
 */

// Creador abstracto
public abstract class InicioCreator {
    public abstract InicioProduct factoryMethod();
}


// Creador concreto especializado en crear la vista de Investigador
class BandejaInicioCreator extends InicioCreator {
    @Override
    public InicioProduct factoryMethod() {
        return new BandejaInicioProduct();
    }
}


// Creador concreto especializado en crear la vista de Administrador
class CarteleraInicioCreator extends InicioCreator {
    @Override
    public InicioProduct factoryMethod() {
        return new CarteleraInicioProduct();
    }
}
