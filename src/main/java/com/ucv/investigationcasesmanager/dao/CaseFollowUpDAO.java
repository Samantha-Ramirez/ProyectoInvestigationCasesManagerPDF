package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.CaseFollowUp;
import java.sql.Timestamp;
import java.util.List;

/*
 * PDyF: DAO que maneja las operaciones de acceso a datos relacionadas con los
 * seguimientos de casos, incluyendo registro, consulta y actualización de estatus.
 */
public class CaseFollowUpDAO extends BaseDAO<CaseFollowUp> {

    // Obtener todos los seguimientos de un caso ordenados del más reciente al más antiguo
    public List<CaseFollowUp> findByCaseId(int caseId) {
        String sql = "SELECT * FROM case_follow_up WHERE case_id = ? ORDER BY registration_date DESC";
        return queryList(sql, this::mapFollowUp, caseId);
    }

    // Guardar un nuevo seguimiento en la base de datos
    public boolean save(CaseFollowUp followUp) {
        String sql = "INSERT INTO case_follow_up ("
                + "case_id, investigator_id, registration_date, activities_performed, "
                + "involved_persons, exposed_amount, status, observations, "
                + "recommendations, conclusions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return execute(sql,
                followUp.getCaseId(), followUp.getInvestigatorId(),
                Timestamp.valueOf(followUp.getRegistrationDate()),
                followUp.getActivitiesPerformed(), followUp.getInvolvedPersons(),
                followUp.getExposedAmount(), followUp.getStatus(),
                followUp.getObservations(), followUp.getRecommendations(),
                followUp.getConclusions()) > 0;
    }

    // Actualizar el estatus del caso al registrar un nuevo seguimiento
    public boolean updateCaseStatus(int caseId, String status) {
        String sql = "UPDATE investigation_case SET status = ? WHERE id = ?";
        return execute(sql, status, caseId) > 0;
    }

    // Mapear una fila del ResultSet a un objeto CaseFollowUp
    private CaseFollowUp mapFollowUp(java.sql.ResultSet rs) throws java.sql.SQLException {
        CaseFollowUp f = new CaseFollowUp();
        f.setId(rs.getInt("id"));
        f.setCaseId(rs.getInt("case_id"));
        f.setInvestigatorId(rs.getInt("investigator_id"));
        f.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
        f.setActivitiesPerformed(rs.getString("activities_performed"));
        f.setInvolvedPersons(rs.getString("involved_persons"));
        f.setExposedAmount(rs.getDouble("exposed_amount"));
        f.setStatus(rs.getString("status"));
        f.setObservations(rs.getString("observations"));
        f.setRecommendations(rs.getString("recommendations"));
        f.setConclusions(rs.getString("conclusions"));
        return f;
    }
}
