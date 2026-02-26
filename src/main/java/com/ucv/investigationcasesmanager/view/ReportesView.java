package com.ucv.investigationcasesmanager.view;

// import com.ucv.investigationcasesmanager.dao.AuditoriaDAO;
import com.ucv.investigationcasesmanager.dao.ReporteDAO;
import com.ucv.investigationcasesmanager.factory.ReporteClient;
import com.ucv.investigationcasesmanager.factory.ReporteProduct;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Vista para generar reportes generales y detallados.
 */
public class ReportesView extends BaseView {
    private final ReporteDAO reporteDAO;
    // private final AuditoriaDAO auditoriaDAO;
    private JComboBox<String> cmbTipoReporte;

    public ReportesView() {
        super("Reportes", true);
        this.reporteDAO = new ReporteDAO();
        // this.auditoriaDAO = new AuditoriaDAO();
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Generar reporte", null, null);

        JPanel tarjeta = crearTarjeta();
        tarjeta.add(crearPanelSeleccionReporte(), BorderLayout.NORTH);
        tarjeta.add(crearTabla(new String[] {"Reporte", "Resultado"}), BorderLayout.CENTER);

        panelContenido.add(tarjeta, BorderLayout.CENTER);
    }

    private JPanel crearPanelSeleccionReporte() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);

        panel.add(new JLabel("Tipo de reporte:"));
        cmbTipoReporte = new JComboBox<>(new String[] {"Empresas con mayores casos",
                "Investigadores con mayores casos", "Casos con más de 3 casos relacionados"});
        cmbTipoReporte.setPreferredSize(new Dimension(330, 34));
        panel.add(cmbTipoReporte);

        JButton btnGenerar = crearBotonPrimario("Generar reporte", e -> generarReporte());
        panel.add(btnGenerar);

        return panel;
    }

    private void generarReporte() {
        String tipo = String.valueOf(cmbTipoReporte.getSelectedItem());
        ReporteProduct reporte = ReporteClient.obtenerReporte(tipo);

        modeloTabla.setColumnIdentifiers(reporte.getColumnas());
        modeloTabla.setRowCount(0);

        List<Object[]> filas = reporte.generar(reporteDAO);
        if (filas.isEmpty()) {
            modeloTabla.addRow(new Object[] {"Sin datos", "No hay información para este criterio"});
        } else {
            for (Object[] fila : filas) {
                modeloTabla.addRow(fila);
            }
        }

        // auditoriaDAO.registrarTraza(usuarioActual.getId(),
        // "Generó reporte: " + reporte.getNombre());
    }
}
