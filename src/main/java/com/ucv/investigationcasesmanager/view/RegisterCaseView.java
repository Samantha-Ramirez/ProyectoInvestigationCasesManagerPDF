package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseController;
import com.ucv.investigationcasesmanager.dto.CaseFormData;
import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Case registration view - collects form data and delegates to CaseController.
 */
public class RegisterCaseView extends BaseView {
    private final CaseController caseController;
    private JTextField txtCaseNumber, txtMobile, txtVictim, txtIncident, txtDuration;
    private JTextArea txtModusOperandi, txtSupportArea, txtDetection, txtDiagnosis,
            txtConclusions, txtObservations, txtSupport;
    private JComboBox<String> cbCaseType, cbInvestigator, cbIrregularityType, cbSubtype, cbAction;
    private List<User> investigators;

    public RegisterCaseView() {
        super("Registro de casos", true);
        this.caseController = new CaseController();
    }

    @Override
    protected void initComponents() {
        setupTitle("Registro de casos", null, null);
        contentPanel.add(createFormPanel(), BorderLayout.CENTER);
        contentPanel.add(
                createBottomPanel(createPrimaryButton("Registrar", e -> handleRegister())),
                BorderLayout.SOUTH);
    }

    private JComponent createFormPanel() {
        JPanel card = createCard();
        JPanel form = createForm();

        txtCaseNumber = new JTextField();
        cbCaseType = new JComboBox<>(new String[]{"Gestión", "Reclamo", "Caso"});
        cbInvestigator = loadInvestigatorsCombo();
        txtMobile = new JTextField();
        txtVictim = new JTextField();
        txtIncident = new JTextField();
        txtDuration = new JTextField();
        cbIrregularityType = new JComboBox<>(new String[]{"Tipo Irregularidad 1", "Tipo 2"});
        cbSubtype = new JComboBox<>(new String[]{"Subtipo A", "Subtipo B"});
        cbAction = new JComboBox<>(new String[]{"Acción Realizada 1", "Acción 2"});
        txtModusOperandi = createTextArea(3, 20, 80);
        txtSupportArea = createTextArea(2, 20, 60);
        txtDetection = createTextArea(2, 20, 60);
        txtDiagnosis = createTextArea(3, 20, 80);
        txtConclusions = createTextArea(3, 20, 80);
        txtObservations = createTextArea(2, 20, 60);
        txtSupport = createTextArea(2, 20, 60);

        styleInput(txtCaseNumber);
        styleInput(cbCaseType);
        styleInput(cbInvestigator);
        styleInput(txtMobile);
        styleInput(txtVictim);
        styleInput(txtIncident);
        styleInput(txtDuration);
        styleInput(cbIrregularityType);
        styleInput(cbSubtype);
        styleInput(cbAction);

        int row = 0;
        row = addField(form, row, "Nro. expediente", txtCaseNumber);
        row = addField(form, row, "Tipo de caso", cbCaseType);
        row = addField(form, row, "Investigador", cbInvestigator);
        row = addField(form, row, "Móvil afectado", txtMobile);
        row = addField(form, row, "Objetivo/Agraviado", txtVictim);
        row = addField(form, row, "Incidencia", txtIncident);
        row = addField(form, row, "Duración (días)", txtDuration);
        row = addField(form, row, "Tipo irregularidad", cbIrregularityType);
        row = addField(form, row, "Subtipo", cbSubtype);
        row = addField(form, row, "Acción realizada", cbAction);
        row = addField(form, row, "Modus operandi", wrapInScroll(txtModusOperandi));
        row = addField(form, row, "Área de apoyo", wrapInScroll(txtSupportArea));
        row = addField(form, row, "Detección", wrapInScroll(txtDetection));
        row = addField(form, row, "Diagnóstico", wrapInScroll(txtDiagnosis));
        row = addField(form, row, "Conclusiones", wrapInScroll(txtConclusions));
        row = addField(form, row, "Observaciones", wrapInScroll(txtObservations));
        addField(form, row, "Soporte", wrapInScroll(txtSupport));

        card.add(wrapInScroll(form), BorderLayout.CENTER);
        return card;
    }

    private JComboBox<String> loadInvestigatorsCombo() {
        investigators = caseController.getInvestigators();
        JComboBox<String> combo = new JComboBox<>();

        if (investigators.isEmpty()) {
            combo.addItem("Sin investigadores");
        } else {
            for (User inv : investigators) {
                combo.addItem(inv.getFirstName() + " " + inv.getLastName());
            }
        }

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
        CaseFormData data = new CaseFormData();
        data.caseNumber = txtCaseNumber.getText();
        data.mobileAffected = txtMobile.getText();
        data.objectiveVictim = txtVictim.getText();
        data.incident = txtIncident.getText();
        data.duration = txtDuration.getText();
        data.modusOperandiDescription = txtModusOperandi.getText();
        data.supportArea = txtSupportArea.getText();
        data.detectionOrigin = txtDetection.getText();
        data.fraudDiagnosis = txtDiagnosis.getText();
        data.conclusionsRecommendations = txtConclusions.getText();
        data.observations = txtObservations.getText();
        data.support = txtSupport.getText();
        data.caseTypeId = cbCaseType.getSelectedIndex() + 1;
        data.irregularityTypeId = cbIrregularityType.getSelectedIndex() + 1;
        data.irregularitySubtypeId = cbSubtype.getSelectedIndex() + 1;
        data.actionPerformedId = cbAction.getSelectedIndex() + 1;

        int selectedIdx = cbInvestigator.getSelectedIndex();
        if (selectedIdx >= 0 && selectedIdx < investigators.size()) {
            data.investigatorId = investigators.get(selectedIdx).getId();
        }

        String error = caseController.registerCase(data, currentUser);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Caso registrado.");
        navigate(this, StartupViewFactory.getStartView(currentUser.getRole()));
    }
}
