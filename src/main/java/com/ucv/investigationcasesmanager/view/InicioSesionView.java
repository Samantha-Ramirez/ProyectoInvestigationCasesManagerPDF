package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.InicioSesionDAO;
import com.ucv.investigationcasesmanager.model.Sesion;
import com.ucv.investigationcasesmanager.model.Usuario;
import com.ucv.investigationcasesmanager.factory.InicioClient;
import javax.swing.*;

/*
 * Vista de inicio de sesión. Permite a los usuarios ingresar su cédula para acceder a la
 * aplicación.
 */
public class InicioSesionView extends BaseView {
    private JTextField txtCedula;
    private JPasswordField txtPassword;

    // Configurar la vista de inicio de sesión
    public InicioSesionView() {
        super("Inicio de sesión", false);
    }

    // Configurar componentes específicos de esta vista
    @Override
    protected void inicializarComponentesEspecificos() {
        configurarTituloSuperior("Inicio de sesión", null, null);
        configurarFormulario();
        txtCedula = new JTextField("Cédula");
        txtPassword = new JPasswordField("Contraseña", 20);
        agregarCampoFormulario(txtCedula);
        agregarCampoFormulario(txtPassword);
        agregarBotonAccionPrincipal("Iniciar sesión", e -> accionIniciarSesion());
    }

    // Consultar el usuario por cédula y, si existe, iniciar sesión
    private void accionIniciarSesion() {
        InicioSesionDAO dao = new InicioSesionDAO();
        Usuario usuario = dao.consultarUsuario(txtCedula.getText());

        if (usuario != null) {
            Sesion.setUsuario(usuario);
            configurarVista(this, InicioClient.inicioSegunRol(usuario.getRol()));
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
        }
    }
}
