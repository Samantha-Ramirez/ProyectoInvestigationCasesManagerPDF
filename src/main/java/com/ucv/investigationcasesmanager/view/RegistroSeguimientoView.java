package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.dao.SeguimientoDAO;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.model.Caso;
import com.ucv.investigationcasesmanager.model.Seguimiento;
import com.ucv.investigationcasesmanager.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/*
 * Vista de registro de seguimiento, con un formulario para agregar nuevas entradas al historial de
 * un caso.
 */
public class RegistroSeguimientoView extends BaseView {
    private final Caso casoActual;
    private final Usuario investigadorActual;
    private final SeguimientoDAO seguimientoDAO;

    private JTextArea txtActividadesRealizadas;
    private JTextArea txtPersonasInvolucradas;
    private JTextField txtMontoExpuesto;
    private JComboBox<String> cbEstatus;
    private JTextArea txtObservaciones;
    private JTextArea txtRecomendaciones;
    private JTextArea txtConclusiones;

    public RegistroSeguimientoView(Caso caso, Usuario investigador) {
        super("Cargando...", true, false);

        this.casoActual = caso;
        this.investigadorActual = investigador;
        this.seguimientoDAO = new SeguimientoDAO();

        if (casoActual == null) {
            JOptionPane.showMessageDialog(this, "Error: Caso no válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        if ("Cerrado".equals(casoActual.getEstatus())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede registrar seguimiento en un caso cerrado.", "Caso Cerrado",
                    JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        setTitle("Registrar Seguimiento - Expediente: " + caso.getNroExpediente());
        inicializarComponentesEspecificos();
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Registrar seguimiento", null, null);

        panelContenido.add(crearPanelInformacionCaso(), BorderLayout.NORTH);
        panelContenido.add(crearPanelFormularioSeguimiento(), BorderLayout.CENTER);
        panelContenido.add(
                crearPanelAccionesInferior(
                        crearBotonPrimario("Registrar seguimiento", e -> accionRegistrar())),
                BorderLayout.SOUTH);
    }

    private JPanel crearPanelInformacionCaso() {
        JPanel panelInfo = crearTarjeta();
        panelInfo.setLayout(new GridLayout(2, 4, 10, 6));

        panelInfo.add(new JLabel("Expediente:"));
        panelInfo.add(new JLabel(casoActual.getNroExpediente()));
        panelInfo.add(new JLabel("Estatus actual:"));

        JLabel lblEstatus = new JLabel(casoActual.getEstatus());
        if ("Cerrado".equals(casoActual.getEstatus())) {
            lblEstatus.setForeground(Color.RED);
            lblEstatus.setFont(new Font("Arial", Font.BOLD, 12));
        }
        panelInfo.add(lblEstatus);

        panelInfo.add(new JLabel("Investigador:"));
        panelInfo.add(new JLabel(
                investigadorActual.getNombre() + " " + investigadorActual.getApellido()));
        panelInfo.add(new JLabel("Fecha:"));
        panelInfo.add(new JLabel(LocalDateTime.now().toString().substring(0, 10)));

        return panelInfo;
    }

    private JComponent crearPanelFormularioSeguimiento() {
        JPanel card = crearTarjeta();
        JPanel form = crearFormularioEtiquetado();

        txtActividadesRealizadas = crearAreaTextoEstilizada(4, 30, 80);
        txtPersonasInvolucradas = crearAreaTextoEstilizada(3, 30, 60);
        txtMontoExpuesto = new JTextField("0.00", 20);
        cbEstatus = new JComboBox<>(new String[] {"En Seguimiento", "Cerrado", "Reabierto"});
        txtObservaciones = crearAreaTextoEstilizada(2, 30, 50);
        txtRecomendaciones = crearAreaTextoEstilizada(3, 30, 60);
        txtConclusiones = crearAreaTextoEstilizada(3, 30, 60);

        estilizarEntrada(txtMontoExpuesto);
        estilizarEntrada(cbEstatus);

        int fila = 0;
        fila = agregarCampoEtiquetado(form, fila, "Actividades realizadas",
                envolverEnScroll(txtActividadesRealizadas));
        fila = agregarCampoEtiquetado(form, fila, "Personas involucradas",
                envolverEnScroll(txtPersonasInvolucradas));
        fila = agregarCampoEtiquetado(form, fila, "Monto expuesto ($)", txtMontoExpuesto);
        fila = agregarCampoEtiquetado(form, fila, "Cambiar estatus a", cbEstatus);
        fila = agregarCampoEtiquetado(form, fila, "Observaciones",
                envolverEnScroll(txtObservaciones));
        fila = agregarCampoEtiquetado(form, fila, "Recomendaciones",
                envolverEnScroll(txtRecomendaciones));
        agregarCampoEtiquetado(form, fila, "Conclusiones", envolverEnScroll(txtConclusiones));

        card.add(envolverEnScroll(form), BorderLayout.CENTER);
        return card;
    }

    private void accionRegistrar() {
        try {
            String actividades = txtActividadesRealizadas.getText().trim();
            if (actividades.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe describir las actividades realizadas.",
                        "Campo requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if ("Cerrado".equals(casoActual.getEstatus())) {
                JOptionPane.showMessageDialog(this,
                        "No se puede registrar seguimiento porque el caso ya está cerrado.",
                        "Caso Cerrado", JOptionPane.WARNING_MESSAGE);
                dispose();
                return;
            }

            double monto = 0;
            String montoStr = txtMontoExpuesto.getText().trim();
            if (!montoStr.isEmpty() && !montoStr.equals("0.00")) {
                try {
                    monto = Double.parseDouble(montoStr.replace(",", "."));
                    if (monto < 0) {
                        JOptionPane.showMessageDialog(this, "El monto no puede ser negativo.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser un número válido.",
                            "Error de formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String nuevoEstatus = (String) cbEstatus.getSelectedItem();
            if ("Cerrado".equals(nuevoEstatus) && camposCierreInvalidos()) {
                return;
            }

            int idCaso = obtenerIdCaso(casoActual.getNroExpediente());
            if (idCaso <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Error: No se pudo identificar el caso en la base de datos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setIdCaso(idCaso);
            seguimiento.setIdInvestigador(investigadorActual.getId());
            seguimiento.setFechaRegistro(LocalDateTime.now());
            seguimiento.setActividadesRealizadas(actividades);
            seguimiento.setPersonasInvolucradas(txtPersonasInvolucradas.getText().trim());
            seguimiento.setMontoExpuesto(monto);
            seguimiento.setEstatus(nuevoEstatus);
            seguimiento.setObservaciones(txtObservaciones.getText().trim());
            seguimiento.setRecomendaciones(txtRecomendaciones.getText().trim());
            seguimiento.setConclusiones(txtConclusiones.getText().trim());

            boolean guardado = seguimientoDAO.guardarSeguimiento(seguimiento);
            if (!guardado) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar el seguimiento. Verifique la conexión con la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            seguimientoDAO.actualizarEstatusCaso(idCaso, nuevoEstatus);
            JOptionPane.showMessageDialog(this,
                    "Seguimiento registrado exitosamente.\nEstatus actualizado a: " + nuevoEstatus,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            configurarVista(this, InicioClient.inicioSegunRol(usuarioActual.getRol()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean camposCierreInvalidos() {
        if (txtObservaciones.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe describir las observaciones.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        if (txtRecomendaciones.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe describir las recomendaciones.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        if (txtConclusiones.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe describir las conclusiones.",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private int obtenerIdCaso(String nroExpediente) {
        try {
            Caso caso = new CasoDAO().consultarCasoPorNroExpediente(nroExpediente);
            return caso != null ? caso.getId() : -1;
        } catch (Exception e) {
            return -1;
        }
    }
}
