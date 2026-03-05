-- UC12: tabla de trazas de auditoría
CREATE TABLE IF NOT EXISTS audit_log (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    username    TEXT    NOT NULL,
    action      TEXT    NOT NULL,
    action_date TEXT    NOT NULL DEFAULT (datetime('now', 'localtime'))
);

-- UC10: tabla de personal amonestado-desincorporado
CREATE TABLE IF NOT EXISTS denied_person (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    ci         TEXT    NOT NULL,
    first_name TEXT    NOT NULL,
    last_name  TEXT    NOT NULL,
    company    TEXT
);

-- UC10: tabla de seriales de equipos reportados robados
CREATE TABLE IF NOT EXISTS stolen_equipment (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    serial         TEXT NOT NULL,
    equipment_type TEXT,
    brand          TEXT,
    model          TEXT,
    observations   TEXT
);
