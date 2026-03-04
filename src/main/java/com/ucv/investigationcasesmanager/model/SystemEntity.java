package com.ucv.investigationcasesmanager.model;

/*
 * Modelo genérico para los catálogos del sistema (entidades simples con id y nombre).
 */
public class SystemEntity {
    private int id;
    private String name;

    public SystemEntity() {}

    public SystemEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
