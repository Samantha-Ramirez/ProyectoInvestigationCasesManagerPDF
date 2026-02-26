package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.dao.ReporteDAO;

import java.util.List;

/**
 * PDyF: Este código implementa el patrón Factory Method para resolver qué reporte generar según el
 * tipo solicitado
 */

// Producto abstracto
public abstract class ReporteProduct {
    public abstract String obtenerNombre();

    public abstract String[] obtenerColumnas();

    public abstract List<Object[]> generar(ReporteDAO reporteDAO);
}


// Producto concreto para el reporte de empresas con mayores casos
class EmpresasReporteProduct extends ReporteProduct {
    @Override
    public String obtenerNombre() {
        return "Empresas con mayores casos";
    }

    @Override
    public String[] obtenerColumnas() {
        return new String[] {"Empresa", "Casos registrados"};
    }

    @Override
    public List<Object[]> generar(ReporteDAO reporteDAO) {
        return reporteDAO.obtenerEmpresasConMayoresCasos();
    }
}


// Producto concreto para el reporte de investigadores con mayores casos
class InvestigadoresReporteProduct extends ReporteProduct {
    @Override
    public String obtenerNombre() {
        return "Investigadores con mayores casos";
    }

    @Override
    public String[] obtenerColumnas() {
        return new String[] {"Investigador", "Cédula", "Casos atendidos"};
    }

    @Override
    public List<Object[]> generar(ReporteDAO reporteDAO) {
        return reporteDAO.obtenerInvestigadoresConMasCasos();
    }
}


// Producto concreto para el reporte de casos con más de 3 casos relacionados
class CasosRelacionadosReporteProduct extends ReporteProduct {
    @Override
    public String obtenerNombre() {
        return "Casos con más de 3 casos relacionados";
    }

    @Override
    public String[] obtenerColumnas() {
        return new String[] {"Nro. Expediente", "Subtipo relación", "Casos relacionados"};
    }

    @Override
    public List<Object[]> generar(ReporteDAO reporteDAO) {
        return reporteDAO.obtenerCasosConMasDeTresRelacionados();
    }
}
