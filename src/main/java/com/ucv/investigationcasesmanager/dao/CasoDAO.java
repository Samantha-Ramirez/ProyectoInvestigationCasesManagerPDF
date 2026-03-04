package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Caso;
import java.util.List;

/*
 * PDyF: Este DAO maneja las operaciones de acceso a datos para la entidad Caso, incluyendo
 * consultas específicas para investigadores y administradores, así como la creación de nuevos
 * casos.
 */
public class CasoDAO extends BaseDAO<Caso> {
    // Obtener casos asignados a un investigador específico
    public List<Caso> obtenerCasosInvestigador(int idUsuario) {
        String sql = "SELECT nro_expediente, estatus, "
                + "CAST(CAST((julianday('now') - julianday(fecha_inicio)) AS INTEGER) AS TEXT)"
                + " || ' días sin atención' AS tiempo_sin_atencion "
                + "FROM caso WHERE id_investigador_asignado = ?";

        return obtenerLista(sql, this::mapearResumenCaso, idUsuario);
    }

    // Obtener todos los casos
    public List<Caso> obtenerCasosAdministrador(int idUsuario) {
        String sql = "SELECT nro_expediente, estatus, "
                + "CAST(CAST((julianday('now') - julianday(fecha_inicio)) AS INTEGER) AS TEXT)"
                + " || ' días sin atención' AS tiempo_sin_atencion "
                + "FROM caso";

        return obtenerLista(sql, this::mapearResumenCaso);
    }

    // Obtener caso por numero de expediente
    public Caso obtenerCasoPorNroExpediente(String nroExpediente) {
        String sql = "SELECT id, nro_expediente, estatus, id_investigador_asignado, "
                + "fecha_inicio, duracion_dias, movil_afectado, objetivo_agraviado, incidencia, "
                + "descripcion_modus_operandi, area_apoyo_resolver, deteccion_procedencia, "
                + "diagnostico_detalle_fraude, conclusiones_recomendaciones, observaciones, soporte "
                + "FROM caso WHERE nro_expediente = ?";

        return obtenerUno(sql, this::mapearDetalleCaso, nroExpediente);
    }

    // Guardar un nuevo caso
    public boolean guardarCaso(Caso caso) {
        String sql = "INSERT INTO caso ("
                + "nro_expediente, fecha_inicio, dias_transcurridos, mes_registro, estatus, "
                + "movil_afectado, objetivo_agraviado, incidencia, duracion_dias, "
                + "descripcion_modus_operandi, area_apoyo_resolver, deteccion_procedencia, "
                + "diagnostico_detalle_fraude, conclusiones_recomendaciones, observaciones, soporte, "
                + "id_investigador_asignado, id_tipo_caso, id_tipo_irregularidad, "
                + "id_subtipo_irregularidad, id_accion_realizada) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return actualizar(sql, caso.getNroExpediente(), caso.getFechaInicio(), caso.getDias(),
                caso.getMes(), caso.getEstatus(), caso.getMovilAfectado(),
                caso.getObjetivoAgraviado(), caso.getIncidencia(), caso.getDuracionDias(),
                caso.getDescripcionModusOperandi(), caso.getAreaApoyoResolver(),
                caso.getDeteccionProcedencia(), caso.getDiagnosticoDetalleFraude(),
                caso.getConclusionesRecomendaciones(), caso.getObservaciones(), caso.getSoporte(),
                caso.getIdInvestigador(), caso.getIdTipoCaso(), caso.getIdTipoIrregularidad(),
                caso.getIdSubtipoIrregularidad(), caso.getIdAccionRealizada()) > 0;
    }

    // Mapear resultado de consulta a un objeto Caso (resumen)
    private Caso mapearResumenCaso(java.sql.ResultSet rs) throws java.sql.SQLException {
        Caso caso = new Caso();
        caso.setNroExpediente(rs.getString("nro_expediente"));
        caso.setEstatus(rs.getString("estatus"));
        caso.setTiempoSinAtencion(rs.getString("tiempo_sin_atencion"));
        return caso;
    }

    // Mapear resultado de consulta a un objeto Caso (detalle)
    private Caso mapearDetalleCaso(java.sql.ResultSet rs) throws java.sql.SQLException {
        Caso caso = new Caso();
        caso.setId(rs.getInt("id"));
        caso.setNroExpediente(rs.getString("nro_expediente"));
        caso.setEstatus(rs.getString("estatus"));
        caso.setIdInvestigador(rs.getInt("id_investigador_asignado"));
        caso.setFechaInicio(rs.getString("fecha_inicio"));
        caso.setDuracionDias(rs.getInt("duracion_dias"));
        caso.setMovilAfectado(rs.getString("movil_afectado"));
        caso.setObjetivoAgraviado(rs.getString("objetivo_agraviado"));
        caso.setIncidencia(rs.getString("incidencia"));
        caso.setDescripcionModusOperandi(rs.getString("descripcion_modus_operandi"));
        caso.setAreaApoyoResolver(rs.getString("area_apoyo_resolver"));
        caso.setDeteccionProcedencia(rs.getString("deteccion_procedencia"));
        caso.setDiagnosticoDetalleFraude(rs.getString("diagnostico_detalle_fraude"));
        caso.setConclusionesRecomendaciones(rs.getString("conclusiones_recomendaciones"));
        caso.setObservaciones(rs.getString("observaciones"));
        caso.setSoporte(rs.getString("soporte"));
        return caso;
    }
}
