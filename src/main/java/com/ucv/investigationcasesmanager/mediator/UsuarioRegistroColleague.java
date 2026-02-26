
package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Usuario;

/**
 * PDyF: Este código implementa el patrón Mediator para gestionar la comunicación entre los colegas
 * que participan en el proceso de registro de casos, centralizando la lógica de validación y
 * preparación del caso.
 */

// Colega concreto
public class UsuarioRegistroColleague extends RegistroColleague {
    private final Usuario usuario;

    public UsuarioRegistroColleague(RegistroMediator mediator, Usuario usuario) {
        super(mediator);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
