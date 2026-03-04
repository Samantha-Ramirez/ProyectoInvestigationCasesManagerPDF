package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Patrón Factory Method - creadores concretos para cada tipo de producto de reporte.
 */

// Creador abstracto
public abstract class ReportCreator {
    public abstract ReportProduct factoryMethod();
}


class CompaniesReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() {
        return new CompaniesReportProduct();
    }
}


class InvestigatorsReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() {
        return new InvestigatorsReportProduct();
    }
}


class RelatedCasesReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() {
        return new RelatedCasesReportProduct();
    }
}
