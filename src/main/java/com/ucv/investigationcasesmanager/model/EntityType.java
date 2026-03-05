package com.ucv.investigationcasesmanager.model;

/*
 * Catálogo de tipos de entidades gestionables (UC09). Cada valor contiene la etiqueta en
 * español visible al usuario y el nombre de la tabla en la base de datos.
 * Cuando tableName es null, la entidad usa la tabla user con rol 'Investigador'.
 */
public enum EntityType {
    GAP_TYPE("Tipo de Brecha", "gap_type"),
    PROJECT_TYPE("Tipo de Proyecto", "project_type"),
    CORRECTED_PROCESS("Procesos Corregidos", "corrected_process"),
    PERFORMED_PROCESS("Procesos Realizados", "performed_process"),
    INVESTIGATOR("Investigadores", null),
    COMPANY("Empresas", "company"),
    CASE_TYPE("Tipos de Casos", "case_type"),
    CASE_SUBTYPE("Subtipo de Casos", "record_subtype"),
    IRREGULARITY_TYPE("Tipo de Irregularidad", "irregularity_type"),
    IRREGULARITY_SUBTYPE("Subtipo de Irregularidad", "irregularity_subtype"),
    CASE_ORIGIN("Procedencia Casos", "case_origin"),
    PERFORMED_ACTIVITY("Actividades Realizadas", "performed_activity");

    private final String label;
    private final String tableName;

    EntityType(String label, String tableName) {
        this.label = label;
        this.tableName = tableName;
    }

    public String getLabel() {
        return label;
    }

    public String getTableName() {
        return tableName;
    }

    // Por qué: indica si la entidad requiere lógica especial (tabla user en lugar de tabla propia)
    public boolean isSpecial() {
        return tableName == null;
    }
}
