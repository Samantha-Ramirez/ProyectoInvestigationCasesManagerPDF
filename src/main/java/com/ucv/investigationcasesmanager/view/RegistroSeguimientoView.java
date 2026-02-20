package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.model.*;
import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.dao.SeguimientoDAO;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class RegistroSeguimientoView extends BaseView {

    private Caso casoActual;
    private Usuario investigadorActual;
    private SeguimientoDAO seguimientoDAO;

    // Componentes Swing
    private JTextArea txtActividadesRealizadas;
    private JTextArea txtPersonasInvolucradas;
    private JTextField txtMontoExpuesto;
    private JComboBox<String> cbEstatus;
    private JTextArea txtObservaciones;

    public RegistroSeguimientoView(Caso caso, Usuario investigador) {
        // Usar el nuevo constructor con 'false' para que NO inicialice automáticamente
        super("Cargando...", true, false);

        // Asignar las variables INMEDIATAMENTE
        this.casoActual = caso;
        this.investigadorActual = investigador;
        this.seguimientoDAO = new SeguimientoDAO();

        // Validar que el caso no sea null
        if (casoActual == null) {
            JOptionPane.showMessageDialog(this,
                    "Error: Caso no válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Verificar si el caso está cerrado
        if ("Cerrado".equals(casoActual.getEstatus())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede registrar seguimiento en un caso cerrado.",
                    "Caso Cerrado",
                    JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        // Actualizar el título con el expediente real
        setTitle("Registrar Seguimiento - Expediente: " + caso.getNroExpediente());

        // Inicializar componentes manualmente (solo UNA vez)
        inicializarComponentesEspecificos();

        // Verificar BD (opcional - puedes comentar si no quieres ver los logs)
        verificarTablaSeguimiento();
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        // Validar que casoActual no sea null (por si acaso)
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
        try {
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
            if (!montoStr.isEmpty() && !montoStr.equals("0.00")) {
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

            // Obtener el ID real del caso
            int idCaso = obtenerIdCaso(casoActual.getNroExpediente());
            if (idCaso <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Error: No se pudo identificar el caso en la base de datos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear objeto Seguimiento
            Seguimiento seguimiento = new Seguimiento();
            seguimiento.setIdCaso(idCaso);
            seguimiento.setIdInvestigador(investigadorActual.getId());
            seguimiento.setFechaRegistro(LocalDateTime.now());
            seguimiento.setActividadesRealizadas(actividades);
            seguimiento.setPersonasInvolucradas(txtPersonasInvolucradas.getText().trim());
            seguimiento.setMontoExpuesto(monto);
            seguimiento.setEstatus((String) cbEstatus.getSelectedItem());
            seguimiento.setObservaciones(txtObservaciones.getText().trim());

            System.out.println("Guardando seguimiento para caso ID: " + idCaso);
            System.out.println("Actividades: " + actividades);

            // Guardar seguimiento
            boolean guardado = seguimientoDAO.guardarSeguimiento(seguimiento);

            if (guardado) {
                // Actualizar estatus del caso
                boolean estatusActualizado = seguimientoDAO.actualizarEstatusCaso(
                        idCaso,
                        (String) cbEstatus.getSelectedItem());

                if (estatusActualizado) {
                    System.out.println("Estatus actualizado correctamente");
                } else {
                    System.out.println("Error al actualizar estatus");
                }

                JOptionPane.showMessageDialog(this,
                        "Seguimiento registrado exitosamente.\nEstatus actualizado a: " +
                                cbEstatus.getSelectedItem(),
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                // Volver a la vista anterior
                configurarVista(this, InicioClient.inicioSegunRol(usuarioActual.getRol()));

            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar el seguimiento. Verifique la conexión con la base de datos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error en accionRegistrar: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int obtenerIdCaso(String nroExpediente) {
        try {
            CasoDAO casoDAO = new CasoDAO();
            Caso caso = casoDAO.buscarPorExpediente(nroExpediente);

            if (caso != null) {
                int id = caso.getId();
                System.out.println("✅ ID del caso encontrado: " + id + " para expediente: " + nroExpediente);
                return id;
            } else {
                System.err.println("❌ No se encontró el caso con expediente: " + nroExpediente);
                return -1;
            }
        } catch (Exception e) {
            System.err.println("❌ Error al obtener ID del caso: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    private void verificarTablaSeguimiento() {
        try {
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='seguimiento'";
            java.sql.Connection conn = com.ucv.investigationcasesmanager.dao.ConexionBD.getInstancia();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println("✅ Tabla 'seguimiento' existe en la BD");

                // Verificar columnas
                java.sql.ResultSet columns = stmt.executeQuery("PRAGMA table_info(seguimiento)");
                System.out.println("Columnas de la tabla seguimiento:");
                while (columns.next()) {
                    System.out.println("  - " + columns.getString("name") + " (" + columns.getString("type") + ")");
                }
            } else {
                System.out.println("❌ Tabla 'seguimiento' NO existe en la BD");
            }
        } catch (Exception e) {
            System.err.println("Error verificando tabla: " + e.getMessage());
        }
    }
}