package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.SeguimientoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Seguimiento;
import com.ucv.investigationcasesmanager.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
 * Vista de detalle de caso, mostrando información general y un historial de seguimientos.
 */
public class DetalleCasoView extends BaseView {
    private final Caso casoActual;
    private final Usuario investigadorActual;
    private final SeguimientoDAO seguimientoDAO;

    private DefaultTableModel modeloTablaSeguimientos;

    public DetalleCasoView(Caso caso, Usuario investigador) {
        super("Detalle del Caso - Expediente: " + caso.getNroExpediente(), true, false);

        this.casoActual = caso;
        this.investigadorActual = investigador;
        this.seguimientoDAO = new SeguimientoDAO();

        inicializarComponentesEspecificos();
        cargarSeguimientos();
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Información del caso", "Volver", e -> volverAListado());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 13));
        tabs.addTab("Información general", crearPanelInformacionGeneral());
        tabs.addTab("Historial de seguimientos", crearPanelSeguimientos());
        panelContenido.add(tabs, BorderLayout.CENTER);

        if ("Investigador".equals(usuarioActual.getRol())) {
            JButton btnNuevoSeguimiento = crearBotonPrimario("Nuevo seguimiento", e -> {
                new RegistroSeguimientoView(casoActual, investigadorActual).setVisible(true);
                dispose();
            });
            panelContenido.add(crearPanelAccionesInferior(btnNuevoSeguimiento), BorderLayout.SOUTH);
        }
    }

    private void volverAListado() {
        if ("Administrador".equals(usuarioActual.getRol())) {
            configurarVista(this, new CarteleraView());
        } else {
            configurarVista(this, new BandejaView());
        }
    }

    private JComponent crearPanelInformacionGeneral() {
        JPanel card = crearTarjeta();
        JPanel form = crearFormularioEtiquetado();

        int fila = 0;
        fila = agregarCampoEtiquetado(form, fila, "Expediente",
                new JLabel(casoActual.getNroExpediente()));
        fila = agregarCampoEtiquetado(form, fila, "Estatus", new JLabel(casoActual.getEstatus()));
        fila = agregarCampoEtiquetado(form, fila, "Fecha de inicio", new JLabel(
                casoActual.getFechaInicio() != null ? casoActual.getFechaInicio() : "N/A"));
        fila = agregarCampoEtiquetado(form, fila, "Duración (días)",
                new JLabel(String.valueOf(casoActual.getDuracionDias())));
        fila = agregarCampoEtiquetado(form, fila, "Móvil afectado", new JLabel(
                casoActual.getMovilAfectado() != null ? casoActual.getMovilAfectado() : "N/A"));
        fila = agregarCampoEtiquetado(form, fila, "Objetivo/Agraviado",
                new JLabel(casoActual.getObjetivoAgraviado() != null
                        ? casoActual.getObjetivoAgraviado()
                        : "N/A"));
        fila = agregarCampoEtiquetado(form, fila, "Incidencia", new JLabel(
                casoActual.getIncidencia() != null ? casoActual.getIncidencia() : "N/A"));

        JTextArea txtModus = crearAreaTextoEstilizada(3, 30, 80);
        txtModus.setEditable(false);
        txtModus.setText(casoActual.getDescripcionModusOperandi() != null
                ? casoActual.getDescripcionModusOperandi()
                : "N/A");
        fila = agregarCampoEtiquetado(form, fila, "Modus operandi", envolverEnScroll(txtModus));

        JTextArea txtObs = crearAreaTextoEstilizada(3, 30, 70);
        txtObs.setEditable(false);
        txtObs.setText(
                casoActual.getObservaciones() != null ? casoActual.getObservaciones() : "N/A");
        agregarCampoEtiquetado(form, fila, "Observaciones", envolverEnScroll(txtObs));

        card.add(envolverEnScroll(form), BorderLayout.CENTER);
        return card;
    }

    private JComponent crearPanelSeguimientos() {
        JPanel card = crearTarjeta();

        String[] columnas =
                {"Fecha", "Actividades", "Personas", "Monto", "Estatus", "Observaciones"};
        modeloTablaSeguimientos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tablaSeguimientos = new JTable(modeloTablaSeguimientos);
        tablaSeguimientos.setRowHeight(32);
        tablaSeguimientos.getTableHeader().setReorderingAllowed(false);
        uiFactory.estilizarTabla(tablaSeguimientos);

        card.add(new JScrollPane(tablaSeguimientos), BorderLayout.CENTER);
        return card;
    }

    private void cargarSeguimientos() {
        modeloTablaSeguimientos.setRowCount(0);

        List<Seguimiento> seguimientos =
                seguimientoDAO.obtenerSeguimientosPorCaso(casoActual.getId());
        if (seguimientos.isEmpty()) {
            modeloTablaSeguimientos
                    .addRow(new Object[] {"No hay seguimientos registrados", "", "", "", "", ""});
            return;
        }

        for (Seguimiento s : seguimientos) {
            modeloTablaSeguimientos.addRow(new Object[] {
                    s.getFechaRegistro().toString().substring(0, 10), s.getActividadesRealizadas(),
                    s.getPersonasInvolucradas() != null ? s.getPersonasInvolucradas() : "",
                    String.format("$%,.2f", s.getMontoExpuesto()), s.getEstatus(),
                    s.getObservaciones() != null ? s.getObservaciones() : ""});
        }
    }
}
