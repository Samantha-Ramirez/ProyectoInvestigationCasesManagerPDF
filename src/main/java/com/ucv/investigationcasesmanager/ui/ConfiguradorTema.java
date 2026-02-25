package com.ucv.investigationcasesmanager.ui;

import javax.swing.*;

/*
 * Configura FlatLaf.
 */
public final class ConfiguradorTema {

    private ConfiguradorTema() {}

    public static void setup() {
        try {
            Class<?> claseConfigurador = Class.forName("com.formdev.flatlaf.FlatLightLaf");
            LookAndFeel configurador =
                    (LookAndFeel) claseConfigurador.getDeclaredConstructor().newInstance();
            UIManager.setLookAndFeel(configurador);
            System.setProperty("flatlaf.uiScale", "1.0");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
            }
        }
    }
}
