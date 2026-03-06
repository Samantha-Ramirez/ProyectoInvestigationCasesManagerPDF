package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CaseDAO;
import com.ucv.investigationcasesmanager.dao.CaseFollowUpDAO;
import com.ucv.investigationcasesmanager.dao.UserDAO;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.CaseFollowUp;
import com.ucv.investigationcasesmanager.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para la asignación/reasignación de investigadores a casos (solo administradores)
 */
public class CaseAssignmentController {
    private final CaseDAO caseDAO;
    private final UserDAO userDAO;
    private final CaseFollowUpDAO followUpDAO;

    public CaseAssignmentController() {
        this.caseDAO = new CaseDAO();
        this.userDAO = new UserDAO();
        this.followUpDAO = new CaseFollowUpDAO();
    }

    /**
     * Obtener lista de investigadores disponibles
     */
    public List<User> getAvailableInvestigators() {
        return userDAO.findInvestigators();
    }

    /**
     * Obtener detalle completo de un caso por su ID
     */
    public Case getCaseById(int caseId) {
        return caseDAO.findById(caseId);
    }

    /**
     * Reasignar un caso a un nuevo investigador
     */
    public String reassignCase(int caseId, int newInvestigatorId, User admin) {
        // Validar que sea administrador
        if (!"Administrador".equals(admin.getRole())) {
            return "Solo los administradores pueden reasignar casos";
        }

        // Obtener el caso
        Case caseObj = caseDAO.findById(caseId);
        if (caseObj == null) {
            return "Caso no encontrado";
        }

        // Obtener el investigador anterior (si existe)
        int oldInvestigatorId = caseObj.getInvestigatorId();
        
        // Obtener datos del nuevo investigador
        User newInvestigator = null;
        List<User> investigators = userDAO.findInvestigators();
        for (User u : investigators) {
            if (u.getId() == newInvestigatorId) {
                newInvestigator = u;
                break;
            }
        }
        
        if (newInvestigator == null) {
            return "Investigador no encontrado";
        }

        // 1. Actualizar el caso con el nuevo investigador
        boolean caseUpdated = caseDAO.updateInvestigator(caseId, newInvestigatorId);

        // 2. Registrar un seguimiento automático de la reasignación
        CaseFollowUp followUp = new CaseFollowUp();
        followUp.setCaseId(caseId);
        followUp.setInvestigatorId(admin.getId());
        followUp.setRegistrationDate(LocalDateTime.now());
        
        String activityMsg = "Caso reasignado";
        if (oldInvestigatorId > 0) {
            // Obtener nombre del investigador anterior (simplificado)
            String oldInvestigatorName = "Investigador " + oldInvestigatorId;
            activityMsg = "Caso reasignado de investigador " + oldInvestigatorId + 
                         " a " + newInvestigator.getFirstName() + " " + newInvestigator.getLastName();
        } else {
            activityMsg = "Caso asignado a " + newInvestigator.getFirstName() + " " + newInvestigator.getLastName();
        }
        
        followUp.setActivitiesPerformed(activityMsg);
        followUp.setInvolvedPersons("");
        followUp.setExposedAmount(0.0);
        followUp.setStatus(caseObj.getStatus()); // Mantener el mismo estatus
        followUp.setObservations("Reasignación realizada por administrador");
        followUp.setRecommendations("");
        followUp.setConclusions("");

        boolean followUpSaved = followUpDAO.save(followUp);

        if (caseUpdated && followUpSaved) {
            return null; // éxito
        } else {
            return "Error al reasignar el caso";
        }
    }
}