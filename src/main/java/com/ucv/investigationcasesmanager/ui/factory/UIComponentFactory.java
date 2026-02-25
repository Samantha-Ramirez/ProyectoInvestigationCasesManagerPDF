package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*
 * Abstract Factory para construir componentes visuales consistentes.
 */
public interface UIComponentFactory {
    JButton createHeaderActionButton(String text, ActionListener action);

    JButton createPrimaryActionButton(String text, ActionListener action);

    JButton createMenuButton(String text, ActionListener action);

    void styleInput(JComponent component);

    void styleTable(JTable table);

    JLabel createStatusBadge(String statusText);

    Color getPrimaryColor();
}
