package com.ucv.investigationcasesmanager.view;

/*
 * PDyF: Este código implementa el patrón Factory Method para decidir qué vista de inicio mostrar
 * según el rol del usuario (Investigador o Administrador)
 */

// Producto
abstract class InicioView {
    public abstract void mostrar();
}


// Producto concreto para Investigador
class BandejaInicioView extends InicioView {
    @Override
    public void mostrar() {
        new BandejaView(1).setVisible(true);
    }
}


// Producto concreto para Administrador
class CarteleraInicioView extends InicioView {
    @Override
    public void mostrar() {

    }
}

