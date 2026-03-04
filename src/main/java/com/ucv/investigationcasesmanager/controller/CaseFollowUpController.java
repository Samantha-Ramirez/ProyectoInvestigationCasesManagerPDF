package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.CaseDAO;
import com.ucv.investigationcasesmanager.dao.CaseFollowUpDAO;
import com.ucv.investigationcasesmanager.dto.FollowUpFormData;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.CaseFollowUp;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Controlador para las operaciones sobre seguimientos de casos. Contiene toda la validación
 * y lógica de negocio relacionada con el registro de seguimientos.
 */
public class CaseFollowUpController {
    private final CaseFollowUpDAO followUpDAO;
    private final CaseDAO caseDAO;

    public CaseFollowUpController() {
        this.followUpDAO = ServiceLocator.get(CaseFollowUpDAO.class);
        this.caseDAO = ServiceLocator.get(CaseDAO.class);
    }

    public List<CaseFollowUp> getFollowUps(int caseId) {
        return followUpDAO.findByCaseId(caseId);
    }

    public int getCaseId(String caseNumber) {
        Case c = caseDAO.findByCaseNumber(caseNumber);
        return c != null ? c.getId() : -1;
    }

    /**
     * Valida, construye y guarda un seguimiento, luego actualiza el estatus del caso.
     * @return null si el registro fue exitoso, o un mensaje de error en caso de fallo
     */
    public String registerFollowUp(FollowUpFormData data) {
        if (data.activities == null || data.activities.trim().isEmpty()) {
            return "Debe describir las actividades realizadas.";
        }

        double amount = 0;
        String amountStr = data.amountText != null ? data.amountText.trim() : "";
        if (!amountStr.isEmpty() && !amountStr.equals("0.00")) {
            try {
                amount = Double.parseDouble(amountStr.replace(",", "."));
                if (amount < 0) {
                    return "El monto no puede ser negativo.";
                }
            } catch (NumberFormatException e) {
                return "El monto debe ser un número válido.";
            }
        }

        if ("Cerrado".equals(data.status)) {
            if (data.observations == null || data.observations.trim().isEmpty()) {
                return "Debe describir las observaciones.";
            }
            if (data.recommendations == null || data.recommendations.trim().isEmpty()) {
                return "Debe describir las recomendaciones.";
            }
            if (data.conclusions == null || data.conclusions.trim().isEmpty()) {
                return "Debe describir las conclusiones.";
            }
        }

        int caseId = getCaseId(data.caseNumber);
        if (caseId <= 0) {
            return "Error: No se pudo identificar el caso en la base de datos.";
        }

        CaseFollowUp followUp = new CaseFollowUp();
        followUp.setCaseId(caseId);
        followUp.setInvestigatorId(data.investigatorId);
        followUp.setRegistrationDate(LocalDateTime.now());
        followUp.setActivitiesPerformed(data.activities.trim());
        followUp.setInvolvedPersons(data.involvedPersons != null ? data.involvedPersons.trim() : "");
        followUp.setExposedAmount(amount);
        followUp.setStatus(data.status);
        followUp.setObservations(data.observations != null ? data.observations.trim() : "");
        followUp.setRecommendations(data.recommendations != null ? data.recommendations.trim() : "");
        followUp.setConclusions(data.conclusions != null ? data.conclusions.trim() : "");

        if (!followUpDAO.save(followUp)) {
            return "Error al guardar el seguimiento. Verifique la conexión con la base de datos.";
        }

        followUpDAO.updateCaseStatus(caseId, data.status);
        return null;
    }
}
