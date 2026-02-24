package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.dao.SeguimientoDAO;
import com.ucv.investigationcasesmanager.model.Seguimiento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DetalleCasoView extends BaseView {

    private Caso casoActual;
    private Usuario investigadorActual;
    private SeguimientoDAO seguimientoDAO;

    // Componentes de la interfaz
    private JTabbedPane panelPestanas;
    private JPanel panelInformacionGeneral;
    private JPanel panelSeguimientos;
    private JTable tablaSeguimientos;
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
        // Crear botón redondeado para "Volver" con comportamiento según rol
        JButton btnVolverRedondeado =
                crearBotonRedondeado("Volver", new Color(235, 235, 235), e -> {
                    // Volver a la vista anterior según el rol
                    if ("Administrador".equals(usuarioActual.getRol())) {
                        configurarVista(this, new CarteleraView());
                    } else {
                        configurarVista(this, new BandejaView());
                    }
                });
            btnVolverRedondeado.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolverRedondeado.setPreferredSize(new Dimension(120, 35));

        // Usar el botón redondeado en el título superior
        configurarTituloSuperiorConBotonRedondeado("Información del Caso", btnVolverRedondeado);

        // Crear panel de pestañas
        panelPestanas = new JTabbedPane();
        panelPestanas.setFont(new Font("Arial", Font.PLAIN, 14));

        // Panel de información general
        panelInformacionGeneral = crearPanelInformacionGeneral();
        panelPestanas.addTab(" Información General", panelInformacionGeneral);

        // Panel de seguimientos
        panelSeguimientos = crearPanelSeguimientos();
        panelPestanas.addTab(" Historial de Seguimientos", panelSeguimientos);

        panelContenido.add(panelPestanas, BorderLayout.CENTER);

        // Panel de botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Solo mostrar el botón "Nuevo Seguimiento" si el usuario es INVESTIGADOR
        if ("Investigador".equals(usuarioActual.getRol())) {
            JButton btnNuevoSeguimiento =
                    crearBotonRedondeado(" Nuevo Seguimiento", new Color(235, 235, 235), e -> {
                        new RegistroSeguimientoView(casoActual, investigadorActual)
                                .setVisible(true);
                        dispose();
                    });
            btnNuevoSeguimiento.setPreferredSize(new Dimension(200, 45));
            btnNuevoSeguimiento.setMinimumSize(new Dimension(200, 45));
            btnNuevoSeguimiento.setMaximumSize(new Dimension(200, 45));
            btnNuevoSeguimiento.setFont(new Font("Arial", Font.BOLD, 14));

            panelBotones.add(btnNuevoSeguimiento);
        }

        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelInformacionGeneral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel con GridBagLayout para organizar la información
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0: Expediente
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Expediente:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(casoActual.getNroExpediente()), gbc);

        // Fila 1: Estatus
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Estatus:"), gbc);
        gbc.gridx = 1;
        JLabel lblEstatus = new JLabel(casoActual.getEstatus());
        if ("Cerrado".equals(casoActual.getEstatus())) {
            lblEstatus.setForeground(Color.RED);
            lblEstatus.setFont(new Font("Arial", Font.BOLD, 12));
        }
        infoPanel.add(lblEstatus, gbc);

        // Fila 2: Fecha de inicio
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(new JLabel("Fecha de inicio:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(
                new JLabel(
                        casoActual.getFechaInicio() != null ? casoActual.getFechaInicio() : "N/A"),
                gbc);

        // Fila 3: Duración
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(new JLabel("Duración (días):"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(String.valueOf(casoActual.getDuracionDias())), gbc);

        // Fila 4: Móvil afectado
        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(new JLabel("Móvil afectado:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(
                casoActual.getMovilAfectado() != null ? casoActual.getMovilAfectado() : "N/A"),
                gbc);

        // Fila 5: Objetivo/Agraviado
        gbc.gridx = 0;
        gbc.gridy = 5;
        infoPanel.add(new JLabel("Objetivo/Agraviado:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(
                casoActual.getObjetivoAgraviado() != null ? casoActual.getObjetivoAgraviado()
                        : "N/A"),
                gbc);

        // Fila 6: Incidencia
        gbc.gridx = 0;
        gbc.gridy = 6;
        infoPanel.add(new JLabel("Incidencia:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(
                new JLabel(casoActual.getIncidencia() != null ? casoActual.getIncidencia() : "N/A"),
                gbc);

        // Fila 7: Modus Operandi (con área de texto)
        gbc.gridx = 0;
        gbc.gridy = 7;
        infoPanel.add(new JLabel("Modus Operandi:"), gbc);
        gbc.gridx = 1;
        JTextArea txtModusOperandi = new JTextArea(3, 30);
        txtModusOperandi.setText(casoActual.getDescripcionModusOperandi() != null
                ? casoActual.getDescripcionModusOperandi()
                : "N/A");
        txtModusOperandi.setEditable(false);
        txtModusOperandi.setLineWrap(true);
        txtModusOperandi.setWrapStyleWord(true);
        txtModusOperandi.setBackground(new Color(245, 245, 245));
        JScrollPane scrollModus = new JScrollPane(txtModusOperandi);
        infoPanel.add(scrollModus, gbc);

        // Fila 8: Observaciones
        gbc.gridx = 0;
        gbc.gridy = 8;
        infoPanel.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1;
        JTextArea txtObservaciones = new JTextArea(2, 30);
        txtObservaciones.setText(
                casoActual.getObservaciones() != null ? casoActual.getObservaciones() : "N/A");
        txtObservaciones.setEditable(false);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        txtObservaciones.setBackground(new Color(245, 245, 245));
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        infoPanel.add(scrollObs, gbc);

        // Agregar al panel principal con scroll
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelSeguimientos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Configurar tabla de seguimientos
        String[] columnas =
                {"Fecha", "Actividades", "Personas", "Monto", "Estatus", "Observaciones"};
        modeloTablaSeguimientos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaSeguimientos = new JTable(modeloTablaSeguimientos);
        tablaSeguimientos.setRowHeight(35);
        tablaSeguimientos.getTableHeader().setReorderingAllowed(false);
        tablaSeguimientos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollTabla = new JScrollPane(tablaSeguimientos);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panel.add(scrollTabla, BorderLayout.CENTER);

        return panel;
    }

    private void cargarSeguimientos() {
        // Limpiar tabla
        modeloTablaSeguimientos.setRowCount(0);

        // Obtener seguimientos del caso
        List<Seguimiento> seguimientos =
                seguimientoDAO.obtenerSeguimientosPorCaso(casoActual.getId());

        if (seguimientos.isEmpty()) {
            modeloTablaSeguimientos
                    .addRow(new Object[] {"No hay seguimientos registrados", "", "", "", "", ""});
        } else {
            for (Seguimiento s : seguimientos) {
                modeloTablaSeguimientos
                        .addRow(new Object[] {s.getFechaRegistro().toString().substring(0, 10),
                                s.getActividadesRealizadas(),
                                s.getPersonasInvolucradas() != null ? s.getPersonasInvolucradas()
                                        : "",
                                String.format("$%,.2f", s.getMontoExpuesto()), s.getEstatus(),
                                s.getObservaciones() != null ? s.getObservaciones() : ""});
            }
        }
    }
}
