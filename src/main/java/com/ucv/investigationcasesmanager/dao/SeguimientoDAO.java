package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Seguimiento;
import com.ucv.investigationcasesmanager.model.Caso;
import java.sql.*;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAO extends BaseDAO<Seguimiento> {

    // Métodos en camelCase
    public boolean guardarSeguimiento(Seguimiento seguimiento) {
        String sql = """
                    INSERT INTO seguimiento
                    (id_caso, id_investigador, fecha_registro, actividades_realizadas,
                     personas_involucradas, monto_expuesto, estatus, observaciones)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        return ejecutarActualizacion(sql,
                seguimiento.getIdCaso(),
                seguimiento.getIdInvestigador(),
                Timestamp.valueOf(seguimiento.getFechaRegistro()),
                seguimiento.getActividadesRealizadas(),
                seguimiento.getPersonasInvolucradas(),
                seguimiento.getMontoExpuesto(),
                seguimiento.getEstatus(),
                seguimiento.getObservaciones()) > 0;
    }

    public boolean actualizarEstatusCaso(int idCaso, String nuevoEstatus) {
        String sql = "UPDATE caso SET estatus = ? WHERE nro_expediente = " +
                "(SELECT nro_expediente FROM caso WHERE id = ?)";

        return ejecutarActualizacion(sql, nuevoEstatus, idCaso) > 0;
    }

    public List<Seguimiento> obtenerSeguimientosPorCaso(int idCaso) {
        List<Seguimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM seguimiento WHERE id_caso = ? ORDER BY fecha_registro DESC";

        ejecutarConsulta(sql, rs -> {
            while (rs.next()) {
                Seguimiento s = new Seguimiento();
                s.setId(rs.getInt("id"));
                s.setIdCaso(rs.getInt("id_caso"));
                s.setIdInvestigador(rs.getInt("id_investigador"));
                s.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                s.setActividadesRealizadas(rs.getString("actividades_realizadas"));
                s.setPersonasInvolucradas(rs.getString("personas_involucradas"));
                s.setMontoExpuesto(rs.getDouble("monto_expuesto"));
                s.setEstatus(rs.getString("estatus"));
                s.setObservaciones(rs.getString("observaciones"));
                lista.add(s);
            }
        }, idCaso);

        return lista;
    }

    public List<Caso> consultarCasosInvestigador(int idInvestigador) {
        return new CasoDAO().consultarCasosInvestigador(idInvestigador);
    }
}