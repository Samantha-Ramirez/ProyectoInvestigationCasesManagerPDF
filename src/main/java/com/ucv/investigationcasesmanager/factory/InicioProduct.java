package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.view.*;

/**
 * PDyF: Este código implementa el patrón Factory Method para decidir qué vista de inicio mostrar
 * según el rol del usuario (Investigador o Administrador)
 */

// Producto abstracto
public abstract class InicioProduct {
    public abstract BaseView getVista();
}


// Producto concreto para Investigador
class BandejaInicioProduct extends InicioProduct {
    @Override
    public BaseView getVista() {
        return new BandejaView();
    }
}


// Producto concreto para Administrador
class CarteleraInicioProduct extends InicioProduct {
    @Override
    public BaseView getVista() {
        return new CarteleraView();
    }
}
