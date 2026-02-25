package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Seguimiento;
import com.ucv.investigationcasesmanager.model.Caso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAO extends BaseDAO<Seguimiento> {
    // Guardar un nuevo seguimiento en la base de datos
    public boolean guardarSeguimiento(Seguimiento seguimiento) {
        String sql = "INSERT INTO seguimiento ("
                + "id_caso, id_investigador, fecha_registro, actividades_realizadas, "
                + "personas_involucradas, monto_expuesto, estatus, observaciones, "
                + "recomendaciones, conclusiones" + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            int resultado = ejecutarActualizacion(sql, seguimiento.getIdCaso(),
                    seguimiento.getIdInvestigador(),
                    Timestamp.valueOf(seguimiento.getFechaRegistro()),
                    seguimiento.getActividadesRealizadas(), seguimiento.getPersonasInvolucradas(),
                    seguimiento.getMontoExpuesto(), seguimiento.getEstatus(),
                    seguimiento.getObservaciones(), seguimiento.getRecomendaciones(), // NUEVO
                    seguimiento.getConclusiones()); // NUEVO

            System.out.println("Resultado de ejecutarActualizacion: " + resultado);
            return resultado > 0;
        } catch (Exception e) {
            System.err.println("Excepción en guardarSeguimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar el estatus del caso asociado a un seguimiento
    public boolean actualizarEstatusCaso(int idCaso, String nuevoEstatus) {
        String sql = "UPDATE caso SET estatus = ? WHERE id = ?";
        System.out.println("Actualizando estatus del caso ID " + idCaso + " a: " + nuevoEstatus);

        try {
            int resultado = ejecutarActualizacion(sql, nuevoEstatus, idCaso);
            System.out.println("Resultado actualización estatus: " + resultado);
            return resultado > 0;
        } catch (Exception e) {
            System.err.println("Error al actualizar estatus: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los seguimientos asociados a un caso específico
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

                // NUEVOS CAMPOS - Verificar que existan en la BD
                try {
                    s.setRecomendaciones(rs.getString("recomendaciones"));
                } catch (SQLException e) {
                    s.setRecomendaciones("");
                }
                try {
                    s.setConclusiones(rs.getString("conclusiones"));
                } catch (SQLException e) {
                    s.setConclusiones("");
                }

                lista.add(s);
            }
        }, idCaso);

        return lista;
    }

    // Consultar casos asignados a un investigador específico
    public List<Caso> consultarCasosInvestigador(int idInvestigador) {
        return new CasoDAO().consultarCasosInvestigador(idInvestigador);
    }
}
