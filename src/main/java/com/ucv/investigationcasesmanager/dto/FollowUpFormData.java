package com.ucv.investigationcasesmanager.dto;

/*
 * DTO que contiene los datos del formulario de registro de seguimiento. La vista lo completa y el
 * controlador se encarga de validarlo y persistirlo.
 */
public class FollowUpFormData {
    public String activities;
    public String involvedPersons;
    public String amountText;
    public String status;
    public String observations;
    public String recommendations;
    public String conclusions;
    public String caseNumber;
    public int investigatorId;
}
