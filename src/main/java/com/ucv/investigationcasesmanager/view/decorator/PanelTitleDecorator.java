package com.ucv.investigationcasesmanager.view.decorator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * PDyF: Decorator pattern - adds a titled border to a panel.
 */
public class PanelTitleDecorator extends PanelDecorator {
    private final String title;

    public PanelTitleDecorator(PanelComponent component, String title) {
        super(component);
        this.title = title;
    }

    @Override
    public JPanel build() {
        JPanel panel = super.build();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)), title));
        return panel;
    }
}
