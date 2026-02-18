package com.ucv.investigationcasesmanager.model;

/*
 * Modelo de sesión. PDyF: Esta clase implementa el patrón Singleton para gestionar la sesión del
 * usuario en toda la aplicación. Permite almacenar y acceder al usuario logueado desde cualquier
 * parte del sistema sin necesidad de pasar objetos o referencias entre vistas.
 */
public class Sesion {
    private static Usuario usuarioActual;

    public static void setUsuario(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
