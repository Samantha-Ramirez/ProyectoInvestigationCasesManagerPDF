package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CaseDAO;
import com.ucv.investigationcasesmanager.dao.UserDAO;
import com.ucv.investigationcasesmanager.decorator.AuditSaveDecorator;
import com.ucv.investigationcasesmanager.decorator.ConcreteSaveOperation;
import com.ucv.investigationcasesmanager.dto.CaseFormData;
import com.ucv.investigationcasesmanager.mediator.RegistrationMediatorClient;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.Session;
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
     * Construir un objeto Case a partir del formulario, lo valida mediante el mediador y lo guarda.
     * PDyF: Decorator - envuelve el guardado con AuditSaveDecorator para registrar la traza.
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
        c.setConclusions(data.conclusions);
        c.setRecommendations(data.recommendations);
        c.setObservations(data.observations);
        c.setSupport(data.support);
        c.setCaseTypeId(data.caseTypeId);
        c.setIrregularityTypeId(data.irregularityTypeId);
        c.setIrregularitySubtypeId(data.irregularitySubtypeId);
        c.setInvestigatorId(data.investigatorId);

        if (data.startDate != null && !data.startDate.isBlank()) {
            c.setStartDate(data.startDate);
        }
        c.setDays(data.daysElapsed);
        if (data.month > 0) {
            c.setMonth(data.month);
        }

        if (!RegistrationMediatorClient.validateAndPrepare(c, currentUser, data.duration)) {
            return "Datos inválidos. Verifique el campo de duración.";
        }

        final boolean[] saved = {false};
        String username = currentUsername();
        ConcreteSaveOperation base = new ConcreteSaveOperation(() -> saved[0] = caseDAO.save(c));
        AuditSaveDecorator decorated =
                new AuditSaveDecorator(username, "Registro de caso: " + c.getCaseNumber());
        decorated.setComponent(base);
        decorated.guardar();

        if (!saved[0]) {
            return "Error al guardar el caso en la base de datos.";
        }

        return null;
    }

    public List<User> getInvestigators() {
        return userDAO.findInvestigators();
    }

    private String currentUsername() {
        return Session.getUser() != null
                ? Session.getUser().getFirstName() + " " + Session.getUser().getLastName()
                : "Sistema";
    }
}
