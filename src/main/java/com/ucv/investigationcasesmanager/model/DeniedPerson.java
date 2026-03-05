package com.ucv.investigationcasesmanager.model;

/*
 * Modelo que representa un registro de personal amonestado o desincorporado.
 */
public class DeniedPerson {
    private int id;
    private String ci;
    private String firstName;
    private String lastName;
    private String company;

    public DeniedPerson() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
