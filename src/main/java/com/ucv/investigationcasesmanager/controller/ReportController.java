package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.ReportDAO;
import com.ucv.investigationcasesmanager.factory.ReportClient;
import com.ucv.investigationcasesmanager.factory.ReportProduct;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para las operaciones de generación de reportes estadísticos.
 */
public class ReportController {
    private final ReportDAO reportDAO;

    public ReportController() {
        this.reportDAO = ServiceLocator.get(ReportDAO.class);
    }

    public ReportProduct resolveReport(String type) {
        return ReportClient.createReport(type);
    }

    public List<Object[]> generateRows(ReportProduct report) {
        return report.generate(reportDAO);
    }
}
