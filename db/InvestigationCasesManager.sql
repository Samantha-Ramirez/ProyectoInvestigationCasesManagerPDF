-- Script SQL para SQLite: crea las tablas en inglés (nombres basados en los atributos del modelo).
-- Ejecutar en SQLite para crear la base de datos desde cero o migrar desde el esquema en español.

PRAGMA foreign_keys = ON;

-- ─────────────────────────────────────────────────────────────────────────────
-- Tabla: user  (reemplaza a: usuario)
-- ─────────────────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS user (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    id_number  TEXT NOT NULL UNIQUE,
    email      TEXT NOT NULL UNIQUE,
    role       TEXT NOT NULL
);

-- ─────────────────────────────────────────────────────────────────────────────
-- Tabla: investigation_case  (reemplaza a: caso)
-- ─────────────────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS investigation_case (
    id                          INTEGER PRIMARY KEY AUTOINCREMENT,
    case_number                 TEXT,
    start_date                  TEXT NOT NULL,
    days_elapsed                INTEGER,
    registration_month          INTEGER,
    status                      TEXT DEFAULT 'Abierto',
    mobile_affected             TEXT,
    objective_victim            TEXT,
    incident                    TEXT,
    duration_days               INTEGER,
    modus_operandi_description  TEXT,
    support_area                TEXT,
    detection_origin            TEXT,
    fraud_diagnosis             TEXT,
    conclusions_recommendations TEXT,
    observations                TEXT,
    support                     TEXT,
    investigator_id             INTEGER,
    case_type_id                INTEGER,
    irregularity_type_id        INTEGER,
    irregularity_subtype_id     INTEGER,
    action_performed_id         INTEGER,
    FOREIGN KEY (investigator_id) REFERENCES user(id)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- Tabla: case_follow_up  (reemplaza a: seguimiento)
-- ─────────────────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS case_follow_up (
    id                   INTEGER PRIMARY KEY AUTOINCREMENT,
    case_id              INTEGER NOT NULL,
    investigator_id      INTEGER NOT NULL,
    registration_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activities_performed TEXT,
    involved_persons     TEXT,
    exposed_amount       REAL DEFAULT 0,
    status               TEXT DEFAULT 'En Seguimiento',
    observations         TEXT,
    recommendations      TEXT,
    conclusions          TEXT,
    FOREIGN KEY (case_id)         REFERENCES investigation_case(id),
    FOREIGN KEY (investigator_id) REFERENCES user(id)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- Tablas de catálogos para UC09 – Gestionar Entidades del Sistema
-- ─────────────────────────────────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS gap_type (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS project_type (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS corrected_process (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS performed_process (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS company (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS record_subtype (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS irregularity_type (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS irregularity_subtype (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    name    TEXT NOT NULL,
    type_id INTEGER,
    FOREIGN KEY (type_id) REFERENCES irregularity_type(id)
);

CREATE TABLE IF NOT EXISTS case_origin (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

-- ─────────────────────────────────────────────────────────────────────────────
-- Datos de ejemplo (usuarios de prueba)
-- ─────────────────────────────────────────────────────────────────────────────
INSERT INTO user (first_name, last_name, id_number, email, role)
VALUES ('Samantha', 'Ramirez', '31307714', 'samantha@gmail.com', 'Administrador'),
       ('María',    'Miranda', '30243278', 'maria@gmail.com',    'Investigador');
