package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Patrón Decorator - decorador abstracto que envuelve un PanelComponent y delega
 * la construcción del panel al componente interno.
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
