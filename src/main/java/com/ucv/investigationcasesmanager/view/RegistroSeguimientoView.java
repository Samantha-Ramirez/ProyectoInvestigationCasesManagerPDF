package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.model.*;
import com.ucv.investigationcasesmanager.dao.SeguimientoDAO;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class RegistroSeguimientoView extends BaseView {

    private Caso casoActual;
    private Usuario investigadorActual;
    private SeguimientoDAO seguimientoDAO;
    private boolean inicializado = false;

    // Componentes Swing
    private JTextArea txtActividadesRealizadas;
    private JTextArea txtPersonasInvolucradas;
    private JTextField txtMontoExpuesto;
    private JComboBox<String> cbEstatus;
    private JTextArea txtObservaciones;

    public RegistroSeguimientoView(Caso caso, Usuario investigador) {
        super("Registrar Seguimiento - Expediente: " + caso.getNroExpediente(), true);
        this.casoActual = caso;
        this.investigadorActual = investigador;
        this.seguimientoDAO = new SeguimientoDAO();

        // VALIDACIÓN: Verificar si el caso está cerrado
        if (casoActual != null && "Cerrado".equals(casoActual.getEstatus())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede registrar seguimiento en un caso cerrado.",
                    "Caso Cerrado",
                    JOptionPane.WARNING_MESSAGE);
            dispose(); // Cerrar la ventana inmediatamente
            return;
        }

        // Forzar inicialización manual después de asignar variables
        inicializarComponentesEspecificos();
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        // Evitar doble inicialización
        if (inicializado)
            return;
        inicializado = true;

        // Validar que casoActual no sea null
        if (casoActual == null) {
            System.err.println("ERROR: casoActual es null en inicialización");
            JOptionPane.showMessageDialog(this,
                    "Error al cargar el caso.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Configurar título superior
        configurarTituloSuperior("Registrar Seguimiento", null, null);

        // Panel de información del caso
        JPanel panelInfo = crearPanelInformacionCaso();
        panelContenido.add(panelInfo, BorderLayout.NORTH);

        // Configurar formulario
        configurarFormulario();

        // Crear campos del formulario
        crearCamposFormulario();

        // Agregar botón de acción principal
        agregarBotonAccionPrincipal("Registrar Seguimiento", e -> accionRegistrar());
    }

    private JPanel crearPanelInformacionCaso() {
        JPanel panelInfo = new JPanel(new GridLayout(2, 4, 10, 5));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Caso"));
        panelInfo.setBackground(Color.WHITE);

        panelInfo.add(new JLabel("Expediente:"));
        panelInfo.add(new JLabel(casoActual.getNroExpediente()));
        panelInfo.add(new JLabel("Estatus actual:"));

        // Resaltar si está cerrado
        String estatusActual = casoActual.getEstatus();
        JLabel lblEstatus = new JLabel(estatusActual);
        if ("Cerrado".equals(estatusActual)) {
            lblEstatus.setForeground(Color.RED);
            lblEstatus.setFont(new Font("Arial", Font.BOLD, 12));
        }
        panelInfo.add(lblEstatus);

        panelInfo.add(new JLabel("Investigador:"));
        panelInfo.add(new JLabel(investigadorActual.getNombre() + " " + investigadorActual.getApellido()));
        panelInfo.add(new JLabel("Fecha:"));
        panelInfo.add(new JLabel(LocalDateTime.now().toString().substring(0, 10)));

        return panelInfo;
    }

    private void crearCamposFormulario() {
        // Actividades Realizadas
        JLabel lblActividades = new JLabel("Actividades Realizadas:");
        txtActividadesRealizadas = new JTextArea(4, 30);
        txtActividadesRealizadas.setLineWrap(true);
        txtActividadesRealizadas.setWrapStyleWord(true);
        JScrollPane scrollActividades = new JScrollPane(txtActividadesRealizadas);
        scrollActividades.setPreferredSize(new Dimension(400, 80));

        agregarCampoFormulario(lblActividades);
        agregarCampoFormulario(scrollActividades);

        // Personas Involucradas
        JLabel lblPersonas = new JLabel("Personas Involucradas:");
        txtPersonasInvolucradas = new JTextArea(3, 30);
        txtPersonasInvolucradas.setLineWrap(true);
        txtPersonasInvolucradas.setWrapStyleWord(true);
        JScrollPane scrollPersonas = new JScrollPane(txtPersonasInvolucradas);
        scrollPersonas.setPreferredSize(new Dimension(400, 60));

        agregarCampoFormulario(lblPersonas);
        agregarCampoFormulario(scrollPersonas);

        // Monto Expuesto
        JLabel lblMonto = new JLabel("Monto Expuesto ($):");
        txtMontoExpuesto = new JTextField(20);
        txtMontoExpuesto.setText("0.00");

        agregarCampoFormulario(lblMonto);
        agregarCampoFormulario(txtMontoExpuesto);

        // Estatus
        JLabel lblEstatus = new JLabel("Cambiar estatus a:");
        cbEstatus = new JComboBox<>(new String[] {
                "En Seguimiento",
                "Cerrado",
                "Reabierto"
        });

        agregarCampoFormulario(lblEstatus);
        agregarCampoFormulario(cbEstatus);

        // Observaciones
        JLabel lblObservaciones = new JLabel("Observaciones:");
        txtObservaciones = new JTextArea(2, 30);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setPreferredSize(new Dimension(400, 50));

        agregarCampoFormulario(lblObservaciones);
        agregarCampoFormulario(scrollObs);
    }

    private void accionRegistrar() {
        // Validar campos obligatorios
        String actividades = txtActividadesRealizadas.getText().trim();
        if (actividades.isEmpty() || actividades.equals("Actividades Realizadas:")) {
            JOptionPane.showMessageDialog(this,
                    "Debe describir las actividades realizadas.",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // VALIDACIÓN EXTRA: Verificar nuevamente si el caso se cerró mientras tanto
        if ("Cerrado".equals(casoActual.getEstatus())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede registrar seguimiento porque el caso ya está cerrado.",
                    "Caso Cerrado",
                    JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        // Validar monto
        double monto = 0;
        String montoStr = txtMontoExpuesto.getText().trim();
        if (!montoStr.isEmpty()) {
            try {
                monto = Double.parseDouble(montoStr.replace(",", "."));
                if (monto < 0) {
                    JOptionPane.showMessageDialog(this,
                            "El monto no puede ser negativo.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "El monto debe ser un número válido.",
                        "Error de formato",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Crear objeto Seguimiento
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setIdCaso(obtenerIdCaso(casoActual.getNroExpediente()));
        seguimiento.setIdInvestigador(investigadorActual.getId());
        seguimiento.setFechaRegistro(LocalDateTime.now());
        seguimiento.setActividadesRealizadas(actividades);
        seguimiento.setPersonasInvolucradas(txtPersonasInvolucradas.getText().trim());
        seguimiento.setMontoExpuesto(monto);
        seguimiento.setEstatus((String) cbEstatus.getSelectedItem());
        seguimiento.setObservaciones(txtObservaciones.getText().trim());

        // Guardar seguimiento
        if (seguimientoDAO.guardarSeguimiento(seguimiento)) {
            // Actualizar estatus del caso
            seguimientoDAO.actualizarEstatusCaso(
                    obtenerIdCaso(casoActual.getNroExpediente()),
                    (String) cbEstatus.getSelectedItem());

            JOptionPane.showMessageDialog(this,
                    "Seguimiento registrado exitosamente.\nEstatus actualizado a: " +
                            cbEstatus.getSelectedItem(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Volver a la vista anterior
            configurarVista(this, InicioClient.inicioSegunRol(usuarioActual.getRol()));

        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el seguimiento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerIdCaso(String nroExpediente) {
        // Pendiente: Implementar consulta real en CasoDAO
        // Por ahora retornamos 1 como ejemplo
        return 1;
    }
}