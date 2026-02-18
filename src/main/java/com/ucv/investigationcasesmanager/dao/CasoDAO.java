package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Caso;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO específico para casos.
 */
public class CasoDAO extends GenericDAO<Caso> {
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
}
