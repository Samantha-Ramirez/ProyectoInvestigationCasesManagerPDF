package com.ucv.investigationcasesmanager.dao;

import java.util.List;

/*
 * PDyF: DAO for report-related queries.
 */
public class ReportDAO extends BaseDAO<Object[]> {

    public List<Object[]> findTopCompaniesByCase() {
        String sql = "SELECT COALESCE(objetivo_agraviado, 'No especificada') AS empresa, "
                + "COUNT(*) AS total_casos "
                + "FROM caso "
                + "GROUP BY COALESCE(objetivo_agraviado, 'No especificada') "
                + "ORDER BY total_casos DESC, empresa ASC";
        return queryList(sql, rs -> new Object[]{rs.getString("empresa"), rs.getInt("total_casos")});
    }

    public List<Object[]> findTopInvestigatorsByCase() {
        String sql = "SELECT u.nombre || ' ' || u.apellido AS investigador, u.cedula, "
                + "COUNT(c.id) AS total_casos "
                + "FROM usuario u "
                + "LEFT JOIN caso c ON c.id_investigador_asignado = u.id "
                + "WHERE u.rol = 'Investigador' "
                + "GROUP BY u.id, investigador, u.cedula "
                + "ORDER BY total_casos DESC, investigador ASC";
        return queryList(sql, rs -> new Object[]{
                rs.getString("investigador"), rs.getString("cedula"), rs.getInt("total_casos")});
    }

    public List<Object[]> findCasesWithMoreThanThreeRelated() {
        String sql = "SELECT c.nro_expediente, "
                + "COALESCE(CAST(c.id_subtipo_irregularidad AS TEXT), 'Sin subtipo') "
                + "AS subtipo_relacion, r.total_relacionados "
                + "FROM caso c "
                + "JOIN ( "
                + "SELECT id_subtipo_irregularidad, COUNT(*) AS total_relacionados "
                + "FROM caso GROUP BY id_subtipo_irregularidad HAVING COUNT(*) > 3 "
                + ") r ON c.id_subtipo_irregularidad = r.id_subtipo_irregularidad "
                + "ORDER BY r.total_relacionados DESC, c.nro_expediente ASC";
        return queryList(sql, rs -> new Object[]{
                rs.getString("nro_expediente"), rs.getString("subtipo_relacion"),
                rs.getInt("total_relacionados")});
    }
}
