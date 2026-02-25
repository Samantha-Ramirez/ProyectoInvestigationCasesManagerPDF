package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Vista de la bandeja de casos para investigadores.
 */
public class BandejaView extends BaseView {

    private final CasoDAO casoDAO;

    public BandejaView() {
        super("Bandeja de casos", true);
        this.casoDAO = new CasoDAO();
        cargarDatos(this.usuarioActual.getId());
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Bandeja de casos", "Registrar",
                e -> configurarVista(this, new RegistroCasoView()));

        JButton btnVerDetalles = crearBotonPrimario("Ver detalles", e -> verDetallesCaso());
        JPanel tarjeta = crearTarjetaWireframe();
        tarjeta.add(crearBarraAcciones("Orden: más reciente → más antiguo", btnVerDetalles),
                BorderLayout.NORTH);
        tarjeta.add(crearTabla(new String[] {"Caso", "Tiempo", "Status", "Acción"}),
                BorderLayout.CENTER);

        panelContenido.add(tarjeta, BorderLayout.CENTER);
    }

    private void verDetallesCaso() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un caso de la lista.",
                    "Ningún caso seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String expediente = (String) modeloTabla.getValueAt(fila, 0);
        Caso caso = casoDAO.consultarCasoPorNroExpediente(expediente);
        if (caso == null) {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el caso.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        new DetalleCasoView(caso, usuarioActual).setVisible(true);
        dispose();
    }

    private void cargarDatos(int idUsuario) {
        List<Caso> casos = casoDAO.consultarCasosInvestigador(idUsuario);
        for (Caso c : casos) {
            modeloTabla.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
