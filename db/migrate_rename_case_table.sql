-- Migración: renombrar la tabla investigation_case a "case"
-- ADVERTENCIA: "case" es una palabra reservada en SQL; se usa siempre entre comillas dobles.
-- Ejecutar una sola vez sobre la base de datos existente:

ALTER TABLE investigation_case RENAME TO "case";
