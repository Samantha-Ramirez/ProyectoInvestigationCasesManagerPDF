package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Patrón Decorator - decorador abstracto que envuelve un PanelComponent y delega la
 * construcción del panel al componente interno.
 */
public abstract class PanelDecorator extends PanelComponent {
    // Por qué: campo no final para que setComponent() permita encadenar decoradores
    // después de la construcción, siguiendo el patrón del profesor.
    protected PanelComponent component;

    public void setComponent(PanelComponent component) {
        this.component = component;
    }

    @Override
    public JPanel build() {
        if (component == null) {
            // Por qué: setComponent() debe invocarse antes de build(); sin componente envuelto
            // el decorador no puede construir el panel y falla de forma explícita.
            throw new IllegalStateException(
                    "Se debe invocar setComponent() antes de llamar a build().");
        }
        return component.build();
    }
}
