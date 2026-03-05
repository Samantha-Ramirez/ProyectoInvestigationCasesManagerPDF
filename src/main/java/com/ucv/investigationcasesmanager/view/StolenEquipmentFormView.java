package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.DeniedFilesController;
import com.ucv.investigationcasesmanager.model.StolenEquipment;

import javax.swing.*;
import java.awt.*;

/*
 * UC10 – Formulario de registro y edición de seriales de equipos reportados robados. Campos:
 * Serial, Tipo de equipo, Marca, Modelo, Observaciones.
 */
public class StolenEquipmentFormView extends BaseView {
    private final DeniedFilesController controller;
    private final StolenEquipment existing;
    private JTextField txtSerial;
    private JTextField txtType;
    private JTextField txtBrand;
    private JTextField txtModel;
    private JTextArea txtObservations;

    public StolenEquipmentFormView(StolenEquipment existing) {
        super((existing == null ? "Registro" : "Edición")
                + " de serial de equipo reportado robado", true, false);
        this.controller = new DeniedFilesController();
        this.existing = existing;
        initComponents();
    }

    @Override
    protected void initComponents() {
        String viewTitle = (existing == null ? "Registro" : "Edición")
                + " de serial de equipo reportado robado";
        setupTitle(viewTitle, "Volver", e -> navigate(this, new StolenEquipmentListView()));

        JPanel card = createCard();
        JPanel form = createForm();

        txtSerial = new JTextField(existing != null ? existing.getSerial() : "Serial");
        txtType = new JTextField(
                existing != null && existing.getEquipmentType() != null ? existing.getEquipmentType()
                        : "Tipo de equipo");
        txtBrand = new JTextField(
                existing != null && existing.getBrand() != null ? existing.getBrand() : "Marca");
        txtModel = new JTextField(
                existing != null && existing.getModel() != null ? existing.getModel() : "Modelo");
        txtObservations = createTextArea(3, 30, 80);
        if (existing != null && existing.getObservations() != null) {
            txtObservations.setText(existing.getObservations());
        } else {
            txtObservations.setText("Observaciones");
        }

        styleInput(txtSerial);
        styleInput(txtType);
        styleInput(txtBrand);
        styleInput(txtModel);

        int row = 0;
        row = addField(form, row, "Serial", txtSerial);
        row = addField(form, row, "Tipo de equipo", txtType);
        row = addField(form, row, "Marca", txtBrand);
        row = addField(form, row, "Modelo", txtModel);
        addField(form, row, "Observaciones", wrapInScroll(txtObservations));

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
        StolenEquipment e = existing != null ? existing : new StolenEquipment();
        e.setSerial(txtSerial.getText().trim());
        e.setEquipmentType(txtType.getText().trim());
        e.setBrand(txtBrand.getText().trim());
        e.setModel(txtModel.getText().trim());
        e.setObservations(txtObservations.getText().trim());

        String error = existing == null ? controller.saveStolenEquipment(e)
                : controller.updateStolenEquipment(e);

        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registro guardado correctamente.");
        navigate(this, new StolenEquipmentListView());
    }

    private void handleDelete() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este registro?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        String error = controller.deleteStolenEquipment(existing.getId());
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
        navigate(this, new StolenEquipmentListView());
    }
}
