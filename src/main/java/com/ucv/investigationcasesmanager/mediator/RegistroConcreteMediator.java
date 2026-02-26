package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Caso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * PDyF: Este código implementa el patrón Mediator para gestionar la comunicación entre los colegas
 * que participan en el proceso de registro de casos, centralizando la lógica de validación y
 * preparación del caso.
 */

// Mediador concreto
public class RegistroConcreteMediator extends RegistroMediator {
    private CasoRegistroColleague casoColleague;
    private UsuarioRegistroColleague usuarioColleague;

    public void setCasoColleague(CasoRegistroColleague casoColleague) {
        this.casoColleague = casoColleague;
    }

    public void setUsuarioColleague(UsuarioRegistroColleague usuarioColleague) {
        this.usuarioColleague = usuarioColleague;
    }

    @Override
    public boolean enviar(String evento, RegistroColleague colleague) {
        if (!"VALIDAR_Y_PREPARAR".equals(evento) || colleague != casoColleague
                || usuarioColleague == null) {
            return false;
        }

        return validarYPrepararCaso(casoColleague.getCaso(), usuarioColleague,
                casoColleague.getDuracionTexto());
    }

    private boolean validarYPrepararCaso(Caso caso, UsuarioRegistroColleague usuarioActor,
            String duracionTexto) {
        try {
            int duracion = (duracionTexto == null || duracionTexto.isBlank()
                    || "Duración (Días)".equalsIgnoreCase(duracionTexto.trim())) ? 0
                            : Integer.parseInt(duracionTexto.trim());
            caso.setDuracionDias(duracion);
        } catch (NumberFormatException e) {
            return false;
        }

        if (usuarioActor.getUsuario().getRol().equalsIgnoreCase("Administrador")) {
            caso.setEstatus("Asignado");
        } else {
            caso.setEstatus("Abierto");
        }

        Calendar cal = Calendar.getInstance();
        caso.setFechaInicio(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        caso.setMes(cal.get(Calendar.MONTH) + 1);

        return true;
    }
}
