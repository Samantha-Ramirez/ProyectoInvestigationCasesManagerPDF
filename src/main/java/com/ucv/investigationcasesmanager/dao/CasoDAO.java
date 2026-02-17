package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Caso;
import java.util.ArrayList;
import java.util.List;

public class CasoDAO extends GenericDAO<Caso> {

    public List<Caso> listarCasosPorInvestigador(int idInvestigador) {
        List<Caso> lista = new ArrayList<>();
        // Query que simula el "tiempo sin atención" basándose en la fecha actual
        String sql = "SELECT nro_expediente, estatus, "
                + "strftime('%d dias_transcurridos', 'now') || ' sin atención' as tiempo "
                + "FROM casos WHERE id_investigador_asignado = ?";

        ejecutarConsulta(sql, rs -> {
            while (rs.next()) {
                Caso caso = new Caso();
                caso.setNroExpediente(rs.getString("nro_expediente"));
                caso.setEstatus(rs.getString("estatus"));
                caso.setTiempoSinAtencion(rs.getString("tiempo"));
                lista.add(caso);
            }
        }, idInvestigador);

        return lista;
    }
}
