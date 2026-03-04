package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * PDyF: Decorator pattern - adds empty border padding to a panel.
 */
public class PanelBorderDecorator extends PanelDecorator {
    private final int top;
    private final int left;
    private final int bottom;
    private final int right;

    public PanelBorderDecorator(PanelComponent component, int top, int left, int bottom,
            int right) {
        super(component);
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
        panel.setBorder(existing == null ? padding
                : BorderFactory.createCompoundBorder(existing, padding));
        return panel;
    }
}
