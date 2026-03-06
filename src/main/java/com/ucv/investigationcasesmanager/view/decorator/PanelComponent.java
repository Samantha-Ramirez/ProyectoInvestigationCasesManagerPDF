package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.JPanel;

/**
 * PDyF: Decorator - componente abstracto que agrega funcionalidades visuales a los paneles de
 * manera flexible y reutilizable, permitiendo componer decoradores para lograr el diseño deseado.
 */

// Componente
public abstract class PanelComponent {
    public abstract JPanel build();
}
