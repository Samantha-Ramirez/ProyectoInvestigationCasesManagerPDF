package com.ucv.investigationcasesmanager;

import com.ucv.investigationcasesmanager.ui.ConfiguradorTema;
import com.ucv.investigationcasesmanager.view.InicioSesionView;
import javax.swing.SwingUtilities;

/*
 * Iniciar la aplicación.
 */
public class App {
    public static void main(String[] args) {
        ConfiguradorTema.setup();
        SwingUtilities.invokeLater(() -> new InicioSesionView().setVisible(true));
    }
}
