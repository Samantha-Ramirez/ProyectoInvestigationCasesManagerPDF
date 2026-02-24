package com.ucv.investigationcasesmanager.model;

/*
 * Modelo de caso.
 */
public class Caso {
    private int id;
    private String nroExpediente;
    private String fechaInicio;
    private int dias;
    private int mes;
    private int duracionDias;
    private String tiempoSinAtencion;

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
    private int idTipoCaso;
    private int idTipoIrregularidad;
    private int idSubtipoIrregularidad;
    private int idAccionRealizada;

    public Caso() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNroExpediente() {
        return nroExpediente;
    }

    public void setNroExpediente(String nroExpediente) {
        this.nroExpediente = nroExpediente;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public String getTiempoSinAtencion() {
        return tiempoSinAtencion;
    }

    public void setTiempoSinAtencion(String tiempo) {
        this.tiempoSinAtencion = tiempo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMovilAfectado() {
        return movilAfectado;
    }

    public void setMovilAfectado(String movilAfectado) {
        this.movilAfectado = movilAfectado;
    }

    public String getObjetivoAgraviado() {
        return objetivoAgraviado;
    }

    public void setObjetivoAgraviado(String objetivoAgraviado) {
        this.objetivoAgraviado = objetivoAgraviado;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public String getDescripcionModusOperandi() {
        return descripcionModusOperandi;
    }

    public void setDescripcionModusOperandi(String descripcionModusOperandi) {
        this.descripcionModusOperandi = descripcionModusOperandi;
    }

    public String getAreaApoyoResolver() {
        return areaApoyoResolver;
    }

    public void setAreaApoyoResolver(String areaApoyoResolver) {
        this.areaApoyoResolver = areaApoyoResolver;
    }

    public String getDeteccionProcedencia() {
        return deteccionProcedencia;
    }

    public void setDeteccionProcedencia(String deteccionProcedencia) {
        this.deteccionProcedencia = deteccionProcedencia;
    }

    public String getDiagnosticoDetalleFraude() {
        return diagnosticoDetalleFraude;
    }

    public void setDiagnosticoDetalleFraude(String diagnosticoDetalleFraude) {
        this.diagnosticoDetalleFraude = diagnosticoDetalleFraude;
    }

    public String getConclusionesRecomendaciones() {
        return conclusionesRecomendaciones;
    }

    public void setConclusionesRecomendaciones(String conclusionesRecomendaciones) {
        this.conclusionesRecomendaciones = conclusionesRecomendaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getSoporte() {
        return soporte;
    }

    public void setSoporte(String soporte) {
        this.soporte = soporte;
    }

    public int getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(int idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public int getIdTipoCaso() {
        return idTipoCaso;
    }

    public void setIdTipoCaso(int idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public int getIdTipoIrregularidad() {
        return idTipoIrregularidad;
    }

    public void setIdTipoIrregularidad(int idTipoIrregularidad) {
        this.idTipoIrregularidad = idTipoIrregularidad;
    }

    public int getIdSubtipoIrregularidad() {
        return idSubtipoIrregularidad;
    }

    public void setIdSubtipoIrregularidad(int idSubtipoIrregularidad) {
        this.idSubtipoIrregularidad = idSubtipoIrregularidad;
    }

    public int getIdAccionRealizada() {
        return idAccionRealizada;
    }

    public void setIdAccionRealizada(int idAccionRealizada) {
        this.idAccionRealizada = idAccionRealizada;
    }
}
