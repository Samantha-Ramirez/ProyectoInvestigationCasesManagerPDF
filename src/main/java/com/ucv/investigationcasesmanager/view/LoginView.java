package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.LoginController;
import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.model.Session;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import java.awt.*;

/*
 * Vista de inicio de sesión.
 */
public class LoginView extends BaseView {
    private final LoginController loginController;
    private JTextField txtIdNumber;
    private JPasswordField txtPassword;

    public LoginView() {
        super("Inicio de sesión", false);
        this.loginController = new LoginController();
    }

    @Override
    protected void initComponents() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridBagLayout());

        JPanel loginCard = createCard();
        loginCard.setLayout(new GridBagLayout());
        loginCard.setPreferredSize(new Dimension(470, 270));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 12, 0);

        JLabel lblTitle = new JLabel("Inicio de sesión");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        loginCard.add(lblTitle, gbc);

        gbc.gridy++;
        loginCard.add(new JLabel("Cédula"), gbc);

        gbc.gridy++;
        txtIdNumber = new JTextField();
        styleInput(txtIdNumber);
        loginCard.add(txtIdNumber, gbc);

        gbc.gridy++;
        loginCard.add(new JLabel("Contraseña"), gbc);

        gbc.gridy++;
        txtPassword = new JPasswordField("********");
        styleInput(txtPassword);
        loginCard.add(txtPassword, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnLogin = createPrimaryButton("Iniciar sesión", e -> handleLogin());
        btnLogin.setPreferredSize(new Dimension(160, 34));
        loginCard.add(btnLogin, gbc);

        contentPanel.add(loginCard, new GridBagConstraints());
    }

    private void handleLogin() {
        User user = loginController.authenticate(txtIdNumber.getText().trim());
        if (user != null) {
            Session.setUser(user);
            navigate(this, StartupViewFactory.getStartView(user.getRole()));
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }
}
