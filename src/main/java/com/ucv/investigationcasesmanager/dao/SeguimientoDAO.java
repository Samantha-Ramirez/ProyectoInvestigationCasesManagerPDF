package com.ucv.investigationcasesmanager.dao;

import com.ucv.investigationcasesmanager.model.Seguimiento;
import com.ucv.investigationcasesmanager.model.Caso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguimientoDAO extends BaseDAO<Seguimiento> {

    public boolean guardarSeguimiento(Seguimiento seguimiento) {
        String sql = "INSERT INTO seguimiento (" +
                "id_caso, id_investigador, fecha_registro, actividades_realizadas, " +
                "personas_involucradas, monto_expuesto, estatus, observaciones" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        System.out.println("SQL a ejecutar: " + sql);
        System.out.println("ID Caso: " + seguimiento.getIdCaso());
        System.out.println("ID Investigador: " + seguimiento.getIdInvestigador());
        System.out.println("Actividades: " + seguimiento.getActividadesRealizadas());

        try {
            int resultado = ejecutarActualizacion(sql,
                    seguimiento.getIdCaso(),
                    seguimiento.getIdInvestigador(),
                    Timestamp.valueOf(seguimiento.getFechaRegistro()),
                    seguimiento.getActividadesRealizadas(),
                    seguimiento.getPersonasInvolucradas(),
                    seguimiento.getMontoExpuesto(),
                    seguimiento.getEstatus(),
                    seguimiento.getObservaciones());
            
            System.out.println("Resultado de ejecutarActualizacion: " + resultado);
            return resultado > 0;
        } catch (Exception e) {
            System.err.println("Excepción en guardarSeguimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

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