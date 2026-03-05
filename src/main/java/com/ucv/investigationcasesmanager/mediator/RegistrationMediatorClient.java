package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.User;

/**
 * PDyF: Patrón Mediator - cliente que orquesta la validación y preparación de un caso antes de su
 * persistencia, desacoplando la lógica del controlador.
 */
public final class RegistrationMediatorClient {
    private RegistrationMediatorClient() {}

    public static boolean validateAndPrepare(Case caseObj, User user, String durationText) {
        RegistrationConcreteMediator mediator = new RegistrationConcreteMediator();
        CaseRegistrationColleague caseColleague =
                new CaseRegistrationColleague(mediator, caseObj, durationText);
        UserRegistrationColleague userColleague = new UserRegistrationColleague(mediator, user);

        mediator.setCaseColleague(caseColleague);
        mediator.setUserColleague(userColleague);

        return caseColleague.send("VALIDATE_AND_PREPARE");
    }
}
