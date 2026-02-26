package com.ucv.investigationcasesmanager.factory;

/*
 * Factory Method para construir el producto de reporte seleccionado por el usuario.
 */
public abstract class ReporteCreator {

    public abstract ReporteProduct factoryMethod();

    public static ReporteCreator desdeTipo(String tipo) {
        if ("Empresas con mayores casos".equals(tipo)) {
            return new EmpresasReporteCreator();
        }
        if ("Investigadores con mayores casos".equals(tipo)) {
            return new InvestigadoresReporteCreator();
        }
        return new CasosRelacionadosReporteCreator();
    }
}


class EmpresasReporteCreator extends ReporteCreator {
    @Override
    public ReporteProduct factoryMethod() {
        return new EmpresasReporteProduct();
    }
}


class InvestigadoresReporteCreator extends ReporteCreator {
    @Override
    public ReporteProduct factoryMethod() {
        return new InvestigadoresReporteProduct();
    }
}


class CasosRelacionadosReporteCreator extends ReporteCreator {
    @Override
    public ReporteProduct factoryMethod() {
        return new CasosRelacionadosReporteProduct();
    }
}
