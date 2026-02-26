package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.ReporteDAO;
import com.ucv.investigationcasesmanager.factory.ReporteClient;
import com.ucv.investigationcasesmanager.factory.ReporteProduct;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para reportes.
 */
public class ReporteController {
    private final ReporteDAO reporteDAO;

    public ReporteController() {
        this.reporteDAO = ServiceLocator.obtenerServicio(ReporteDAO.class);
    }

    // Resolver reporte específico según el tipo solicitado
    public ReporteProduct resolverReporte(String tipo) {
        return ReporteClient.obtenerReporte(tipo);
    }

    // Generar filas de datos para el reporte solicitado
    public List<Object[]> generarFilas(ReporteProduct reporte) {
        return reporte.generar(reporteDAO);
    }
}
