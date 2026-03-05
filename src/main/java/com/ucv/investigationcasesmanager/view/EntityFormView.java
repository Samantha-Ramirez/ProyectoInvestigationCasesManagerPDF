package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.EntityController;
import com.ucv.investigationcasesmanager.model.EntityType;
import com.ucv.investigationcasesmanager.model.SystemEntity;

import javax.swing.*;
import java.awt.*;

/*
 * Vista de formulario para registrar o editar un registro de una entidad del sistema. En modo
 * edición también permite eliminar el registro.
 */
public class EntityFormView extends BaseView {
    private final EntityController entityController;
    private final EntityType entityType;
    private final SystemEntity existing;
    private JTextField txtTitle;

    public EntityFormView(EntityType entityType, SystemEntity existing) {
        super((existing == null ? "Registro" : "Edición") + " de " + entityType.getLabel(), true,
                false);
        this.entityController = new EntityController();
        this.entityType = entityType;
        this.existing = existing;
        initComponents();
    }

    @Override
    protected void initComponents() {
        String viewTitle =
                (existing == null ? "Registro" : "Edición") + " de " + entityType.getLabel();
        setupTitle(viewTitle, "Volver", e -> navigate(this, new EntityListView(entityType)));

        JPanel card = createCard();
        JPanel form = createForm();

        txtTitle = new JTextField(existing != null ? existing.getName() : "");
        styleInput(txtTitle);

        addField(form, 0, "Título", txtTitle);
        card.add(wrapInScroll(form), BorderLayout.CENTER);
        contentPanel.add(card, BorderLayout.CENTER);

        if (existing != null) {
            // Modo edición: botones Guardar y Eliminar
            JButton btnSave = createPrimaryButton("Guardar", e -> handleSave());
            JButton btnDelete = createHeaderButton("Eliminar", e -> handleDelete());
            contentPanel.add(createBottomPanel(btnDelete, btnSave), BorderLayout.SOUTH);
        } else {
            // Modo registro: botón Registrar
            contentPanel.add(createBottomPanel(createPrimaryButton("Registrar", e -> handleSave())),
                    BorderLayout.SOUTH);
        }
    }

    private void handleSave() {
        String name = txtTitle.getText().trim();
        String error;
        if (existing == null) {
            error = entityController.save(entityType, name);
        } else {
            error = entityController.update(entityType, existing.getId(), name);
        }

        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registro guardado correctamente.");
        navigate(this, new EntityListView(entityType));
    }

    private void handleDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este registro?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        String error = entityController.delete(entityType, existing.getId());
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
        navigate(this, new EntityListView(entityType));
    }
}
