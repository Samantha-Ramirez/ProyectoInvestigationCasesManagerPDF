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
        String sql = "SELECT * FROM seguimiento WHERE id_caso = ? ORDER BY fecha_registro DESC";
        return queryList(sql, this::mapFollowUp, caseId);
    }

    // Guardar un nuevo seguimiento en la base de datos
    public boolean save(CaseFollowUp followUp) {
        String sql = "INSERT INTO seguimiento ("
                + "id_caso, id_investigador, fecha_registro, actividades_realizadas, "
                + "personas_involucradas, monto_expuesto, estatus, observaciones, "
                + "recomendaciones, conclusiones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        String sql = "UPDATE caso SET estatus = ? WHERE id = ?";
        return execute(sql, status, caseId) > 0;
    }

    // Mapear una fila del ResultSet a un objeto CaseFollowUp
    private CaseFollowUp mapFollowUp(java.sql.ResultSet rs) throws java.sql.SQLException {
        CaseFollowUp f = new CaseFollowUp();
        f.setId(rs.getInt("id"));
        f.setCaseId(rs.getInt("id_caso"));
        f.setInvestigatorId(rs.getInt("id_investigador"));
        f.setRegistrationDate(rs.getTimestamp("fecha_registro").toLocalDateTime());
        f.setActivitiesPerformed(rs.getString("actividades_realizadas"));
        f.setInvolvedPersons(rs.getString("personas_involucradas"));
        f.setExposedAmount(rs.getDouble("monto_expuesto"));
        f.setStatus(rs.getString("estatus"));
        f.setObservations(rs.getString("observaciones"));
        f.setRecommendations(rs.getString("recomendaciones"));
        f.setConclusions(rs.getString("conclusiones"));
        return f;
    }
}
