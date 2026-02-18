package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.model.Usuario;

/*
 * Vista de inicio. PDyF: Este código implementa el patrón Factory Method para decidir qué vista de
 * inicio mostrar según el rol del usuario (Investigador o Administrador)
 */

// Producto abstracto
abstract class InicioView {
    public abstract BaseView getInicio();
}


// Producto concreto para Investigador
class BandejaInicioView extends InicioView {
    @Override
    public BaseView getInicio() {
        return new BandejaView(1);
    }
}


// Producto concreto para Administrador
class CarteleraInicioView extends InicioView {
    @Override
    public BaseView getInicio() {
        return new CarteleraView(1);
    }
}


// Creador abstracto
abstract class InicioCreator {
    public abstract InicioView FactoryMethod(Usuario user);

    public static BaseView inicioSegunRol(Usuario user) {
        InicioCreator creador;
        if (user.getRol().equalsIgnoreCase("Administrador")) {
            creador = new CarteleraInicioCreator();
        } else {
            creador = new BandejaInicioCreator();
        }
        return creador.FactoryMethod(user).getInicio();
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
