package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.model.Sesion;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/*
 * Vista base para las diferentes secciones de la aplicación. Contiene la estructura común de
 * cabecera, menú lateral y panel central.
 */
public abstract class BaseView extends JFrame {
    protected Usuario usuarioActual;
    protected JPanel panelContenido;
    protected JPanel menuLateral;
    protected JPanel cabecera;
    protected DefaultTableModel modeloTabla;
    protected JTable tabla;
    protected JPanel panelFormulario;
    protected JScrollPane scrollFormulario;
    private int filaActual = 0;

    // Configurar la estructura base de la vista
    public BaseView(String titulo, Boolean mostrarMenu) {
        this.usuarioActual = Sesion.getUsuario();

        setTitle(titulo);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configurarCabecera();
        if (mostrarMenu) {
            configurarMenuLateral();
        }
        configurarPanelCentral();

        setLocationRelativeTo(null);
        inicializarComponentesEspecificos();
    }

    // Configurar la cabecera con el nombre del usuario logueado
    private void configurarCabecera() {
        cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(new Color(128, 0, 128));
        cabecera.setPreferredSize(new Dimension(1100, 80));

        String infoUser = (usuarioActual != null)
                ? usuarioActual.getNombre() + " " + usuarioActual.getApellido()
                : "Iniciar sesión";
        JLabel lblUser = new JLabel(infoUser + " 👤 ");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 14));
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        cabecera.add(lblUser, BorderLayout.EAST);
        add(cabecera, BorderLayout.NORTH);
    }

    // Configurar el menú lateral con opciones comunes
    private void configurarMenuLateral() {
        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(Color.WHITE);
        menuLateral.setPreferredSize(new Dimension(180, 0));
        menuLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        agregarBotonMenu("🏠 Inicio", e -> irAInicio());
        agregarBotonMenu("📊 Reportes", e -> irAInicio());
        agregarBotonMenu("🔍 Auditoría", e -> irAInicio());
        agregarBotonMenu("📂 Entidades", e -> irAInicio());
        agregarBotonMenu("🚪 Cerrar Sesión", e -> ejecutarCerrarSesion());
        menuLateral.add(Box.createVerticalStrut(20));

        add(menuLateral, BorderLayout.WEST);
    }

    // Agregar botones al menú lateral con estilo uniforme
    private void agregarBotonMenu(String texto, ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.setMaximumSize(new Dimension(200, 50));
        btn.addActionListener(accion);

        menuLateral.add(Box.createVerticalStrut(10));
        menuLateral.add(btn);
    }

    // Ir al inicio según el rol del usuario
    private void irAInicio() {
        configurarVista(this, InicioClient.inicioSegunRol(usuarioActual.getRol()));
    }

    // Ejecutar el cierre de sesión y volver a la pantalla de inicio de sesión
    private void ejecutarCerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            configurarVista(this, new InicioSesionView());
        }
    }

    // Configurar el panel central donde se mostrarán los contenidos específicos de cada sección
    private void configurarPanelCentral() {
        panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        add(panelContenido, BorderLayout.CENTER);
    }

    // Configurar el título superior de cada sección con un botón de acción opcional
    protected void configurarTituloSuperior(String tituloSeccion, String textoBoton,
            ActionListener accion) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblTitulo = new JLabel(tituloSeccion);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        if (textoBoton != null) {
            JButton btn = new JButton(textoBoton);
            btn.setPreferredSize(new Dimension(120, 35));
            btn.setBackground(new Color(235, 235, 235));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(accion);
            panelSuperior.add(btn, BorderLayout.EAST);
        }

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelContenido.add(panelSuperior, BorderLayout.NORTH);
    }

    // Configurar la tabla para mostrar datos en el panel central
    protected void configurarTabla(String[] columnas) {
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(45);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        scroll.getViewport().setBackground(Color.WHITE);

        panelContenido.add(scroll, BorderLayout.CENTER);
    }

    // Configurar el formulario para registrar o editar casos
    protected void configurarFormulario() {
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        filaActual = 0;

        scrollFormulario = new JScrollPane(panelFormulario);
        scrollFormulario.setBorder(null);
        scrollFormulario.getViewport().setBackground(Color.WHITE);

        scrollFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollFormulario.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollFormulario.getVerticalScrollBar().setUnitIncrement(16);

        panelContenido.add(scrollFormulario, BorderLayout.CENTER);
    }

    // Configurar la vista a mostrar
    protected void configurarVista(JFrame vistaActual, JFrame vistaNueva) {
        vistaActual.dispose();
        vistaNueva.setVisible(true);
    }

    // Agregar un campo al formulario con estilo uniforme
    protected void agregarCampoFormulario(JComponent componente) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = filaActual++;
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        if (componente instanceof JTextField || componente instanceof JTextArea) {
            configurarEstiloYPlaceholder(componente);
        }

        panelFormulario.add(componente, gbc);
        panelFormulario.revalidate();
    }

    // Configurar estilo y comportamiento de placeholder para campos de texto
    private void configurarEstiloYPlaceholder(JComponent componente) {
        componente.setBackground(new Color(235, 235, 235));
        componente.setForeground(Color.GRAY); // Color de placeholder inicial
        componente.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        String placeholder =
                (componente instanceof JTextField) ? ((JTextField) componente).getText()
                        : ((JTextArea) componente).getText();

        componente.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                String textoActual =
                        (componente instanceof JTextField) ? ((JTextField) componente).getText()
                                : ((JTextArea) componente).getText();

                if (textoActual.equals(placeholder)) {
                    if (componente instanceof JTextField)
                        ((JTextField) componente).setText("");
                    else
                        ((JTextArea) componente).setText("");
                    componente.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String textoActual =
                        (componente instanceof JTextField) ? ((JTextField) componente).getText()
                                : ((JTextArea) componente).getText();

                if (textoActual.isEmpty()) {
                    if (componente instanceof JTextField)
                        ((JTextField) componente).setText(placeholder);
                    else
                        ((JTextArea) componente).setText(placeholder);
                    componente.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Agregar un botón de acción principal al formulario
    protected void agregarBotonAccionPrincipal(String texto, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(220, 220, 220));
        btn.setPreferredSize(new Dimension(150, 40));
        btn.addActionListener(accion);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btn);
        panelContenido.add(panelBoton, BorderLayout.SOUTH);
    }

    // Configurar componentes específicos
    protected abstract void inicializarComponentesEspecificos();
}
