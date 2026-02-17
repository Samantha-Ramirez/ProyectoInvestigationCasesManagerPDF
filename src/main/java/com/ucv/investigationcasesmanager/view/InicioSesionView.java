package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.InicioSesionDAO;
import javax.swing.*;
import java.awt.*;

public class InicioSesionView extends JFrame {
    private JTextField txtCedula;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public InicioSesionView() {
        setTitle("Inicio de sesión");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel header = new JPanel();
        header.setBackground(new Color(128, 0, 128));
        header.setPreferredSize(new Dimension(800, 80));
        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        txtCedula = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBackground(new Color(230, 230, 230));

        centerPanel.add(new JLabel("Cédula:"));
        centerPanel.add(txtCedula);
        centerPanel.add(new JLabel("Contraseña:"));
        centerPanel.add(txtPassword);
        centerPanel.add(btnLogin);
        add(centerPanel, BorderLayout.CENTER);

        // Acción del botón
        btnLogin.addActionListener(e -> inicioSesion());
    }

    private void inicioSesion() {
        InicioSesionDAO dao = new InicioSesionDAO();
        String rol = dao.validarUsuario(txtCedula.getText()).getRol();

        if (rol != null) {
            this.dispose();

            InicioCreator creador;
            if (rol.equalsIgnoreCase("Administrador")) {
                creador = new CarteleraInicioCreator();
            } else {
                creador = new BandejaInicioCreator();
            }
            creador.FactoryMethod().mostrar();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }
}
