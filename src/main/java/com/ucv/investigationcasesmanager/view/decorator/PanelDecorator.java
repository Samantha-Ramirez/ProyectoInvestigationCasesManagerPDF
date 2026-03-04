package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Decorator pattern - abstract decorator for panel components.
 */
public abstract class PanelDecorator implements PanelComponent {
    protected final PanelComponent component;

    protected PanelDecorator(PanelComponent component) {
        this.component = component;
    }

    @Override
    public JPanel build() {
        return component.build();
    }
}
