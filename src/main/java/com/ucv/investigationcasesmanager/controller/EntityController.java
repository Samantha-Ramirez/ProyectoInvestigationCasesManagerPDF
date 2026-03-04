package com.ucv.investigationcasesmanager.controller;

import com.ucv.investigationcasesmanager.dao.SystemEntityDAO;
import com.ucv.investigationcasesmanager.dao.UserDAO;
import com.ucv.investigationcasesmanager.iterator.EntityIterator;
import com.ucv.investigationcasesmanager.iterator.EntityListIterator;
import com.ucv.investigationcasesmanager.model.EntityType;
import com.ucv.investigationcasesmanager.model.SystemEntity;
import com.ucv.investigationcasesmanager.service.ServiceLocator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * Controlador para UC09 – Gestionar Entidades del Sistema. Centraliza el CRUD de los catálogos y
 * provee iteradores para UC13 – Seleccionar Entidad por Código.
 */
public class EntityController {
    private final SystemEntityDAO entityDAO;
    private final UserDAO userDAO;

    public EntityController() {
        this.entityDAO = ServiceLocator.get(SystemEntityDAO.class);
        this.userDAO = ServiceLocator.get(UserDAO.class);
    }

    // Obtener todos los registros de una entidad dada
    public List<SystemEntity> getAll(EntityType type) {
        if (type == EntityType.INVESTIGATOR) {
            return userDAO.findInvestigators().stream()
                    .map(u -> new SystemEntity(u.getId(),
                            u.getFirstName() + " " + u.getLastName()))
                    .collect(Collectors.toList());
        }
        return entityDAO.findAll(type.getTableName());
    }

    /**
     * PDyF: Iterator – retorna un iterador sobre la colección de entidades para que las vistas
     * puedan poblar combos (UC13) sin conocer cómo está almacenada la colección internamente.
     */
    public EntityIterator<SystemEntity> getIterator(EntityType type) {
        return new EntityListIterator<>(getAll(type));
    }

    // Registrar un nuevo registro de la entidad dada
    public String save(EntityType type, String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre no puede estar vacío.";
        }
        if (type == EntityType.INVESTIGATOR) {
            return saveInvestigator(name.trim());
        }
        return entityDAO.save(type.getTableName(), name.trim()) ? null
                : "Error al guardar el registro.";
    }

    // Actualizar el nombre de un registro existente
    public String update(EntityType type, int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            return "El nombre no puede estar vacío.";
        }
        if (type == EntityType.INVESTIGATOR) {
            return updateInvestigator(id, name.trim());
        }
        return entityDAO.update(type.getTableName(), id, name.trim()) ? null
                : "Error al actualizar el registro.";
    }

    // Eliminar un registro; los investigadores no se eliminan desde este módulo
    public String delete(EntityType type, int id) {
        if (type == EntityType.INVESTIGATOR) {
            return "Los investigadores no pueden eliminarse desde este módulo.";
        }
        return entityDAO.delete(type.getTableName(), id) ? null : "Error al eliminar el registro.";
    }

    // Por qué: auto-genera id_number y email únicos mediante UUID para satisfacer las
    // restricciones UNIQUE de la tabla user al registrar un investigador desde la gestión de
    // entidades.
    private String saveInvestigator(String fullName) {
        String[] names = splitFullName(fullName);
        String uid = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        String idNumber = "INV-" + uid;
        String email = names[0].toLowerCase().replaceAll("\\s+", "") + "." + uid + "@sistema.local";
        return userDAO.saveInvestigator(names[0], names[1], idNumber, email) ? null
                : "Error al guardar el investigador.";
    }

    private String updateInvestigator(int id, String fullName) {
        String[] names = splitFullName(fullName);
        return userDAO.updateName(id, names[0], names[1]) ? null
                : "Error al actualizar el investigador.";
    }

    // Divide un nombre completo en [firstName, lastName]; si no hay espacio, lastName queda vacío
    private String[] splitFullName(String fullName) {
        int sep = fullName.indexOf(' ');
        if (sep < 0) {
            return new String[] {fullName, ""};
        }
        return new String[] {fullName.substring(0, sep), fullName.substring(sep + 1)};
    }
}
