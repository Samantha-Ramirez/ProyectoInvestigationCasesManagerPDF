package com.ucv.investigationcasesmanager.factory;

/*
 * Cliente para obtener un reporte concreto sin exponer la lógica de creación.
 */
public class ReporteClient {
    private ReporteClient() {}

    public static ReporteProduct obtenerReporte(String tipo) {
        return ReporteCreator.desdeTipo(tipo).factoryMethod();
    }
}
