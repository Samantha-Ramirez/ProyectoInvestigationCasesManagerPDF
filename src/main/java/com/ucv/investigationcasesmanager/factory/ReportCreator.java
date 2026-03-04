package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Factory Method pattern - creators for report products.
 */

// Abstract creator
public abstract class ReportCreator {
    public abstract ReportProduct factoryMethod();
}


class CompaniesReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() { return new CompaniesReportProduct(); }
}


class InvestigatorsReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() { return new InvestigatorsReportProduct(); }
}


class RelatedCasesReportCreator extends ReportCreator {
    @Override
    public ReportProduct factoryMethod() { return new RelatedCasesReportProduct(); }
}
