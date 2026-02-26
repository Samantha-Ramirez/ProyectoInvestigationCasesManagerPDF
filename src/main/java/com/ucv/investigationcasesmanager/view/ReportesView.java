package com.ucv.investigationcasesmanager.view;

// import com.ucv.investigationcasesmanager.dao.AuditoriaDAO;
import com.ucv.investigationcasesmanager.controller.ReporteController;
import com.ucv.investigationcasesmanager.factory.ReporteProduct;
import com.ucv.investigationcasesmanager.view.decorator.PanelBaseComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelBordeDecorator;
import com.ucv.investigationcasesmanager.view.decorator.PanelTituloDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Vista de reportes.
 */
public class ReportesView extends BaseView {
    private final ReporteController reporteController;
    // private final AuditoriaDAO auditoriaDAO;
    private JComboBox<String> cmbTipoReporte;

    public ReportesView() {
        super("Reportes", true);
        this.reporteController = new ReporteController();
        // this.auditoriaDAO = new AuditoriaDAO();
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Generar reporte", null, null);

        JPanel tarjeta = crearTarjeta();
        JPanel panelSeleccion = crearPanelSeleccionReporte();

        PanelComponent decorado = new PanelBordeDecorator(
                new PanelTituloDecorator(new PanelBaseComponent(panelSeleccion), "Filtros"), 6, 8,
                6, 8);

        tarjeta.add(decorado.build(), BorderLayout.NORTH);
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
        ReporteProduct reporte = reporteController.resolverReporte(tipo);

        modeloTabla.setColumnIdentifiers(reporte.obtenerColumnas());
        modeloTabla.setRowCount(0);

        List<Object[]> filas = reporteController.generarFilas(reporte);
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
