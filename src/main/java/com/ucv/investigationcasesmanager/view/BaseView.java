package com.ucv.investigationcasesmanager.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class BaseView extends JFrame {
    protected JPanel panelContenido;
    protected JPanel menuLateral;
    protected JPanel cabecera;
    protected DefaultTableModel modeloTabla;
    protected JTable tabla;

    public BaseView(String titulo) {
        setTitle(titulo);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configurarCabecera();
        configurarMenuLateral();
        configurarPanelCentral();

        setLocationRelativeTo(null);
    }

    private void configurarCabecera() {
        cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(new Color(128, 0, 128));
        cabecera.setPreferredSize(new Dimension(1100, 80));

        JLabel lblIcono = new JLabel("👤 ");
        lblIcono.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        cabecera.add(lblIcono, BorderLayout.EAST);

        add(cabecera, BorderLayout.NORTH);
    }

    private void configurarMenuLateral() {
        menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(Color.WHITE);
        menuLateral.setPreferredSize(new Dimension(180, 0));
        menuLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

        agregarBotonMenu("🏠 Inicio");
        agregarBotonMenu("📊 Reportes");
        agregarBotonMenu("🔍 Auditoría");
        agregarBotonMenu("📂 Entidades");

        add(menuLateral, BorderLayout.WEST);
    }

    private void agregarBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setMaximumSize(new Dimension(180, 40));
        menuLateral.add(Box.createVerticalStrut(10));
        menuLateral.add(btn);
    }

    private void configurarPanelCentral() {
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        add(panelContenido, BorderLayout.CENTER);
    }

    protected void configurarTituloSuperior(String tituloSeccion, String textoBoton) {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblTitulo = new JLabel(tituloSeccion);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton btnAccion = new JButton(textoBoton);
        btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAccion.setBackground(new Color(230, 230, 230));

        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        if (textoBoton != null)
            panelSuperior.add(btnAccion, BorderLayout.EAST);

        panelContenido.add(panelSuperior, BorderLayout.NORTH);
    }

    protected void configurarTabla(String[] columnas) {
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(45);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setShowVerticalLines(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panelContenido.add(scroll, BorderLayout.CENTER);
    }

    protected abstract void inicializarComponentesEspecificos();
}
