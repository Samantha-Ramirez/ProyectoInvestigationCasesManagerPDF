package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Caso;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO específico para casos.
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
