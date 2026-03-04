package com.ucv.investigationcasesmanager.ui.factory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * PDyF: Abstract Factory pattern - creates UI components with a consistent visual style.
 */
public abstract class ScreenAbstractFactory {
    public abstract JButton createHeaderButton(String text, ActionListener action);
    public abstract JButton createPrimaryButton(String text, ActionListener action);
    public abstract JButton createMenuButton(String text, ActionListener action);
    public abstract void styleInput(JComponent component);
    public abstract void styleTable(JTable table);
    public abstract JLabel createStatusBadge(String status);
    public abstract Color getPrimaryColor();
}
