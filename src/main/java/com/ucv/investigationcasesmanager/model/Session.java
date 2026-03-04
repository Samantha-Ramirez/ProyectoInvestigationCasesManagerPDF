package com.ucv.investigationcasesmanager.model;

/*
 * PDyF: Singleton estático que gestiona la sesión del usuario activo en toda la aplicación.
 * Permite acceder al usuario autenticado desde cualquier parte del sistema sin pasar referencias.
 */
public class Session {
    private static User currentUser;

    private Session() {}

    public static void setUser(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
