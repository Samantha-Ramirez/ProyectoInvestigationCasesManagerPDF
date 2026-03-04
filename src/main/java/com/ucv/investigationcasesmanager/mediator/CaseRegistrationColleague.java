package com.ucv.investigationcasesmanager.mediator;

import com.ucv.investigationcasesmanager.model.Case;

/**
 * PDyF: Patrón Mediator - colega concreto que porta los datos del caso a registrar.
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

    public boolean requestValidationAndPreparation() {
        return mediator.send("VALIDATE_AND_PREPARE", this);
    }

    public Case getCase() { return caseObj; }

    public String getDurationText() { return durationText; }
}
