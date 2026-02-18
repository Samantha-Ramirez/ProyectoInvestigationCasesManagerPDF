package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.view.BaseView;

/**
 * PDyF: Este código implementa el patrón Factory Method para decidir qué vista de inicio mostrar
 * según el rol del usuario (Investigador o Administrador)
 */

// Cliente que utiliza el Factory Method para obtener la vista de inicio según el rol del usuario
public class InicioClient {
    public static BaseView inicioSegunRol(String rol) {
        InicioCreator creador;
        if (rol.equalsIgnoreCase("Administrador")) {
            creador = new CarteleraInicioCreator();
        } else {
            creador = new BandejaInicioCreator();
        }
        return creador.FactoryMethod().getVista();
    }
}
