package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CasoController;
import com.ucv.investigationcasesmanager.model.Caso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/*
 * Vista de bandeja.
 */
public class BandejaView extends BaseView {

    private static final int COLUMNA_ACCION = 3;
    private final CasoController casoController;

    public BandejaView() {
        super("Bandeja de casos", true);
        this.casoController = new CasoController();
        cargarDatos(this.usuarioActual.getId());
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Bandeja de casos", "Registrar",
                e -> configurarVista(this, new RegistroCasoView()));

        JPanel tarjeta = crearTarjeta();
        tarjeta.add(crearBarraAcciones("Orden: más reciente → más antiguo", null),
                BorderLayout.NORTH);
        tarjeta.add(crearTabla(new String[] {"Caso", "Tiempo", "Status", "Acción"}),
                BorderLayout.CENTER);

        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.rowAtPoint(e.getPoint());
                int columna = tabla.columnAtPoint(e.getPoint());
                if (fila >= 0 && columna == COLUMNA_ACCION) {
                    verDetallesCaso(fila);
                }
            }
        });

        panelContenido.add(tarjeta, BorderLayout.CENTER);
    }

    private void verDetallesCaso(int fila) {
        String expediente = (String) modeloTabla.getValueAt(fila, 0);
        Caso caso = casoController.obtenerCasoPorNroExpediente(expediente);
        if (caso == null) {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el caso.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        new DetalleCasoView(caso, usuarioActual).setVisible(true);
        dispose();
    }

    private void cargarDatos(int idUsuario) {
        List<Caso> casos = casoController.obtenerCasosInvestigador(idUsuario);
        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "Ver"});
        }
    }
}
