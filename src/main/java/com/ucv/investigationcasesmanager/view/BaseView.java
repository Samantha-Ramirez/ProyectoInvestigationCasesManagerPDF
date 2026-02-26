package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.model.Sesion;
import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.ui.factory.UIAbstractFactory;
import com.ucv.investigationcasesmanager.ui.factory.PantallaConcreteFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

/*
 * Vista base con estructura compartida y helpers para construir pantallas wireframe con FlatLaf.
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
    protected final UIAbstractFactory uiFactory;
    private int filaActual = 0;

    public BaseView(String titulo, Boolean mostrarMenu) {
        this(titulo, mostrarMenu, true);
    }

    public BaseView(String titulo, Boolean mostrarMenu, boolean inicializar) {
        this.usuarioActual = Sesion.getUsuario();
        this.uiFactory = new PantallaConcreteFactory();

        setTitle(titulo);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(242, 242, 242));

        configurarCabecera();
        if (mostrarMenu) {
            configurarMenuLateral();
        }
        configurarPanelCentral();

        setLocationRelativeTo(null);

        if (inicializar) {
            inicializarComponentesEspecificos();
        }
    }

    private void configurarCabecera() {
        cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(uiFactory.obtenerColorPrimario());
        cabecera.setPreferredSize(new Dimension(1100, 50));

        String infoUser = (usuarioActual != null)
                ? usuarioActual.getNombre() + " " + usuarioActual.getApellido()
                : "Usuario 1";

        JLabel lblUser = new JLabel(infoUser + "  ");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 13));
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));

        cabecera.add(lblUser, BorderLayout.EAST);
        add(cabecera, BorderLayout.NORTH);
    }

    private void configurarMenuLateral() {
        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(Color.WHITE);
        menuLateral.setPreferredSize(new Dimension(160, 0));
        menuLateral
                .setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));

        menuLateral.add(Box.createVerticalStrut(12));
        agregarBotonMenu("⌂  Inicio", e -> irAInicio());
        agregarBotonMenu("⚑  Bandeja", e -> irAInicio());
        agregarBotonMenu("↺  Reportes", e -> irAReportes());
        agregarBotonMenu("⚙  Entidades", e -> irAInicio());
        agregarBotonMenu("◌  Auditoría", e -> irAInicio());
        menuLateral.add(Box.createVerticalGlue());
        agregarBotonMenu("⇦  Cerrar sesión", e -> ejecutarCerrarSesion());
        menuLateral.add(Box.createVerticalStrut(14));

        add(menuLateral, BorderLayout.WEST);
    }

    private void agregarBotonMenu(String texto, ActionListener accion) {
        JButton btn = uiFactory.crearBotonMenu(texto, accion);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        menuLateral.add(btn);
        menuLateral.add(Box.createVerticalStrut(2));
    }

    private void irAInicio() {
        configurarVista(this, InicioClient.obtenerInicio(usuarioActual.getRol()));
    }

    private void irAReportes() {
        configurarVista(this, new ReportesView());
    }

    private void ejecutarCerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Sesion.cerrarSesion();
            configurarVista(this, new InicioSesionView());
        }
    }

    private void configurarPanelCentral() {
        panelContenido = new JPanel(new BorderLayout(12, 12));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        add(panelContenido, BorderLayout.CENTER);
    }

    protected void configurarTituloSuperior(String tituloSeccion, String textoBoton,
            ActionListener accion) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblTitulo = new JLabel(tituloSeccion);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblTitulo, BorderLayout.WEST);

        if (textoBoton != null && accion != null) {
            panelSuperior.add(crearBotonEncabezado(textoBoton, accion), BorderLayout.EAST);
        }

        panelContenido.add(panelSuperior, BorderLayout.NORTH);
    }

    protected JButton crearBotonEncabezado(String texto, ActionListener accion) {
        return uiFactory.crearBotonEncabezado(texto, accion);
    }

    protected JButton crearBotonPrimario(String texto, ActionListener accion) {
        return uiFactory.crearBotonPrimario(texto, accion);
    }

    protected JPanel crearTarjeta() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(232, 232, 232)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        return card;
    }

    protected JPanel crearBarraAcciones(String textoInfo, JButton botonDerecha) {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);

        if (textoInfo != null && !textoInfo.isBlank()) {
            JLabel lblInfo = new JLabel(textoInfo);
            lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
            lblInfo.setForeground(new Color(95, 95, 95));
            barra.add(lblInfo, BorderLayout.WEST);
        }

        if (botonDerecha != null) {
            barra.add(botonDerecha, BorderLayout.EAST);
        }

        return barra;
    }

    protected JScrollPane crearTabla(String[] columnas) {
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(34);
        tabla.getTableHeader().setReorderingAllowed(false);
        uiFactory.estilizarTabla(tabla);

        if (columnas.length > 2 && "Status".equalsIgnoreCase(columnas[2])) {
            tabla.getColumnModel().getColumn(2).setCellRenderer(new StatusBadgeRenderer());
        }

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(236, 236, 236)));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    protected void configurarTabla(String[] columnas) {
        panelContenido.add(crearTabla(columnas), BorderLayout.CENTER);
    }

    protected JPanel crearFormularioEtiquetado() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        return form;
    }

    protected int agregarCampoEtiquetado(JPanel form, int fila, String etiqueta, JComponent campo) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.insets = new Insets(6, 4, 6, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(etiqueta + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        form.add(campo, gbc);
        return fila + 1;
    }

    protected JTextArea crearAreaTextoEstilizada(int filas, int columnas, int altoPreferido) {
        JTextArea area = new JTextArea(filas, columnas);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        area.setBackground(new Color(237, 237, 237));
        area.setPreferredSize(new Dimension(420, altoPreferido));
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        return area;
    }

    protected JScrollPane envolverEnScroll(JComponent componente) {
        JScrollPane scroll = new JScrollPane(componente);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    protected void estilizarEntrada(JComponent component) {
        uiFactory.estilizarTexto(component);
    }

    protected JPanel crearPanelAccionesInferior(JButton... botones) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        panel.setOpaque(false);
        for (JButton boton : botones) {
            if (boton != null) {
                panel.add(boton);
            }
        }
        return panel;
    }

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

    protected void configurarVista(JFrame vistaActual, JFrame vistaNueva) {
        vistaActual.dispose();
        vistaNueva.setVisible(true);
    }

    protected void agregarCampoFormulario(JComponent componente) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = filaActual++;
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        if (componente instanceof JTextField || componente instanceof JTextArea
                || componente instanceof JComboBox<?>) {
            configurarEstiloYPlaceholder(componente);
        }

        panelFormulario.add(componente, gbc);
        panelFormulario.revalidate();
    }

    private void configurarEstiloYPlaceholder(JComponent componente) {
        uiFactory.estilizarTexto(componente);

        if (!(componente instanceof JTextField || componente instanceof JTextArea)) {
            return;
        }

        componente.setForeground(Color.GRAY);
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
                    if (componente instanceof JTextField) {
                        ((JTextField) componente).setText("");
                    } else {
                        ((JTextArea) componente).setText("");
                    }
                    componente.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String textoActual =
                        (componente instanceof JTextField) ? ((JTextField) componente).getText()
                                : ((JTextArea) componente).getText();

                if (textoActual.isEmpty()) {
                    if (componente instanceof JTextField) {
                        ((JTextField) componente).setText(placeholder);
                    } else {
                        ((JTextArea) componente).setText(placeholder);
                    }
                    componente.setForeground(Color.GRAY);
                }
            }
        });
    }

    protected void agregarBotonAccionPrincipal(String texto, ActionListener accion) {
        panelContenido.add(crearPanelAccionesInferior(crearBotonPrimario(texto, accion)),
                BorderLayout.SOUTH);
    }

    protected JButton crearBotonRedondeado(String texto, Color colorFondo, ActionListener accion) {
        JButton boton = uiFactory.crearBotonEncabezado(texto, accion);
        boton.setBackground(colorFondo);
        return boton;
    }

    protected abstract void inicializarComponentesEspecificos();

    private class StatusBadgeRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel badge = uiFactory.crearEstatusIcono(String.valueOf(value));
            if (isSelected) {
                badge.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(185, 170, 212)),
                        BorderFactory.createEmptyBorder(2, 8, 2, 8)));
            }
            return badge;
        }
    }
}
