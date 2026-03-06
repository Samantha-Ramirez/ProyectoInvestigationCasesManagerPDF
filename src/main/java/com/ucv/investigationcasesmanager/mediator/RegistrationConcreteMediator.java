package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Case;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * PDyF: Patrón Mediator - mediador concreto que centraliza la validación y preparación del caso
 * antes de su registro, coordinando la comunicación entre los colegas de caso y usuario.
 */
public class RegistrationConcreteMediator extends RegistrationMediator {
    private CaseRegistrationColleague caseColleague;
    private UserRegistrationColleague userColleague;

    public void setCaseColleague(CaseRegistrationColleague caseColleague) {
        this.caseColleague = caseColleague;
    }

    public void setUserColleague(UserRegistrationColleague userColleague) {
        this.userColleague = userColleague;
    }

    @Override
    public boolean send(String event, RegistrationColleague colleague) {
        if (!"VALIDATE_AND_PREPARE".equals(event) || colleague != caseColleague
                || userColleague == null) {
            return false;
        }
        return validateAndPrepareCase(caseColleague.getCase(), userColleague,
                caseColleague.getDurationText());
    }

    private boolean validateAndPrepareCase(Case c, UserRegistrationColleague userActor,
            String durationText) {
        try {
            int duration = (durationText == null || durationText.isBlank()
                    || "Duración (Días)".equalsIgnoreCase(durationText.trim())) ? 0
                            : Integer.parseInt(durationText.trim());
            c.setDurationDays(duration);
        } catch (NumberFormatException e) {
            caseColleague.notify("Error: duración inválida.");
            return false;
        }

        if (userActor.getUser().getRole().equalsIgnoreCase("Administrador")) {
            c.setStatus("Asignado");
        } else {
            c.setStatus("Abierto");
        }

        Calendar cal = Calendar.getInstance();
        if (c.getStartDate() == null || c.getStartDate().isBlank()) {
            c.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        }
        if (c.getMonth() == 0) {
            c.setMonth(cal.get(Calendar.MONTH) + 1);
        }

        caseColleague.notify("Caso preparado correctamente.");
        userColleague.notify(
                "Registro autorizado para el usuario: " + userActor.getUser().getFirstName());
        return true;
    }
}
