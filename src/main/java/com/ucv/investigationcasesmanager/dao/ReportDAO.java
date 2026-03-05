package com.ucv.investigationcasesmanager.dao;

import java.util.List;

/*
 * PDyF: DAO que maneja las consultas para la generación de reportes estadísticos sobre casos,
 * investigadores y empresas.
 */
public class ReportDAO extends BaseDAO<Object[]> {

    // Obtener las empresas con mayor cantidad de casos registrados
    public List<Object[]> findTopCompaniesByCase() {
        String sql = "SELECT COALESCE(objective_victim, 'No especificada') AS empresa, "
                + "COUNT(*) AS total_casos " + "FROM \"case\" "
                + "GROUP BY COALESCE(objective_victim, 'No especificada') "
                + "ORDER BY total_casos DESC, empresa ASC";
        return queryList(sql,
                rs -> new Object[] {rs.getString("empresa"), rs.getInt("total_casos")});
    }

    // Obtener los investigadores con mayor cantidad de casos asignados
    public List<Object[]> findTopInvestigatorsByCase() {
        String sql = "SELECT u.first_name || ' ' || u.last_name AS investigador, u.id_number, "
                + "COUNT(c.id) AS total_casos " + "FROM user u "
                + "LEFT JOIN \"case\" c ON c.investigator_id = u.id "
                + "WHERE u.role = 'Investigador' " + "GROUP BY u.id, investigador, u.id_number "
                + "ORDER BY total_casos DESC, investigador ASC";
        return queryList(sql, rs -> new Object[] {rs.getString("investigador"),
                rs.getString("id_number"), rs.getInt("total_casos")});
    }

    // Obtener los casos que tienen más de 3 casos relacionados por subtipo de irregularidad
    public List<Object[]> findCasesWithMoreThanThreeRelated() {
        String sql = "SELECT c.case_number, "
                + "COALESCE(CAST(c.irregularity_subtype_id AS TEXT), 'Sin subtipo') "
                + "AS subtipo_relacion, r.total_relacionados " + "FROM \"case\" c "
                + "JOIN ( " + "SELECT irregularity_subtype_id, COUNT(*) AS total_relacionados "
                + "FROM \"case\" GROUP BY irregularity_subtype_id HAVING COUNT(*) > 3 "
                + ") r ON c.irregularity_subtype_id = r.irregularity_subtype_id "
                + "ORDER BY r.total_relacionados DESC, c.case_number ASC";
        return queryList(sql, rs -> new Object[] {rs.getString("case_number"),
                rs.getString("subtipo_relacion"), rs.getInt("total_relacionados")});
    }
}
