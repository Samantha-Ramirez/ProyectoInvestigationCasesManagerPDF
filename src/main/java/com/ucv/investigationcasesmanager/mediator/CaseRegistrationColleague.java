package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Case;

/**
 * PDyF: Mediator - colega concreto que porta los datos del caso a registrar.
 */
public class CaseRegistrationColleague extends RegistrationColleague {
    private final Case caseObj;
    private final String durationText;

    public CaseRegistrationColleague(RegistrationMediator mediator, Case caseObj,
            String durationText) {
        super(mediator);
        this.caseObj = caseObj;
        this.durationText = durationText;
    }

    public boolean send(String message) {
        return mediator.send(message, this);
    }

    public void notify(String message) {
        System.out.println("CaseRegistrationColleague notificado: " + message);
    }

    public Case getCase() {
        return caseObj;
    }

    public String getDurationText() {
        return durationText;
    }
}
