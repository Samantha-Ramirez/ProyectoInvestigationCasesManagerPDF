package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Seguimiento;
import java.sql.Timestamp;
import java.util.List;

/*
 * PDyF: Este DAO maneja las operaciones de acceso a datos relacionadas con los seguimientos de
 * casos, incluyendo la creación de seguimientos, actualización del estatus del caso y consulta de
 * seguimientos por caso.
 */
public class SeguimientoDAO extends BaseDAO<Seguimiento> {
    // Guardar un nuevo seguimiento
    public boolean guardarSeguimiento(Seguimiento seguimiento) {
        String sql = "INSERT INTO seguimiento ("
                + "id_caso, id_investigador, fecha_registro, actividades_realizadas, "
                + "personas_involucradas, monto_expuesto, estatus, observaciones, "
                + "recomendaciones, conclusiones" + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return ejecutarActualizacion(sql, seguimiento.getIdCaso(), seguimiento.getIdInvestigador(),
                Timestamp.valueOf(seguimiento.getFechaRegistro()),
                seguimiento.getActividadesRealizadas(), seguimiento.getPersonasInvolucradas(),
                seguimiento.getMontoExpuesto(), seguimiento.getEstatus(),
                seguimiento.getObservaciones(), seguimiento.getRecomendaciones(),
                seguimiento.getConclusiones()) > 0;
    }

    // Actualizar el estatus del caso asociado a un seguimiento
    public boolean actualizarEstatusCaso(int idCaso, String estatus) {
        String sql = "UPDATE caso SET estatus = ? WHERE id = ?";
        return ejecutarActualizacion(sql, estatus, idCaso) > 0;
    }

    // Obtener todos los seguimientos asociados a un caso específico
    public List<Seguimiento> obtenerSeguimientosPorCaso(int idCaso) {
        String sql = "SELECT * FROM seguimiento WHERE id_caso = ? ORDER BY fecha_registro DESC";

        return consultarLista(sql, this::mapearSeguimiento, idCaso);
    }

    // Consultar casos asignados a un investigador específico
    public List<Caso> consultarCasosInvestigador(int idInvestigador) {
        return new CasoDAO().consultarCasosInvestigador(idInvestigador);
    }

    // Mapear resultado de consulta a un objeto Seguimiento
    private Seguimiento mapearSeguimiento(java.sql.ResultSet rs) throws java.sql.SQLException {
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
        s.setRecomendaciones(rs.getString("recomendaciones"));
        s.setConclusiones(rs.getString("conclusiones"));
        return s;
    }
}
