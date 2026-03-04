package com.ucv.investigationcasesmanager.ui;

import javax.swing.*;

/*
 * Configura el Look and Feel de la aplicación usando FlatLaf.
 */
public final class ThemeConfig {
    private ThemeConfig() {}

    public static void setup() {
        try {
            Class<?> lafClass = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            LookAndFeel laf = (LookAndFeel) lafClass.getDeclaredConstructor().newInstance();
            UIManager.setLookAndFeel(laf);
            System.setProperty("flatlaf.uiScale", "1.0");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // ignorar: si FlatLaf no está disponible se usa el look nativo del sistema
            }
        }
    }
}
