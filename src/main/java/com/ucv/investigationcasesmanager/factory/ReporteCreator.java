package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Este código implementa el patrón Factory Method para resolver qué reporte generar según el
 * tipo solicitado
 */

// Creador abstracto
public abstract class ReporteCreator {
    public abstract ReporteProduct factoryMethod();
}


// Creador concreto especializado en crear el reporte de empresas con mayores casos
class EmpresasReporteCreator extends ReporteCreator {
    @Override
    public ReporteProduct factoryMethod() {
        return new EmpresasReporteProduct();
    }
}


// Creador concreto especializado en crear el reporte de investigadores con mayores casos
class InvestigadoresReporteCreator extends ReporteCreator {
    @Override
    public ReporteProduct factoryMethod() {
        return new InvestigadoresReporteProduct();
    }
}


// Creador concreto especializado en crear el reporte de casos con más de 3 casos relacionados
class CasosRelacionadosReporteCreator extends ReporteCreator {
    @Override
    public ReporteProduct factoryMethod() {
        return new CasosRelacionadosReporteProduct();
    }
}
