package com.ucv.investigationcasesmanager.decorator;

import com.ucv.investigationcasesmanager.dao.AuditLogDAO;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

/**
 * PDyF: Decorator – ConcreteDecorator que añade el registro automático de auditoría tras ejecutar
 * la operación de guardado subyacente.
 */
public class AuditSaveDecorator extends SaveDecorator {
    private final String username;
    private final String actionDescription;
    private final AuditLogDAO auditLogDAO;

    public AuditSaveDecorator(String username, String actionDescription) {
        this.username = username;
        this.actionDescription = actionDescription;
        this.auditLogDAO = ServiceLocator.get(AuditLogDAO.class);
    }

    @Override
    public void guardar() {
        super.guardar();
        registrarAuditoria();
    }

    // Comportamiento extra añadido por el decorador: registra la traza de auditoría
    private void registrarAuditoria() {
        auditLogDAO.save(username, actionDescription);
    }
}
