package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Color;

/**
 * PDyF: Este código implementa el patrón Decorator para agregar funcionalidades visuales a los
 * paneles de manera flexible y reutilizable, permitiendo componer decoradores para lograr el diseño
 * deseado.
 */

// Decorador concreto
public class PanelTituloDecorator extends PanelDecorator {
    private final String titulo;

    public PanelTituloDecorator(PanelComponent panelComponent, String titulo) {
        super(panelComponent);
        this.titulo = titulo;
    }

    @Override
    public JPanel build() {
        JPanel panel = super.build();
        Border tituloBorde = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)), titulo);
        panel.setBorder(tituloBorde);
        return panel;
    }
}
