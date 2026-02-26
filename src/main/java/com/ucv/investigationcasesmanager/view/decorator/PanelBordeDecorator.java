package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * PDyF: Este código implementa el patrón Decorator para agregar funcionalidades visuales a los
 * paneles de manera flexible y reutilizable, permitiendo componer decoradores para lograr el diseño
 * deseado.
 */

// Decorador concreto
public class PanelBordeDecorator extends PanelDecorator {
    private final int top;
    private final int left;
    private final int bottom;
    private final int right;

    public PanelBordeDecorator(PanelComponent panelComponent, int top, int left, int bottom,
            int right) {
        super(panelComponent);
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    @Override
    public JPanel build() {
        JPanel panel = super.build();
        Border anterior = panel.getBorder();
        Border padding = BorderFactory.createEmptyBorder(top, left, bottom, right);
        panel.setBorder(
                anterior == null ? padding : BorderFactory.createCompoundBorder(anterior, padding));
        return panel;
    }
}
