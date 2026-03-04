package com.ucv.investigationcasesmanager.model;

import java.time.LocalDateTime;

/*
 * Modelo que representa un seguimiento registrado sobre un caso de investigación.
 */
public class CaseFollowUp {
    private int id;
    private int caseId;
    private int investigatorId;
    private LocalDateTime registrationDate;
    private String activitiesPerformed;
    private String involvedPersons;
    private double exposedAmount;
    private String status;
    private String observations;
    private String recommendations;
    private String conclusions;

    public CaseFollowUp() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public int getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(int investigatorId) {
        this.investigatorId = investigatorId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getActivitiesPerformed() {
        return activitiesPerformed;
    }

    public void setActivitiesPerformed(String activitiesPerformed) {
        this.activitiesPerformed = activitiesPerformed;
    }

    public String getInvolvedPersons() {
        return involvedPersons;
    }

    public void setInvolvedPersons(String involvedPersons) {
        this.involvedPersons = involvedPersons;
    }

    public double getExposedAmount() {
        return exposedAmount;
    }

    public void setExposedAmount(double exposedAmount) {
        this.exposedAmount = exposedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getConclusions() {
        return conclusions;
    }

    public void setConclusions(String conclusions) {
        this.conclusions = conclusions;
    }
}
