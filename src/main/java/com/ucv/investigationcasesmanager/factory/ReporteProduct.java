package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.dao.ReporteDAO;

import java.util.List;

/*
 * Producto del Factory Method para reportes de negocio.
 */
public abstract class ReporteProduct {
    public abstract String getNombre();

    public abstract String[] getColumnas();

    public abstract List<Object[]> generar(ReporteDAO reporteDAO);
}


class EmpresasReporteProduct extends ReporteProduct {
    @Override
    public String getNombre() {
        return "Empresas con mayores casos";
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Empresa", "Casos registrados"};
    }

    @Override
    public List<Object[]> generar(ReporteDAO reporteDAO) {
        return reporteDAO.consultarEmpresasConMayoresCasos();
    }
}


class InvestigadoresReporteProduct extends ReporteProduct {
    @Override
    public String getNombre() {
        return "Investigadores con mayores casos";
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Investigador", "Cédula", "Casos atendidos"};
    }

    @Override
    public List<Object[]> generar(ReporteDAO reporteDAO) {
        return reporteDAO.consultarInvestigadoresConMasCasos();
    }
}


class CasosRelacionadosReporteProduct extends ReporteProduct {
    @Override
    public String getNombre() {
        return "Casos con más de 3 casos relacionados";
    }

    @Override
    public String[] getColumnas() {
        return new String[] {"Nro. Expediente", "Subtipo relación", "Casos relacionados"};
    }

    @Override
    public List<Object[]> generar(ReporteDAO reporteDAO) {
        return reporteDAO.consultarCasosConMasDeTresRelacionados();
    }
}
