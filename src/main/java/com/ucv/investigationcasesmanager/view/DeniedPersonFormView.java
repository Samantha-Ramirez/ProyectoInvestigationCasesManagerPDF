package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.DeniedFilesController;
import com.ucv.investigationcasesmanager.model.DeniedPerson;

import javax.swing.*;
import java.awt.*;

/*
 * Formulario de registro y edición de personal amonestado-desincorporado. Campos: CI, Nombre,
 * Apellido, Empresa.
 */
public class DeniedPersonFormView extends BaseView {
    private final DeniedFilesController controller;
    private final DeniedPerson existing;
    private JTextField txtCi;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtCompany;

    public DeniedPersonFormView(DeniedPerson existing) {
        super((existing == null ? "Registro" : "Edición")
                + " de personal amonestado-desincorporado", true, false);
        this.controller = new DeniedFilesController();
        this.existing = existing;
        initComponents();
    }

    @Override
    protected void initComponents() {
        String viewTitle = (existing == null ? "Registro" : "Edición")
                + " de personal amonestado-desincorporado";
        setupTitle(viewTitle, "Volver", e -> navigate(this, new DeniedPersonListView()));

        JPanel card = createCard();
        JPanel form = createForm();

        txtCi = new JTextField(existing != null ? existing.getCi() : "");
        txtFirstName = new JTextField(existing != null ? existing.getFirstName() : "");
        txtLastName = new JTextField(existing != null ? existing.getLastName() : "");
        txtCompany = new JTextField(
                existing != null && existing.getCompany() != null ? existing.getCompany() : "");

        styleInput(txtCi);
        styleInput(txtFirstName);
        styleInput(txtLastName);
        styleInput(txtCompany);

        int row = 0;
        row = addField(form, row, "CI", txtCi);
        row = addField(form, row, "Nombre", txtFirstName);
        row = addField(form, row, "Apellido", txtLastName);
        addField(form, row, "Empresa", txtCompany);

        card.add(wrapInScroll(form), BorderLayout.CENTER);
        contentPanel.add(card, BorderLayout.CENTER);

        if (existing != null) {
            JButton btnSave = createPrimaryButton("Guardar", e -> handleSave());
            JButton btnDelete = createHeaderButton("Eliminar", e -> handleDelete());
            contentPanel.add(createBottomPanel(btnDelete, btnSave), BorderLayout.SOUTH);
        } else {
            contentPanel.add(createBottomPanel(createPrimaryButton("Registrar", e -> handleSave())),
                    BorderLayout.SOUTH);
        }
    }

    private void handleSave() {
        DeniedPerson p = existing != null ? existing : new DeniedPerson();
        p.setCi(txtCi.getText().trim());
        p.setFirstName(txtFirstName.getText().trim());
        p.setLastName(txtLastName.getText().trim());
        p.setCompany(txtCompany.getText().trim());

        String error = existing == null ? controller.saveDeniedPerson(p)
                : controller.updateDeniedPerson(p);

        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registro guardado correctamente.");
        navigate(this, new DeniedPersonListView());
    }

    private void handleDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este registro?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        String error = controller.deleteDeniedPerson(existing.getId());
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
        navigate(this, new DeniedPersonListView());
    }
}
