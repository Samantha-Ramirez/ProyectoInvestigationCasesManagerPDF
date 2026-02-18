package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.model.Usuario;

/*
 * Vista de inicio. PDyF: Este código implementa el patrón Factory Method para decidir qué vista de
 * inicio mostrar según el rol del usuario (Investigador o Administrador)
 */

// Producto abstracto
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
        new CarteleraView(1).setVisible(true);
    }
}


// Creador abstracto
abstract class InicioCreator {
    public abstract InicioView FactoryMethod(Usuario user);

    public static void inicioSegunRol(Usuario user) {
        InicioCreator creador;
        if (user.getRol().equalsIgnoreCase("Administrador")) {
            creador = new CarteleraInicioCreator();
        } else {
            creador = new BandejaInicioCreator();
        }
        creador.FactoryMethod(user).mostrar();
    }
}


// Creador concreto especializado en crear la vista de Investigador
class BandejaInicioCreator extends InicioCreator {
    @Override
    public InicioView FactoryMethod(Usuario user) {
        return new BandejaInicioView();
    }
}


// Creador concreto especializado en crear la vista de Administrador
class CarteleraInicioCreator extends InicioCreator {
    @Override
    public InicioView FactoryMethod(Usuario user) {
        return new CarteleraInicioView();
    }
}
