package com.ucv.investigationcasesmanager.dto;

/*
 * DTO que contiene los datos del formulario de registro de caso.
 * La vista lo completa y el controlador lo utiliza para construir y guardar el objeto Case.
 */
public class CaseFormData {
    public String caseNumber;
    public String mobileAffected;
    public String objectiveVictim;
    public String incident;
    public String duration;
    public String modusOperandiDescription;
    public String supportArea;
    public String detectionOrigin;
    public String fraudDiagnosis;
    public String conclusionsRecommendations;
    public String observations;
    public String support;
    public int caseTypeId;
    public int investigatorId;
    public int irregularityTypeId;
    public int irregularitySubtypeId;
    public int actionPerformedId;
}
