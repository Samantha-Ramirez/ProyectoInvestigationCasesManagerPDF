package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/*
 * Implementación concreta del estilo de los wireframes.
 */
public class WireframeUIFactory implements UIComponentFactory {
    private static final Color PRIMARY = new Color(125, 21, 175);
    private static final Color LIGHT_BG = new Color(248, 248, 251);
    private static final Color INPUT_BG = new Color(237, 237, 237);
    private static final Color BORDER = new Color(217, 217, 217);

    @Override
    public JButton createHeaderActionButton(String text, ActionListener action) {
        JButton button = createRoundedButton(text, new Color(236, 231, 240), PRIMARY, 100, 30);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addActionListener(action);
        return button;
    }

    @Override
    public JButton createPrimaryActionButton(String text, ActionListener action) {
        JButton button = createRoundedButton(text, new Color(235, 235, 235), PRIMARY, 170, 34);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addActionListener(action);
        return button;
    }

    @Override
    public JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.PLAIN, 13));
        button.setMaximumSize(new Dimension(170, 36));
        button.setForeground(new Color(67, 67, 67));
        button.addActionListener(action);
        return button;
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
    public JLabel createStatusBadge(String statusText) {
        JLabel badge = new JLabel(statusText, SwingConstants.CENTER);
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
        JButton button = new JButton(text) {
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

        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }
}
