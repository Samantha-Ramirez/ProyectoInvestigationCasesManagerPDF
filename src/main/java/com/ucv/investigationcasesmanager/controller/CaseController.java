package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CaseDAO;
import com.ucv.investigationcasesmanager.dao.UserDAO;
import com.ucv.investigationcasesmanager.dto.CaseFormData;
import com.ucv.investigationcasesmanager.mediator.RegistrationMediatorClient;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.User;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para las operaciones sobre casos. Contiene toda la validación y lógica de negocio
 * relacionada con el registro y consulta de casos.
 */
public class CaseController {
    private final CaseDAO caseDAO;
    private final UserDAO userDAO;

    public CaseController() {
        this.caseDAO = ServiceLocator.get(CaseDAO.class);
        this.userDAO = ServiceLocator.get(UserDAO.class);
    }

    public List<Case> getCasesForInvestigator(int userId) {
        return caseDAO.findByInvestigator(userId);
    }

    public List<Case> getAllCases() {
        return caseDAO.findAll();
    }

    public Case findByCaseNumber(String caseNumber) {
        return caseDAO.findByCaseNumber(caseNumber);
    }

    /**
     * Construye un objeto Case a partir del formulario, lo valida mediante el mediador y lo guarda.
     * @return null si el registro fue exitoso, o un mensaje de error en caso de fallo
     */
    public String registerCase(CaseFormData data, User currentUser) {
        if (data.investigatorId <= 0) {
            return "No hay investigadores disponibles para asignar.";
        }

        Case c = new Case();
        c.setCaseNumber(data.caseNumber);
        c.setMobileAffected(data.mobileAffected);
        c.setObjectiveVictim(data.objectiveVictim);
        c.setIncident(data.incident);
        c.setModusOperandiDescription(data.modusOperandiDescription);
        c.setSupportArea(data.supportArea);
        c.setDetectionOrigin(data.detectionOrigin);
        c.setFraudDiagnosis(data.fraudDiagnosis);
        c.setConclusionsRecommendations(data.conclusionsRecommendations);
        c.setObservations(data.observations);
        c.setSupport(data.support);
        c.setCaseTypeId(data.caseTypeId);
        c.setIrregularityTypeId(data.irregularityTypeId);
        c.setIrregularitySubtypeId(data.irregularitySubtypeId);
        c.setActionPerformedId(data.actionPerformedId);
        c.setInvestigatorId(data.investigatorId);

        if (!RegistrationMediatorClient.validateAndPrepare(c, currentUser, data.duration)) {
            return "Datos inválidos. Verifique el campo de duración.";
        }

        if (!caseDAO.save(c)) {
            return "Error al guardar el caso en la base de datos.";
        }

        return null;
    }

    public List<User> getInvestigators() {
        return userDAO.findInvestigators();
    }
}
