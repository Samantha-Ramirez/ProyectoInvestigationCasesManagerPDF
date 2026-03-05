package com.ucv.investigationcasesmanager;

import com.ucv.investigationcasesmanager.ui.ThemeConfig;
import com.ucv.investigationcasesmanager.view.LoginView;
import javax.swing.SwingUtilities;

/*
 * Punto de entrada de la aplicación.
 */
public class App {
    public static void main(String[] args) {
        java.io.File dbFile = new java.io.File("db/InvestigationCasesManager.db");
        if (!dbFile.exists()) {
            System.err.println(
                    "¡ERROR!: No se encontró la base de datos en: " + dbFile.getAbsolutePath());
        }
        ThemeConfig.setup();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
