package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.InicioSesionDAO;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import com.ucv.investigationcasesmanager.model.Sesion;
import com.ucv.investigationcasesmanager.model.Usuario;

import javax.swing.*;
import java.awt.*;

/*
 * Vista de inicio de sesión.
 */
public class InicioSesionView extends BaseView {
    private JTextField txtCedula;
    private JPasswordField txtPassword;

    public InicioSesionView() {
        super("Inicio de sesión", false);
    }

    @Override
    protected void inicializarComponentesEspecificos() {
        panelContenido.removeAll();
        panelContenido.setLayout(new GridBagLayout());

        JPanel tarjetaLogin = crearTarjeta();
        tarjetaLogin.setLayout(new GridBagLayout());
        tarjetaLogin.setPreferredSize(new Dimension(470, 270));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 12, 0);

        JLabel lblTitulo = new JLabel("Inicio de sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        tarjetaLogin.add(lblTitulo, gbc);

        gbc.gridy++;
        tarjetaLogin.add(new JLabel("Cédula"), gbc);

        gbc.gridy++;
        txtCedula = new JTextField("30243278");
        estilizarEntrada(txtCedula);
        tarjetaLogin.add(txtCedula, gbc);

        gbc.gridy++;
        tarjetaLogin.add(new JLabel("Contraseña"), gbc);

        gbc.gridy++;
        txtPassword = new JPasswordField("********");
        estilizarEntrada(txtPassword);
        tarjetaLogin.add(txtPassword, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnIniciarSesion = crearBotonPrimario("Iniciar sesión", e -> accionIniciarSesion());
        btnIniciarSesion.setPreferredSize(new Dimension(160, 34));
        tarjetaLogin.add(btnIniciarSesion, gbc);

        panelContenido.add(tarjetaLogin, new GridBagConstraints());
    }

    private void accionIniciarSesion() {
        InicioSesionDAO dao = new InicioSesionDAO();
        Usuario usuario = dao.consultarUsuario(txtCedula.getText().trim());

        if (usuario != null) {
            Sesion.setUsuario(usuario);
            configurarVista(this, InicioClient.inicioSegunRol(usuario.getRol()));
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }
}
