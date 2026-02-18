package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.InicioSesionDAO;
import com.ucv.investigationcasesmanager.model.Sesion;
import com.ucv.investigationcasesmanager.model.Usuario;
import javax.swing.*;
import java.awt.*;

/*
 * Vista de inicio de sesión. Permite a los usuarios ingresar su cédula para acceder a la
 * aplicación.
 */
public class InicioSesionView extends JFrame {
    private JTextField txtCedula;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public InicioSesionView() {
        setTitle("Inicio de sesión");
        setSize(1100, 700);
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

        btnLogin.addActionListener(e -> inicioSesion());
    }

    private void inicioSesion() {
        InicioSesionDAO dao = new InicioSesionDAO();
        Usuario usuario = dao.consultarUsuario(txtCedula.getText());

        if (usuario != null) {
            this.dispose();
            Sesion.setUsuario(usuario);
            InicioCreator.inicioSegunRol(usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }
}
