package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Patrón Decorator - decorador abstracto que envuelve un PanelComponent y delega la
 * construcción del panel al componente interno.
 */
public abstract class PanelDecorator extends PanelComponent {
    protected PanelComponent component;

    public void setComponent(PanelComponent component) {
        this.component = component;
    }

    @Override
    public JPanel build() {
        if (component == null) {
            throw new IllegalStateException(
                    "Se debe invocar setComponent() antes de llamar a build().");
        }
        return component.build();
    }
}
