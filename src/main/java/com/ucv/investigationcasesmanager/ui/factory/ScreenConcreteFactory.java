package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * PDyF: Fábrica concreta que implementa el estilo visual definido en los wireframes del sistema.
 */
public class ScreenConcreteFactory extends ScreenAbstractFactory {
    private static final Color PRIMARY = new Color(125, 21, 175);
    private static final Color LIGHT_BG = new Color(248, 248, 251);
    private static final Color INPUT_BG = new Color(237, 237, 237);
    private static final Color BORDER = new Color(217, 217, 217);

    @Override
    public JButton createHeaderButton(String text, ActionListener action) {
        JButton btn = createRoundedButton(text, new Color(236, 231, 240), PRIMARY, 100, 30);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.addActionListener(action);
        return btn;
    }

    @Override
    public JButton createPrimaryButton(String text, ActionListener action) {
        JButton btn = createRoundedButton(text, new Color(235, 235, 235), PRIMARY, 100, 30);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.addActionListener(action);
        return btn;
    }

    @Override
    public JButton createMenuButton(String text, ActionListener action) {
        return buildMenuButton(null, text, action);
    }

    // Por qué: sobrecarga con ícono para que el menú lateral muestre íconos vectoriales
    // alineados a la izquierda del texto, tal como muestran los wireframes del sistema.
    @Override
    public JButton createMenuButton(Icon icon, String text, ActionListener action) {
        return buildMenuButton(icon, text, action);
    }

    private JButton buildMenuButton(Icon icon, String text, ActionListener action) {
        JButton btn = new JButton(text, icon);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT);
        btn.setIconTextGap(8);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setMaximumSize(new Dimension(170, 36));
        btn.setForeground(new Color(67, 67, 67));
        btn.addActionListener(action);
        return btn;
    }

    @Override
    public void styleInput(JComponent component) {
        component.setBackground(INPUT_BG);
        component.setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        component.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    @Override
    public void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(new Color(56, 56, 56));
        table.setSelectionBackground(new Color(242, 236, 247));
        table.setGridColor(new Color(238, 238, 238));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(LIGHT_BG);
        header.setForeground(new Color(80, 80, 80));
        header.setBorder(BorderFactory.createLineBorder(BORDER));
    }

    @Override
    public JLabel createStatusBadge(String status) {
        JLabel badge = new JLabel(status, SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(new Color(0, 166, 204));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        return badge;
    }

    @Override
    public Color getPrimaryColor() {
        return PRIMARY;
    }

    private JButton createRoundedButton(String text, Color bg, Color fg, int width, int height) {
        JButton btn = new JButton(text) {
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
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        return btn;
    }
}
