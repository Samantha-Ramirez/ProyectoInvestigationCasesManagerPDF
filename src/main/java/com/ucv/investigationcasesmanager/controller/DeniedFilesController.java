package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.DeniedPersonDAO;
import com.ucv.investigationcasesmanager.dao.StolenEquipmentDAO;
import com.ucv.investigationcasesmanager.decorator.AuditSaveDecorator;
import com.ucv.investigationcasesmanager.decorator.ConcreteSaveOperation;
import com.ucv.investigationcasesmanager.model.DeniedPerson;
import com.ucv.investigationcasesmanager.model.Session;
import com.ucv.investigationcasesmanager.model.StolenEquipment;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;

/*
 * Controlador para UC10 - Marcar Archivos como Negados. Gestiona el CRUD de personal
 * amonestado-desincorporado y seriales de equipos robados. PDyF: Decorator – envuelve cada
 * operación de guardado con AuditSaveDecorator para registrar automáticamente la traza de
 * auditoría.
 */
public class DeniedFilesController {
    private final DeniedPersonDAO deniedPersonDAO;
    private final StolenEquipmentDAO stolenEquipmentDAO;

    public DeniedFilesController() {
        this.deniedPersonDAO = ServiceLocator.get(DeniedPersonDAO.class);
        this.stolenEquipmentDAO = ServiceLocator.get(StolenEquipmentDAO.class);
    }

    // ── Personal Amonestado ───────────────────────────────────────────────────

    public List<DeniedPerson> getAllDeniedPersons() {
        return deniedPersonDAO.findAll();
    }

    public String saveDeniedPerson(DeniedPerson p) {
        if (p.getCi() == null || p.getCi().isBlank())
            return "La cédula no puede estar vacía.";
        if (p.getFirstName() == null || p.getFirstName().isBlank())
            return "El nombre no puede estar vacío.";
        if (p.getLastName() == null || p.getLastName().isBlank())
            return "El apellido no puede estar vacío.";

        final boolean[] ok = {false};
        String user = currentUsername();
        ConcreteSaveOperation base = new ConcreteSaveOperation(() -> ok[0] = deniedPersonDAO.save(p));
        AuditSaveDecorator decorated =
                new AuditSaveDecorator(user, "Registro de personal amonestado: " + p.getFullName());
        decorated.setComponent(base);
        decorated.guardar();

        return ok[0] ? null : "Error al guardar el registro.";
    }

    public String updateDeniedPerson(DeniedPerson p) {
        if (p.getCi() == null || p.getCi().isBlank())
            return "La cédula no puede estar vacía.";
        if (p.getFirstName() == null || p.getFirstName().isBlank())
            return "El nombre no puede estar vacío.";
        if (p.getLastName() == null || p.getLastName().isBlank())
            return "El apellido no puede estar vacío.";

        final boolean[] ok = {false};
        String user = currentUsername();
        ConcreteSaveOperation base =
                new ConcreteSaveOperation(() -> ok[0] = deniedPersonDAO.update(p));
        AuditSaveDecorator decorated = new AuditSaveDecorator(user,
                "Actualización de personal amonestado: " + p.getFullName());
        decorated.setComponent(base);
        decorated.guardar();

        return ok[0] ? null : "Error al actualizar el registro.";
    }

    public String deleteDeniedPerson(int id) {
        final boolean[] ok = {false};
        String user = currentUsername();
        ConcreteSaveOperation base =
                new ConcreteSaveOperation(() -> ok[0] = deniedPersonDAO.delete(id));
        AuditSaveDecorator decorated =
                new AuditSaveDecorator(user, "Eliminación de personal amonestado ID: " + id);
        decorated.setComponent(base);
        decorated.guardar();

        return ok[0] ? null : "Error al eliminar el registro.";
    }

    // ── Equipos Robados ───────────────────────────────────────────────────────

    public List<StolenEquipment> getAllStolenEquipment() {
        return stolenEquipmentDAO.findAll();
    }

    public String saveStolenEquipment(StolenEquipment e) {
        if (e.getSerial() == null || e.getSerial().isBlank())
            return "El serial no puede estar vacío.";

        final boolean[] ok = {false};
        String user = currentUsername();
        ConcreteSaveOperation base =
                new ConcreteSaveOperation(() -> ok[0] = stolenEquipmentDAO.save(e));
        AuditSaveDecorator decorated =
                new AuditSaveDecorator(user, "Registro de equipo robado serial: " + e.getSerial());
        decorated.setComponent(base);
        decorated.guardar();

        return ok[0] ? null : "Error al guardar el registro.";
    }

    public String updateStolenEquipment(StolenEquipment e) {
        if (e.getSerial() == null || e.getSerial().isBlank())
            return "El serial no puede estar vacío.";

        final boolean[] ok = {false};
        String user = currentUsername();
        ConcreteSaveOperation base =
                new ConcreteSaveOperation(() -> ok[0] = stolenEquipmentDAO.update(e));
        AuditSaveDecorator decorated = new AuditSaveDecorator(user,
                "Actualización de equipo robado serial: " + e.getSerial());
        decorated.setComponent(base);
        decorated.guardar();

        return ok[0] ? null : "Error al actualizar el registro.";
    }

    public String deleteStolenEquipment(int id) {
        final boolean[] ok = {false};
        String user = currentUsername();
        ConcreteSaveOperation base =
                new ConcreteSaveOperation(() -> ok[0] = stolenEquipmentDAO.delete(id));
        AuditSaveDecorator decorated =
                new AuditSaveDecorator(user, "Eliminación de equipo robado ID: " + id);
        decorated.setComponent(base);
        decorated.guardar();

        return ok[0] ? null : "Error al eliminar el registro.";
    }

    private String currentUsername() {
        return Session.getUser() != null
                ? Session.getUser().getFirstName() + " " + Session.getUser().getLastName()
                : "Sistema";
    }
}
