package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.model.Sesion;
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

    // Configurar la estructura base de la vista
    public BaseView(String titulo) {
        this.usuarioActual = Sesion.getUsuario();

        setTitle(titulo);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configurarCabecera();
        configurarMenuLateral();
        configurarPanelCentral();

        setLocationRelativeTo(null);
    }

    // Configurar la cabecera con el nombre del usuario logueado
    private void configurarCabecera() {
        cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(new Color(128, 0, 128));
        cabecera.setPreferredSize(new Dimension(1100, 80));

        String infoUser = (usuarioActual != null)
                ? usuarioActual.getNombre() + " " + usuarioActual.getApellido()
                : "Sin Sesión";
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
        this.dispose();
        InicioCreator.inicioSegunRol(usuarioActual);
    }

    // Ejecutar el cierre de sesión y volver a la pantalla de inicio de sesión
    private void ejecutarCerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            this.dispose();
            new InicioSesionView().setVisible(true);
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
    protected void configurarTituloSuperior(String tituloSeccion, String textoBoton) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblTitulo = new JLabel(tituloSeccion);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        if (textoBoton != null) {
            JButton btnAccion = new JButton(textoBoton);
            btnAccion.setPreferredSize(new Dimension(120, 35));
            btnAccion.setBackground(new Color(235, 235, 235));
            btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelSuperior.add(btnAccion, BorderLayout.EAST);
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

    // Configurar componentes específicos
    protected abstract void inicializarComponentesEspecificos();
}
