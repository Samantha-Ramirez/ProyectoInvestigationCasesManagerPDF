package com.ucv.investigationcasesmanager.model;

public class Caso {
    private String nroExpediente;
    private String fechaInicio;
    private int dias;
    private int mes;
    private int duracionDias;

    private String estatus;
    private String movilAfectado;
    private String objetivoAgraviado;
    private String incidencia;
    private String descripcionModusOperandi;
    private String areaApoyoResolver;
    private String deteccionProcedencia;
    private String diagnosticoDetalleFraude;
    private String conclusionesRecomendaciones;
    private String observaciones;
    private String soporte;

    private int idInvestigador;
    private int idTipoCaso; // Gestión, Reclamo o Caso
    private int idTipoIrregularidad;
    private int idSubtipoIrregularidad;
    private int idAccionRealizada;

    public Caso() {}

    public String getNroExpediente() {
        return nroExpediente;
    }

    public void setNroExpediente(String nroExpediente) {
        this.nroExpediente = nroExpediente;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
