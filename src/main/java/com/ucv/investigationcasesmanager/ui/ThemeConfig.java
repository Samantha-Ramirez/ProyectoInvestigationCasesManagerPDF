package com.ucv.investigationcasesmanager.ui;

import javax.swing.*;

/*
 * Configures FlatLaf look and feel.
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
                // ignore
            }
        }
    }
}
