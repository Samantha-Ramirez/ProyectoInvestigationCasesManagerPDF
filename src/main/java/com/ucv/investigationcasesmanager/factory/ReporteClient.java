package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Este código implementa el patrón Factory Method para resolver qué reporte generar según el
 * tipo solicitado
 */

// Cliente que obtiene un reporte concreto sin exponer la lógica de creación
public class ReporteClient {
    private ReporteClient() {}

    public static ReporteProduct obtenerReporte(String tipo) {
        ReporteCreator creador;
        if ("Empresas con mayores casos".equals(tipo)) {
            creador = new EmpresasReporteCreator();
        } else if ("Investigadores con mayores casos".equals(tipo)) {
            creador = new InvestigadoresReporteCreator();
        } else {
            creador = new CasosRelacionadosReporteCreator();
        }
        return creador.factoryMethod();
    }
}
