package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseFollowUpController;
import com.ucv.investigationcasesmanager.dto.FollowUpFormData;
import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Vista de registro de seguimiento de casos (UC05) - diseño alineado con el wireframe:
 * campos Actividades Realizadas, Personas Involucradas, Monto Expuesto, Status e Investigador.
 */
public class RegisterFollowUpView extends BaseView {
    private final Case currentCase;
    private final CaseFollowUpController followUpController;

    private JTextArea txtActivities;
    private JTextField txtInvolvedPersons;
    private JTextField txtAmount;
    private JComboBox<String> cbStatus;
    private JComboBox<String> cbInvestigator;
    private List<User> investigators;

    public RegisterFollowUpView(Case caseObj, User investigator) {
        super("Cargando...", true, false);
        this.currentCase = caseObj;
        this.followUpController = new CaseFollowUpController();

        if (currentCase == null) {
            JOptionPane.showMessageDialog(this, "Error: Caso no válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        if ("Cerrado".equals(currentCase.getStatus())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede registrar seguimiento en un caso cerrado.", "Caso Cerrado",
                    JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        setTitle("Seguimiento de casos - Expediente: " + caseObj.getCaseNumber());
        initComponents();
    }

    @Override
    protected void initComponents() {
        setupTitle("Seguimiento de casos", null, null);
        contentPanel.add(createFollowUpFormPanel(), BorderLayout.CENTER);
        contentPanel.add(
                createBottomPanel(createPrimaryButton("Registrar", e -> handleRegister())),
                BorderLayout.SOUTH);
    }

    private JComponent createFollowUpFormPanel() {
        JPanel card = createCard();
        JPanel form = createForm();

        cbStatus = new JComboBox<>(new String[] {"Seguimiento", "Cerrado", "Reabierto"});
        cbInvestigator = loadInvestigatorsCombo();
        txtActivities = createTextArea(4, 30, 90);
        txtInvolvedPersons = new JTextField();
        txtAmount = new JTextField("0.00");

        styleInput(cbStatus);
        styleInput(cbInvestigator);
        styleInput(txtInvolvedPersons);
        styleInput(txtAmount);

        int row = 0;
        row = addField(form, row, "Actividades Realizadas", wrapInScroll(txtActivities));
        row = addField(form, row, "Personas Involucradas", txtInvolvedPersons);
        row = addField(form, row, "Monto Expuesto", txtAmount);
        row = addField(form, row, "Status", cbStatus);
        addField(form, row, "Investigador", cbInvestigator);

        card.add(wrapInScroll(form), BorderLayout.CENTER);
        return card;
    }

    private JComboBox<String> loadInvestigatorsCombo() {
        investigators = followUpController.getInvestigators();
        JComboBox<String> combo = new JComboBox<>();

        if (investigators.isEmpty()) {
            combo.addItem("Sin investigadores");
        } else {
            for (User inv : investigators) {
                combo.addItem(inv.getFirstName() + " " + inv.getLastName());
            }
        }

        // Por qué: si el usuario activo es Investigador, se preselecciona su propio registro
        // y se deshabilita el combo para que no pueda asignarse a otro.
        if ("Investigador".equalsIgnoreCase(currentUser.getRole())) {
            for (int i = 0; i < investigators.size(); i++) {
                if (investigators.get(i).getId() == currentUser.getId()) {
                    combo.setSelectedIndex(i);
                    break;
                }
            }
            combo.setEnabled(false);
        }

        return combo;
    }

    private void handleRegister() {
        FollowUpFormData data = new FollowUpFormData();
        data.activities = txtActivities.getText();
        data.involvedPersons = txtInvolvedPersons.getText();
        data.amountText = txtAmount.getText();
        data.status = (String) cbStatus.getSelectedItem();
        data.caseNumber = currentCase.getCaseNumber();

        int selectedIdx = cbInvestigator.getSelectedIndex();
        data.investigatorId = (selectedIdx >= 0 && selectedIdx < investigators.size())
                ? investigators.get(selectedIdx).getId()
                : currentUser.getId();

        String error = followUpController.registerFollowUp(data);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Seguimiento registrado exitosamente.\nEstatus actualizado a: " + data.status,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        navigate(this, StartupViewFactory.getStartView(currentUser.getRole()));
    }
}
