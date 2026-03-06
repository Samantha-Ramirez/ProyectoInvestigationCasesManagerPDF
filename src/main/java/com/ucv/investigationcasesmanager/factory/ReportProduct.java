package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.dao.ReportDAO;
import java.util.List;

/**
 * PDyF: Factory Method - resuelve qué tipo de reporte generar según el tipo solicitado.
 */

// Producto abstracto
public abstract class ReportProduct {
    public abstract String getName();

    public abstract String[] getColumns();

    public abstract List<Object[]> generate(ReportDAO reportDAO);
}


// Producto concreto: empresas con mayor cantidad de casos
class CompaniesReportProduct extends ReportProduct {
    @Override
    public String getName() {
        return "Empresas con mayores casos";
    }

    @Override
    public String[] getColumns() {
        return new String[] {"Empresa", "Casos registrados"};
    }

    @Override
    public List<Object[]> generate(ReportDAO reportDAO) {
        return reportDAO.findTopCompaniesByCase();
    }
}


// Producto concreto: investigadores con mayor cantidad de casos
class InvestigatorsReportProduct extends ReportProduct {
    @Override
    public String getName() {
        return "Investigadores con mayores casos";
    }

    @Override
    public String[] getColumns() {
        return new String[] {"Investigador", "Cédula", "Casos atendidos"};
    }

    @Override
    public List<Object[]> generate(ReportDAO reportDAO) {
        return reportDAO.findTopInvestigatorsByCase();
    }
}


// Producto concreto: casos con más de 3 casos relacionados
class RelatedCasesReportProduct extends ReportProduct {
    @Override
    public String getName() {
        return "Casos con más de 3 casos relacionados";
    }

    @Override
    public String[] getColumns() {
        return new String[] {"Nro. Expediente", "Subtipo relación", "Casos relacionados"};
    }

    @Override
    public List<Object[]> generate(ReportDAO reportDAO) {
        return reportDAO.findCasesWithMoreThanThreeRelated();
    }
}
