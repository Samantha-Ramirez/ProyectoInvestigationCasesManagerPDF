package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Case;
import java.util.List;

/*
 * PDyF: DAO que maneja las operaciones de acceso a datos para la entidad Case,
 * incluyendo consultas para investigadores, administradores y registro de nuevos casos.
 */
public class CaseDAO extends BaseDAO<Case> {

    // Obtener los casos asignados a un investigador específico
    public List<Case> findByInvestigator(int userId) {
        String sql = "SELECT nro_expediente, estatus, "
                + "CAST(CAST((julianday('now') - julianday(fecha_inicio)) AS INTEGER) AS TEXT)"
                + " || ' días sin atención' AS tiempo_sin_atencion "
                + "FROM caso WHERE id_investigador_asignado = ?";
        return queryList(sql, this::mapSummary, userId);
    }

    // Obtener todos los casos del sistema (para administradores)
    public List<Case> findAll() {
        String sql = "SELECT nro_expediente, estatus, "
                + "CAST(CAST((julianday('now') - julianday(fecha_inicio)) AS INTEGER) AS TEXT)"
                + " || ' días sin atención' AS tiempo_sin_atencion "
                + "FROM caso";
        return queryList(sql, this::mapSummary);
    }

    // Obtener el detalle completo de un caso por número de expediente
    public Case findByCaseNumber(String caseNumber) {
        String sql = "SELECT id, nro_expediente, estatus, id_investigador_asignado, "
                + "fecha_inicio, duracion_dias, movil_afectado, objetivo_agraviado, incidencia, "
                + "descripcion_modus_operandi, area_apoyo_resolver, deteccion_procedencia, "
                + "diagnostico_detalle_fraude, conclusiones_recomendaciones, observaciones, soporte "
                + "FROM caso WHERE nro_expediente = ?";
        return queryOne(sql, this::mapDetail, caseNumber);
    }

    // Registrar un nuevo caso en la base de datos
    public boolean save(Case c) {
        String sql = "INSERT INTO caso ("
                + "nro_expediente, fecha_inicio, dias_transcurridos, mes_registro, estatus, "
                + "movil_afectado, objetivo_agraviado, incidencia, duracion_dias, "
                + "descripcion_modus_operandi, area_apoyo_resolver, deteccion_procedencia, "
                + "diagnostico_detalle_fraude, conclusiones_recomendaciones, observaciones, soporte, "
                + "id_investigador_asignado, id_tipo_caso, id_tipo_irregularidad, "
                + "id_subtipo_irregularidad, id_accion_realizada) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return execute(sql,
                c.getCaseNumber(), c.getStartDate(), c.getDays(), c.getMonth(), c.getStatus(),
                c.getMobileAffected(), c.getObjectiveVictim(), c.getIncident(), c.getDurationDays(),
                c.getModusOperandiDescription(), c.getSupportArea(), c.getDetectionOrigin(),
                c.getFraudDiagnosis(), c.getConclusionsRecommendations(), c.getObservations(),
                c.getSupport(), c.getInvestigatorId(), c.getCaseTypeId(),
                c.getIrregularityTypeId(), c.getIrregularitySubtypeId(),
                c.getActionPerformedId()) > 0;
    }

    // Mapear una fila del ResultSet a un objeto Case (resumen para la bandeja)
    private Case mapSummary(java.sql.ResultSet rs) throws java.sql.SQLException {
        Case c = new Case();
        c.setCaseNumber(rs.getString("nro_expediente"));
        c.setStatus(rs.getString("estatus"));
        c.setTimeWithoutAttention(rs.getString("tiempo_sin_atencion"));
        return c;
    }

    // Mapear una fila del ResultSet a un objeto Case (detalle completo)
    private Case mapDetail(java.sql.ResultSet rs) throws java.sql.SQLException {
        Case c = new Case();
        c.setId(rs.getInt("id"));
        c.setCaseNumber(rs.getString("nro_expediente"));
        c.setStatus(rs.getString("estatus"));
        c.setInvestigatorId(rs.getInt("id_investigador_asignado"));
        c.setStartDate(rs.getString("fecha_inicio"));
        c.setDurationDays(rs.getInt("duracion_dias"));
        c.setMobileAffected(rs.getString("movil_afectado"));
        c.setObjectiveVictim(rs.getString("objetivo_agraviado"));
        c.setIncident(rs.getString("incidencia"));
        c.setModusOperandiDescription(rs.getString("descripcion_modus_operandi"));
        c.setSupportArea(rs.getString("area_apoyo_resolver"));
        c.setDetectionOrigin(rs.getString("deteccion_procedencia"));
        c.setFraudDiagnosis(rs.getString("diagnostico_detalle_fraude"));
        c.setConclusionsRecommendations(rs.getString("conclusiones_recomendaciones"));
        c.setObservations(rs.getString("observaciones"));
        c.setSupport(rs.getString("soporte"));
        return c;
    }
}
