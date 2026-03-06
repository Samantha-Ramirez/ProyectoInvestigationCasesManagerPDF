package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CaseDAO;
import com.ucv.investigationcasesmanager.dao.CaseFollowUpDAO;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.CaseFollowUp;
import com.ucv.investigationcasesmanager.model.User;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para la reapertura de casos (solo administradores)
 */
public class CaseReopenController {
    private final CaseDAO caseDAO;
    private final CaseFollowUpDAO followUpDAO;

    public CaseReopenController() {
        this.caseDAO = new CaseDAO();
        this.followUpDAO = new CaseFollowUpDAO();
    }

    /**
     * Obtener casos cerrados para mostrar al administrador
     */
    public List<Case> getClosedCases() {
        return caseDAO.findClosedCases();
    }

    /**
     * Obtener detalle completo de un caso por su ID
     */
    public Case getCaseById(int caseId) {
        return caseDAO.findById(caseId);
    }

    /**
     * Reabrir un caso: actualizar estatus y soporte, y registrar seguimiento automático
     */
    public String reopenCase(int caseId, String newSupport, User admin) {
        // Validar que sea administrador
        if (!"Administrador".equals(admin.getRole())) {
            return "Solo los administradores pueden reabrir casos";
        }

        // Obtener el caso para verificar que está cerrado
        Case caseObj = caseDAO.findById(caseId);
        if (caseObj == null) {
            return "Caso no encontrado";
        }
        if (!"Cerrado".equals(caseObj.getStatus())) {
            return "Solo se pueden reabrir casos cerrados";
        }

        boolean statusUpdated = caseDAO.updateStatus(caseId, "Reabierto");

        boolean supportUpdated = caseDAO.updateSupport(caseId, newSupport);

        CaseFollowUp followUp = new CaseFollowUp();
        followUp.setCaseId(caseId);
        followUp.setInvestigatorId(admin.getId());
        followUp.setRegistrationDate(LocalDateTime.now());
        followUp.setActivitiesPerformed("Caso reabierto por administrador");
        followUp.setInvolvedPersons("");
        followUp.setExposedAmount(0.0);
        followUp.setStatus("Reabierto");
        followUp.setObservations("Reapertura del caso");
        followUp.setRecommendations("");
        followUp.setConclusions("");

        boolean followUpSaved = followUpDAO.save(followUp);

        if (statusUpdated && supportUpdated && followUpSaved) {
            return null;
        } else {
            return "Error al reabrir el caso";
        }
    }
}
