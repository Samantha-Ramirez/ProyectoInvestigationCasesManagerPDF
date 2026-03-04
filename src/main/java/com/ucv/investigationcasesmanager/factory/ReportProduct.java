package com.ucv.investigationcasesmanager.factory;

import com.ucv.investigationcasesmanager.dao.ReportDAO;
import java.util.List;

/**
 * PDyF: Factory Method pattern - resolves which report to generate based on requested type.
 */

// Abstract product
public abstract class ReportProduct {
    public abstract String getName();
    public abstract String[] getColumns();
    public abstract List<Object[]> generate(ReportDAO reportDAO);
}


// Concrete product for companies with most cases
class CompaniesReportProduct extends ReportProduct {
    @Override
    public String getName() { return "Empresas con mayores casos"; }

    @Override
    public String[] getColumns() { return new String[]{"Empresa", "Casos registrados"}; }

    @Override
    public List<Object[]> generate(ReportDAO reportDAO) {
        return reportDAO.findTopCompaniesByCase();
    }
}


// Concrete product for investigators with most cases
class InvestigatorsReportProduct extends ReportProduct {
    @Override
    public String getName() { return "Investigadores con mayores casos"; }

    @Override
    public String[] getColumns() { return new String[]{"Investigador", "Cédula", "Casos atendidos"}; }

    @Override
    public List<Object[]> generate(ReportDAO reportDAO) {
        return reportDAO.findTopInvestigatorsByCase();
    }
}


// Concrete product for cases with more than 3 related cases
class RelatedCasesReportProduct extends ReportProduct {
    @Override
    public String getName() { return "Casos con más de 3 casos relacionados"; }

    @Override
    public String[] getColumns() {
        return new String[]{"Nro. Expediente", "Subtipo relación", "Casos relacionados"};
    }

    @Override
    public List<Object[]> generate(ReportDAO reportDAO) {
        return reportDAO.findCasesWithMoreThanThreeRelated();
    }
}
