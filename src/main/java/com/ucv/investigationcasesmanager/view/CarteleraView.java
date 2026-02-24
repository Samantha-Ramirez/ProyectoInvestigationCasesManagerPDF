package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Vista de la cartelera de casos para administradores. Muestra los casos asignados y el tiempo sin
 * atención.
 */
public class CarteleraView extends BaseView {

    private void agregarPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setOpaque(false);

        // Botón Ver Detalles con fondo blanco
        JButton btnVerDetalles =
                crearBotonRedondeado("Ver Detalles", Color.WHITE, e -> verDetallesCaso());
        btnVerDetalles.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerDetalles.setPreferredSize(new Dimension(120, 35));
        btnVerDetalles.setForeground(Color.BLACK); // Texto negro para contraste

        panelBotones.add(btnVerDetalles);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    private void verDetallesCaso() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String expediente = (String) modeloTabla.getValueAt(fila, 0);
            CasoDAO dao = new CasoDAO();
            Caso caso = dao.buscarPorExpediente(expediente);
            if (caso != null) {
                new DetalleCasoView(caso, usuarioActual).setVisible(true);
                dispose();
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        cargarDatos(usuarioActual.getId());
    }

    // Configurar la vista de cartelera de casos
    public CarteleraView() {
        super("Cartelera de casos", true);
        cargarDatos(this.usuarioActual.getId());
    }

    // Configurar componentes específicos de esta vista
    @Override
    protected void inicializarComponentesEspecificos() {
        // Crear botón redondeado para "Registrar"
        JButton btnRegistrarRedondeado = crearBotonRedondeado("Registrar", new Color(235, 235, 235),
                e -> configurarVista(this, new RegistroCasoView()));
        btnRegistrarRedondeado.setPreferredSize(new Dimension(120, 35));
        btnRegistrarRedondeado.setFont(new Font("Arial", Font.BOLD, 14));

        // Usar el botón redondeado en el título superior
        configurarTituloSuperiorConBotonRedondeado("Cartelera de casos", btnRegistrarRedondeado);

        String[] columnas = {"Caso", "Tiempo", "Status", "Acción"};
        configurarTabla(columnas);

        // Agregar panel de botones de acción
        agregarPanelBotones();
    }

    // Cargar todos los casos y mostrar en la tabla
    private void cargarDatos(int idUsuario) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.consultarCasosAdministrador(idUsuario);

        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
