package com.ucv.investigationcasesmanager.model;

/*
 * Manages the current user session (Singleton-style static state).
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
