package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.AuditLogDAO;
import com.ucv.investigationcasesmanager.model.AuditLog;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para UC12 - Consultar Trazas de Auditoría. Centraliza la lectura de los registros de
 * auditoría del sistema.
 */
public class AuditController {
    private final AuditLogDAO auditLogDAO;

    public AuditController() {
        this.auditLogDAO = ServiceLocator.get(AuditLogDAO.class);
    }

    // Obtener todos los registros de auditoría
    public List<AuditLog> getAllLogs() {
        return auditLogDAO.findAll();
    }
}
