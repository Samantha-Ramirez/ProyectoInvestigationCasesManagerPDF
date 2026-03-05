package com.ucv.investigationcasesmanager.model;

/*
 * Modelo que representa un caso de investigación.
 */
public class Case {
    private int id;
    private String caseNumber;
    private String startDate;
    private int days;
    private int month;
    private int durationDays;
    private String timeWithoutAttention;
    private String status;
    private String mobileAffected;
    private String objectiveVictim;
    private String incident;
    private String modusOperandiDescription;
    private String supportArea;
    private String detectionOrigin;
    private String fraudDiagnosis;
    private String conclusions;
    private String recommendations;
    private String observations;
    private String support;
    private int investigatorId;
    private String investigatorName;
    private int caseTypeId;
    private int irregularityTypeId;
    private int irregularitySubtypeId;
    private int actionPerformedId;

    public Case() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public String getTimeWithoutAttention() {
        return timeWithoutAttention;
    }

    public void setTimeWithoutAttention(String timeWithoutAttention) {
        this.timeWithoutAttention = timeWithoutAttention;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobileAffected() {
        return mobileAffected;
    }

    public void setMobileAffected(String mobileAffected) {
        this.mobileAffected = mobileAffected;
    }

    public String getObjectiveVictim() {
        return objectiveVictim;
    }

    public void setObjectiveVictim(String objectiveVictim) {
        this.objectiveVictim = objectiveVictim;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getModusOperandiDescription() {
        return modusOperandiDescription;
    }

    public void setModusOperandiDescription(String modusOperandiDescription) {
        this.modusOperandiDescription = modusOperandiDescription;
    }

    public String getSupportArea() {
        return supportArea;
    }

    public void setSupportArea(String supportArea) {
        this.supportArea = supportArea;
    }

    public String getDetectionOrigin() {
        return detectionOrigin;
    }

    public void setDetectionOrigin(String detectionOrigin) {
        this.detectionOrigin = detectionOrigin;
    }

    public String getFraudDiagnosis() {
        return fraudDiagnosis;
    }

    public void setFraudDiagnosis(String fraudDiagnosis) {
        this.fraudDiagnosis = fraudDiagnosis;
    }

    public String getConclusions() {
        return conclusions;
    }

    public void setConclusions(String conclusions) {
        this.conclusions = conclusions;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public int getInvestigatorId() {
        return investigatorId;
    }

    public void setInvestigatorId(int investigatorId) {
        this.investigatorId = investigatorId;
    }

    public String getInvestigatorName() {
        return investigatorName;
    }

    public void setInvestigatorName(String investigatorName) {
        this.investigatorName = investigatorName;
    }

    public int getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(int caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public int getIrregularityTypeId() {
        return irregularityTypeId;
    }

    public void setIrregularityTypeId(int irregularityTypeId) {
        this.irregularityTypeId = irregularityTypeId;
    }

    public int getIrregularitySubtypeId() {
        return irregularitySubtypeId;
    }

    public void setIrregularitySubtypeId(int irregularitySubtypeId) {
        this.irregularitySubtypeId = irregularitySubtypeId;
    }

    public int getActionPerformedId() {
        return actionPerformedId;
    }

    public void setActionPerformedId(int actionPerformedId) {
        this.actionPerformedId = actionPerformedId;
    }
}
