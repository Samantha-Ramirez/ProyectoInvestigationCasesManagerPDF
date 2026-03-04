package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Client that uses the Factory Method to obtain the appropriate report.
 */
public class ReportClient {
    private ReportClient() {}

    public static ReportProduct createReport(String type) {
        ReportCreator creator;
        if ("Empresas con mayores casos".equals(type)) {
            creator = new CompaniesReportCreator();
        } else if ("Investigadores con mayores casos".equals(type)) {
            creator = new InvestigatorsReportCreator();
        } else {
            creator = new RelatedCasesReportCreator();
        }
        return creator.factoryMethod();
    }
}
