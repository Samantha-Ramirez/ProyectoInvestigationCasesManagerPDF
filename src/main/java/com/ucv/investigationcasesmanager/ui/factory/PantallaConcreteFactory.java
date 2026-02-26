package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * PDyF: Este código implementa el patrón Abstract Factory para crear componentes visuales con un
 * estilo consistente basado en los wireframes.
 */

// Fabrica concreta
public class PantallaConcreteFactory extends PantallaAbstractFactory {
    private static final Color PRIMARY = new Color(125, 21, 175);
    private static final Color LIGHT_BG = new Color(248, 248, 251);
    private static final Color INPUT_BG = new Color(237, 237, 237);
    private static final Color BORDER = new Color(217, 217, 217);

    @Override
    public JButton crearBotonEncabezado(String texto, ActionListener accion) {
        JButton boton = crearBotonRedondeado(texto, new Color(236, 231, 240), PRIMARY, 100, 30);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.addActionListener(accion);
        return boton;
    }

    @Override
    public JButton crearBotonPrimario(String texto, ActionListener accion) {
        JButton boton = crearBotonRedondeado(texto, new Color(235, 235, 235), PRIMARY, 170, 34);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.addActionListener(accion);
        return boton;
    }

    @Override
    public JButton crearBotonMenu(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFont(new Font("Arial", Font.PLAIN, 13));
        boton.setMaximumSize(new Dimension(170, 36));
        boton.setForeground(new Color(67, 67, 67));
        boton.addActionListener(accion);
        return boton;
    }

    @Override
    public void estilizarTexto(JComponent componente) {
        componente.setBackground(INPUT_BG);
        componente.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        componente.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    @Override
    public void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setForeground(new Color(56, 56, 56));
        tabla.setSelectionBackground(new Color(242, 236, 247));
        tabla.setGridColor(new Color(238, 238, 238));
        tabla.setShowVerticalLines(false);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(LIGHT_BG);
        header.setForeground(new Color(80, 80, 80));
        header.setBorder(BorderFactory.createLineBorder(BORDER));
    }

    @Override
    public JLabel crearEstatusIcono(String estatus) {
        JLabel badge = new JLabel(estatus, SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(new Color(0, 166, 204));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        return badge;
    }

    @Override
    public Color obtenerColorPrimario() {
        return PRIMARY;
    }

    private JButton crearBotonRedondeado(String texto, Color bg, Color fg, int width, int height) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        boton.setBackground(bg);
        boton.setForeground(fg);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(width, height));
        return boton;
    }
}
