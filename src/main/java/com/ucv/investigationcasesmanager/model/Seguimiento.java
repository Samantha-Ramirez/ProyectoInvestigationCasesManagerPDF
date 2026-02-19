package com.ucv.investigationcasesmanager.model;

import java.time.LocalDateTime;

public class Seguimiento {
    private int id;
    private int idCaso;
    private int idInvestigador;
    private LocalDateTime fechaRegistro;
    private String actividadesRealizadas;
    private String personasInvolucradas;
    private double montoExpuesto;
    private String estatus; // "En Seguimiento", "Cerrado", "Reabierto"
    private String observaciones;

    public Seguimiento() {
    }

    // Getters y Setters (camelCase)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public int getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(int idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getActividadesRealizadas() {
        return actividadesRealizadas;
    }

    public void setActividadesRealizadas(String actividadesRealizadas) {
        this.actividadesRealizadas = actividadesRealizadas;
    }

    public String getPersonasInvolucradas() {
        return personasInvolucradas;
    }

    public void setPersonasInvolucradas(String personasInvolucradas) {
        this.personasInvolucradas = personasInvolucradas;
    }

    public double getMontoExpuesto() {
        return montoExpuesto;
    }

    public void setMontoExpuesto(double montoExpuesto) {
        this.montoExpuesto = montoExpuesto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}