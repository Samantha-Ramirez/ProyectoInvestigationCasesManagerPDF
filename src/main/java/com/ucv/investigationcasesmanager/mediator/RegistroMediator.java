package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * PDyF: Este código implementa el patrón Mediator para centralizar la lógica de validación y
 * preparación de un caso antes de guardarlo. El RegistroCasoView delega esta responsabilidad al
 * RegistroMediator, quien se encarga de validar los datos, asignar estatus según el rol del usuario
 * y establecer la fecha y el mes automáticamente.
 */
public class RegistroMediator {
    // Validar los datos obligatorios, manejar la lógica de estatus y asignar fecha/mes
    public static boolean validarYPreparar(Caso caso, Usuario usuario, String duracionTexto) {
        try {
            int duracion =
                    (duracionTexto.equals("Duración (Días)")) ? 0 : Integer.parseInt(duracionTexto);
            caso.setDuracionDias(duracion);
        } catch (NumberFormatException e) {
            return false;
        }

        if (usuario.getRol().equalsIgnoreCase("Administrador")) {
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
