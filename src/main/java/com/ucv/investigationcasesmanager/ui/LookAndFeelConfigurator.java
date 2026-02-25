package com.ucv.investigationcasesmanager.ui;

import javax.swing.*;

/*
 * Configura FlatLaf de forma segura (fallback automático a LAF del sistema).
 */
public final class LookAndFeelConfigurator {

    private LookAndFeelConfigurator() {}

    public static void setup() {
        try {
            Class<?> lafClass = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            LookAndFeel lookAndFeel = (LookAndFeel) lafClass.getDeclaredConstructor().newInstance();
            UIManager.setLookAndFeel(lookAndFeel);
            System.setProperty("flatlaf.uiScale", "1.0");
        } catch (Exception ignored) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception alsoIgnored) {
                // Sin acción: se mantiene look&feel por defecto de Swing.
            }
        }
    }
}
