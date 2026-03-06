package com.ucv.investigationcasesmanager.factory;

/**
 * PDyF: Cliente que utiliza el Factory Method para obtener el reporte correspondiente al tipo
 * solicitado por el usuario.
 */
public class ReportViewFactory {
    private ReportViewFactory() {}

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
