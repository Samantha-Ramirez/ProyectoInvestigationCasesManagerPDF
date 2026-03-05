package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Este código implementa el patrón Decorator para agregar funcionalidades visuales a los
 * paneles de manera flexible y reutilizable, permitiendo componer decoradores para lograr el diseño
 * deseado.
 */

// Componente
public abstract class PanelComponent {
    public abstract JPanel build();
}
