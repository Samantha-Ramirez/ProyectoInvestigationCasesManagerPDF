package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * PDyF: Patrón Decorator - decorador concreto que agrega un borde con relleno (padding) al panel.
 */
public class PanelBorderDecorator extends PanelDecorator {
    private final int top;
    private final int left;
    private final int bottom;
    private final int right;

    public PanelBorderDecorator(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    @Override
    public JPanel build() {
        JPanel panel = super.build();
        Border existing = panel.getBorder();
        Border padding = BorderFactory.createEmptyBorder(top, left, bottom, right);
        panel.setBorder(
                existing == null ? padding : BorderFactory.createCompoundBorder(existing, padding));
        return panel;
    }
}
