package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Caso;
import java.util.ArrayList;
import java.util.List;

/*
 * PDyF: Este DAO maneja las operaciones de acceso a datos para la entidad Caso, incluyendo
 * consultas específicas para investigadores y administradores, así como la creación de nuevos
 * casos.
 */
public class CasoDAO extends BaseDAO<Caso> {
    // Consultar casos asignados a un investigador específico
    public List<Caso> consultarCasosInvestigador(int idUsuario) {
        List<Caso> lista = new ArrayList<>();
        String sql = "SELECT nro_expediente, estatus, "
                + "strftime('%d dias_transcurridos', 'now') || ' sin atención' as tiempo "
                + "FROM caso WHERE id_investigador_asignado = ?";

        ejecutarConsulta(sql, rs -> {
            while (rs.next()) {
                Caso caso = new Caso();
                caso.setNroExpediente(rs.getString("nro_expediente"));
                caso.setEstatus(rs.getString("estatus"));
                caso.setTiempoSinAtencion(rs.getString("tiempo"));
                lista.add(caso);
            }
        }, idUsuario);

        return lista;
    }

    // Consultar todos los casos
    public List<Caso> consultarCasosAdministrador(int idUsuario) {
        List<Caso> lista = new ArrayList<>();
        String sql = "SELECT nro_expediente, estatus, "
                + "strftime('%d dias_transcurridos', 'now') || ' sin atención' as tiempo "
                + "FROM caso";

        ejecutarConsulta(sql, rs -> {
            while (rs.next()) {
                Caso caso = new Caso();
                caso.setNroExpediente(rs.getString("nro_expediente"));
                caso.setEstatus(rs.getString("estatus"));
                caso.setTiempoSinAtencion(rs.getString("tiempo"));
                lista.add(caso);
            }
        });

        return lista;
    }

    // Consultar caso por numero de expediente
    public Caso consultarCasoPorNroExpediente(String expediente) {
        String sql = "SELECT id, nro_expediente, estatus, id_investigador_asignado, "
                + "movil_afectado, objetivo_agraviado, incidencia "
                + "FROM caso WHERE nro_expediente = ?";

        Caso[] caso = new Caso[1];

        ejecutarConsulta(sql, rs -> {
            if (rs.next()) {
                Caso c = new Caso();
                c.setId(rs.getInt("id"));
                c.setNroExpediente(rs.getString("nro_expediente"));
                c.setEstatus(rs.getString("estatus"));
                c.setIdInvestigador(rs.getInt("id_investigador_asignado"));
                c.setMovilAfectado(rs.getString("movil_afectado"));
                c.setObjetivoAgraviado(rs.getString("objetivo_agraviado"));
                c.setIncidencia(rs.getString("incidencia"));
                caso[0] = c;
            }
        }, expediente);

        return caso[0];
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

        return ejecutarActualizacion(sql, caso.getNroExpediente(), caso.getFechaInicio(),
                caso.getDias(), caso.getMes(), caso.getEstatus(), caso.getMovilAfectado(),
                caso.getObjetivoAgraviado(), caso.getIncidencia(), caso.getDuracionDias(),
                caso.getDescripcionModusOperandi(), caso.getAreaApoyoResolver(),
                caso.getDeteccionProcedencia(), caso.getDiagnosticoDetalleFraude(),
                caso.getConclusionesRecomendaciones(), caso.getObservaciones(), caso.getSoporte(),
                caso.getIdInvestigador(), caso.getIdTipoCaso(), caso.getIdTipoIrregularidad(),
                caso.getIdSubtipoIrregularidad(), caso.getIdAccionRealizada()) > 0;
    }
}
