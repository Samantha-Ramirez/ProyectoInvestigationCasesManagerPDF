package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.AuditLogDAO;
import com.ucv.investigationcasesmanager.dao.ReportDAO;
import com.ucv.investigationcasesmanager.factory.ReportClient;
import com.ucv.investigationcasesmanager.factory.ReportProduct;
import com.ucv.investigationcasesmanager.model.Session;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para las operaciones de generación de reportes estadísticos. PDyF: Decorator – cada
 * vez que se genera un reporte, registra la traza de auditoría mediante AuditLogDAO.
 */
public class ReportController {
    private final ReportDAO reportDAO;
    private final AuditLogDAO auditLogDAO;

    public ReportController() {
        this.reportDAO = ServiceLocator.get(ReportDAO.class);
        this.auditLogDAO = ServiceLocator.get(AuditLogDAO.class);
    }

    public ReportProduct resolveReport(String type) {
        return ReportClient.createReport(type);
    }

    public List<Object[]> generateRows(ReportProduct report) {
        List<Object[]> rows = report.generate(reportDAO);
        // Registrar traza de auditoría al generar el reporte
        String username = Session.getUser() != null
                ? Session.getUser().getFirstName() + " " + Session.getUser().getLastName()
                : "Sistema";
        auditLogDAO.save(username, "Generación de reporte: " + report.getName());
        return rows;
    }
}
