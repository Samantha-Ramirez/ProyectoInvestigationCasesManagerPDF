package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/*
 * Vista de la cartelera de casos para administradores.
 */
public class CarteleraView extends BaseView {

    private static final int COLUMNA_ACCION = 3;
    private final CasoDAO casoDAO;

    public CarteleraView() {
        super("Cartelera de casos", true);
        this.casoDAO = new CasoDAO();
        cargarDatos(this.usuarioActual.getId());
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Cartelera de casos", "Registrar",
                e -> configurarVista(this, new RegistroCasoView()));

        JPanel tarjeta = crearTarjetaWireframe();
        tarjeta.add(crearBarraAcciones("Vista general de casos por investigador", null),
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
        Caso caso = casoDAO.consultarCasoPorNroExpediente(expediente);
        if (caso != null) {
            new DetalleCasoView(caso, usuarioActual).setVisible(true);
            dispose();
        }
    }

    private void cargarDatos(int idUsuario) {
        List<Caso> casos = casoDAO.consultarCasosAdministrador(idUsuario);
        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "Ver"});
        }
    }
}
