package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Case;
import java.util.List;

/*
 * PDyF: DAO que maneja las operaciones de acceso a datos para la entidad Case, incluyendo consultas
 * para investigadores, administradores y registro de nuevos casos.
 */
public class CaseDAO extends BaseDAO<Case> {

    // Obtener los casos asignados a un investigador específico
    public List<Case> findByInvestigator(int userId) {
        String sql = "SELECT case_number, status, "
                + "CAST(CAST((julianday('now') - julianday(start_date)) AS INTEGER) AS TEXT)"
                + " || ' días sin atención' AS time_without_attention "
                + "FROM investigation_case WHERE investigator_id = ?";
        return queryList(sql, this::mapSummary, userId);
    }

    // Obtener todos los casos del sistema (para administradores)
    public List<Case> findAll() {
        String sql = "SELECT case_number, status, "
                + "CAST(CAST((julianday('now') - julianday(start_date)) AS INTEGER) AS TEXT)"
                + " || ' días sin atención' AS time_without_attention " + "FROM investigation_case";
        return queryList(sql, this::mapSummary);
    }

    // Obtener el detalle completo de un caso por número de expediente
    // Por qué: se selecciona conclusions_recommendations (columna histórica que almacena
    // las conclusiones) y recommendations (columna nueva para recomendaciones separadas).
    public Case findByCaseNumber(String caseNumber) {
        String sql = "SELECT id, case_number, status, investigator_id, "
                + "start_date, duration_days, mobile_affected, objective_victim, incident, "
                + "modus_operandi_description, support_area, detection_origin, "
                + "fraud_diagnosis, conclusions_recommendations, recommendations, "
                + "observations, support "
                + "FROM investigation_case WHERE case_number = ?";
        return queryOne(sql, this::mapDetail, caseNumber);
    }

    // Registrar un nuevo caso en la base de datos
    public boolean save(Case c) {
        String sql = "INSERT INTO investigation_case ("
                + "case_number, start_date, days_elapsed, registration_month, status, "
                + "mobile_affected, objective_victim, incident, duration_days, "
                + "modus_operandi_description, support_area, detection_origin, "
                + "fraud_diagnosis, conclusions_recommendations, recommendations, "
                + "observations, support, "
                + "investigator_id, case_type_id, irregularity_type_id, "
                + "irregularity_subtype_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return execute(sql, c.getCaseNumber(), c.getStartDate(), c.getDays(), c.getMonth(),
                c.getStatus(), c.getMobileAffected(), c.getObjectiveVictim(), c.getIncident(),
                c.getDurationDays(), c.getModusOperandiDescription(), c.getSupportArea(),
                c.getDetectionOrigin(), c.getFraudDiagnosis(), c.getConclusions(),
                c.getRecommendations(), c.getObservations(), c.getSupport(),
                c.getInvestigatorId(), c.getCaseTypeId(),
                c.getIrregularityTypeId(), c.getIrregularitySubtypeId()) > 0;
    }

    // Mapear una fila del ResultSet a un objeto Case (resumen para la bandeja)
    private Case mapSummary(java.sql.ResultSet rs) throws java.sql.SQLException {
        Case c = new Case();
        c.setCaseNumber(rs.getString("case_number"));
        c.setStatus(rs.getString("status"));
        c.setTimeWithoutAttention(rs.getString("time_without_attention"));
        return c;
    }

    // Mapear una fila del ResultSet a un objeto Case (detalle completo)
    private Case mapDetail(java.sql.ResultSet rs) throws java.sql.SQLException {
        Case c = new Case();
        c.setId(rs.getInt("id"));
        c.setCaseNumber(rs.getString("case_number"));
        c.setStatus(rs.getString("status"));
        c.setInvestigatorId(rs.getInt("investigator_id"));
        c.setStartDate(rs.getString("start_date"));
        c.setDurationDays(rs.getInt("duration_days"));
        c.setMobileAffected(rs.getString("mobile_affected"));
        c.setObjectiveVictim(rs.getString("objective_victim"));
        c.setIncident(rs.getString("incident"));
        c.setModusOperandiDescription(rs.getString("modus_operandi_description"));
        c.setSupportArea(rs.getString("support_area"));
        c.setDetectionOrigin(rs.getString("detection_origin"));
        c.setFraudDiagnosis(rs.getString("fraud_diagnosis"));
        c.setConclusions(rs.getString("conclusions_recommendations"));
        c.setRecommendations(rs.getString("recommendations"));
        c.setObservations(rs.getString("observations"));
        c.setSupport(rs.getString("support"));
        return c;
    }
}
