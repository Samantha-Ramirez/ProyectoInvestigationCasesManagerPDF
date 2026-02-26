package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Este código implementa el patrón Decorator para agregar funcionalidades visuales a los
 * paneles de manera flexible y reutilizable, permitiendo componer decoradores para lograr el diseño
 * deseado.
 */

// Decorador abstracto
public abstract class PanelDecorator implements PanelComponent {
    protected final PanelComponent panelComponent;

    protected PanelDecorator(PanelComponent panelComponent) {
        this.panelComponent = panelComponent;
    }

    @Override
    public JPanel build() {
        return panelComponent.build();
    }
}
