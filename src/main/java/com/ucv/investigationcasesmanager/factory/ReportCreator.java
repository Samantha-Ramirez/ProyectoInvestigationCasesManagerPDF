package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Patrón Factory Method - creadores concretos para cada tipo de producto de reporte.
 */

// Creador abstracto
public abstract class ReportCreator {
    public abstract ReportProduct factoryMethod();
}


// Creador concreto
class CompaniesReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() {
        return new CompaniesReportProduct();
    }
}


// Creador concreto
class InvestigatorsReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() {
        return new InvestigatorsReportProduct();
    }
}


// Creador concreto
class RelatedCasesReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() {
        return new RelatedCasesReportProduct();
    }
}
