package com.ucv.investigationcasesmanager;

import com.ucv.investigationcasesmanager.ui.LookAndFeelConfigurator;
import com.ucv.investigationcasesmanager.view.InicioSesionView;
import javax.swing.SwingUtilities;

/*
 * Iniciar la aplicación
 */
public class App {
    public static void main(String[] args) {
        LookAndFeelConfigurator.setup();
        SwingUtilities.invokeLater(() -> new InicioSesionView().setVisible(true));
    }
}
