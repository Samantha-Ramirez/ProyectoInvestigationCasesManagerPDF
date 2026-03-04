package com.ucv.investigationcasesmanager;

import com.ucv.investigationcasesmanager.ui.ThemeConfig;
import com.ucv.investigationcasesmanager.view.LoginView;
import javax.swing.SwingUtilities;

/*
 * Punto de entrada de la aplicación.
 */
public class App {
    public static void main(String[] args) {
        ThemeConfig.setup();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
