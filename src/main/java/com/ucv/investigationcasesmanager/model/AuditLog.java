package com.ucv.investigationcasesmanager.model;

/*
 * Modelo que representa una traza de auditoría del sistema.
 */
public class AuditLog {
    private int id;
    private String username;
    private String action;
    private String actionDate;

    public AuditLog() {}

    public AuditLog(String username, String action, String actionDate) {
        this.username = username;
        this.action = action;
        this.actionDate = actionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }
}
