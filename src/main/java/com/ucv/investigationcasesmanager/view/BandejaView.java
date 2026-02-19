package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;

//import com.ucv.investigationcasesmanager.model.Sesion;
import javax.swing.*;
import java.awt.*;

import java.util.List;

/*
 * Vista de la bandeja de casos para investigadores. Muestra los casos asignados y el tiempo sin
 * atención.
 */
public class BandejaView extends BaseView {

    private CasoDAO casoDAO;

    // Configurar la vista de bandeja de casos para el investigador
    public BandejaView() {
        super("Bandeja de casos", true);
        this.casoDAO = new CasoDAO();
        cargarDatos(this.usuarioActual.getId());
    }

    // Configurar componentes específicos de esta vista
    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Bandeja de casos", "Registrar", e -> {
            configurarVista(this, new RegistroCasoView());
        });

        String[] columnas = { "Caso", "Tiempo", "Status", "Acción" };
        configurarTabla(columnas);
        // Agregar panel de botones de acción
        agregarPanelBotones();
    }

    // Agregar panel con botones de acción
    private void agregarPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setOpaque(false);

        JButton btnSeguimiento = new JButton("Registrar Seguimiento");
        btnSeguimiento.setBackground(new Color(235, 235, 235));
        btnSeguimiento.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSeguimiento.addActionListener(e -> registrarSeguimiento());

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(235, 235, 235));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> actualizarTabla());

        panelBotones.add(btnSeguimiento);
        panelBotones.add(btnActualizar);

        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    // Registrar seguimiento para el caso seleccionado
    private void registrarSeguimiento() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String expediente = (String) modeloTabla.getValueAt(fila, 0);
            String estatus = (String) modeloTabla.getValueAt(fila, 2);

            if ("Cerrado".equals(estatus)) {
                JOptionPane.showMessageDialog(this,
                        "No se puede registrar seguimiento en un caso cerrado.",
                        "Caso Cerrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Buscar el caso completo usando el DAO mejorado
            Caso caso = casoDAO.buscarPorExpediente(expediente);
            if (caso != null) {
                new RegistroSeguimientoView(caso, usuarioActual).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo encontrar el caso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un caso de la lista.",
                    "Ningún caso seleccionado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Actualizar la tabla con los datos más recientes
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        cargarDatos(usuarioActual.getId());
        JOptionPane.showMessageDialog(this,
                "Tabla actualizada correctamente.",
                "Actualización",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Cargar los casos asignados al investigador y mostrar en la tabla
    private void cargarDatos(int idUsuario) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.consultarCasosInvestigador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] { c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝" });
        }
    }
}
