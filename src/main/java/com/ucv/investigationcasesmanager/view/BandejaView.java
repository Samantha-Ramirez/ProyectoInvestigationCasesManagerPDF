package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;

// import com.ucv.investigationcasesmanager.model.Sesion;
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
        // Crear botón redondeado para "Registrar"
        JButton btnRegistrarRedondeado = crearBotonRedondeado("Registrar", new Color(235, 235, 235),
                e -> configurarVista(this, new RegistroCasoView()));
                btnRegistrarRedondeado.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Usar el botón redondeado en el título superior
        configurarTituloSuperiorConBotonRedondeado("Bandeja de casos", btnRegistrarRedondeado);

        String[] columnas = {"Caso", "Tiempo", "Status", "Acción"};
        configurarTabla(columnas);
        // Agregar panel de botones de acción
        agregarPanelBotones();
    }

    // Agregar panel con botones de acción redondeados
    private void agregarPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setOpaque(false);

        // Botón Ver Detalles redondeado
        JButton btnVerDetalles = crearBotonRedondeado("Ver Detalles", new Color(235, 235, 235), 
                e -> verDetallesCaso());
            btnVerDetalles.setFont(new Font("Arial", Font.BOLD, 14));

        panelBotones.add(btnVerDetalles);

        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    // Método para ver detalles del caso seleccionado
    private void verDetallesCaso() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String expediente = (String) modeloTabla.getValueAt(fila, 0);

            // Buscar el caso completo
            Caso caso = casoDAO.buscarPorExpediente(expediente);
            if (caso != null) {
                // Abrir la vista de detalles
                new DetalleCasoView(caso, usuarioActual).setVisible(true);
                dispose(); // Cerrar la bandeja actual (opcional)
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar el caso.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un caso de la lista.",
                    "Ningún caso seleccionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Cargar los casos asignados al investigador y mostrar en la tabla
    private void cargarDatos(int idUsuario) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.consultarCasosInvestigador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}