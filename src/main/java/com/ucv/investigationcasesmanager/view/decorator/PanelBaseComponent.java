package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Este código implementa el patrón Decorator para agregar funcionalidades visuales a los
 * paneles de manera flexible y reutilizable, permitiendo componer decoradores para lograr el diseño
 * deseado.
 */

// Componente concreto
public class PanelBaseComponent implements PanelComponent {
    private final JPanel panel;

    public PanelBaseComponent(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public JPanel build() {
        return panel;
    }
}
