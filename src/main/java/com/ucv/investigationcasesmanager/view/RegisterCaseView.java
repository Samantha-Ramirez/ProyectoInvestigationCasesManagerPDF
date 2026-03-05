package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseController;
import com.ucv.investigationcasesmanager.controller.EntityController;
import com.ucv.investigationcasesmanager.dto.CaseFormData;
import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.iterator.EntityIterator;
import com.ucv.investigationcasesmanager.model.EntityType;
import com.ucv.investigationcasesmanager.model.SystemEntity;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Vista de registro de casos - recopila datos del formulario y delega al CaseController.
 * PDyF: Iterator – usa EntityIterator para poblar los combos de entidades sin exponer la lista
 * interna (UC13).
 */
public class RegisterCaseView extends BaseView {
    private final CaseController caseController;
    private final EntityController entityController;
    private JTextField txtCaseNumber, txtMobile, txtVictim, txtIncident, txtDuration;
    private JTextArea txtModusOperandi, txtSupportArea, txtDetection, txtDiagnosis, txtConclusions,
            txtObservations, txtSupport;
    private JComboBox<String> cbCaseType, cbInvestigator, cbIrregularityType, cbSubtype, cbAction;
    private List<User> investigators;
    private List<SystemEntity> irregularityTypes;
    private List<SystemEntity> irregularitySubtypes;
    private List<SystemEntity> performedProcesses;

    public RegisterCaseView() {
        // Por qué: se pospone initComponents() para que caseController esté
        // asignado antes de que loadInvestigatorsCombo() lo invoque.
        super("Registro de casos", true, false);
        this.caseController = new CaseController();
        this.entityController = new EntityController();
        initComponents();
    }

    @Override
    protected void initComponents() {
        setupTitle("Registro de casos", null, null);
        contentPanel.add(createFormPanel(), BorderLayout.CENTER);
        contentPanel.add(createBottomPanel(createPrimaryButton("Registrar", e -> handleRegister())),
                BorderLayout.SOUTH);
    }

    private JComponent createFormPanel() {
        JPanel card = createCard();
        JPanel form = createForm();

        txtCaseNumber = new JTextField();
        cbCaseType = new JComboBox<>(new String[] {"Gestión", "Reclamo", "Caso"});
        cbInvestigator = loadInvestigatorsCombo();
        txtMobile = new JTextField();
        txtVictim = new JTextField();
        txtIncident = new JTextField();
        txtDuration = new JTextField();
        irregularityTypes = new ArrayList<>();
        irregularitySubtypes = new ArrayList<>();
        performedProcesses = new ArrayList<>();
        cbIrregularityType = loadEntityCombo(EntityType.IRREGULARITY_TYPE, irregularityTypes);
        cbSubtype = loadEntityCombo(EntityType.IRREGULARITY_SUBTYPE, irregularitySubtypes);
        cbAction = loadEntityCombo(EntityType.PERFORMED_PROCESS, performedProcesses);
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

    // PDyF: Iterator – puebla un combo usando EntityIterator para recorrer las entidades
    // sin exponer cómo están almacenadas internamente (UC13).
    private JComboBox<String> loadEntityCombo(EntityType type, List<SystemEntity> target) {
        JComboBox<String> combo = new JComboBox<>();
        EntityIterator<SystemEntity> it = entityController.getIterator(type);
        while (it.hasNext()) {
            SystemEntity entity = it.next();
            target.add(entity);
            combo.addItem(entity.getName());
        }
        if (target.isEmpty()) {
            combo.addItem("Sin registros");
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

        int irrTypeIdx = cbIrregularityType.getSelectedIndex();
        data.irregularityTypeId = (!irregularityTypes.isEmpty() && irrTypeIdx >= 0)
                ? irregularityTypes.get(irrTypeIdx).getId()
                : irrTypeIdx + 1;

        int irrSubIdx = cbSubtype.getSelectedIndex();
        data.irregularitySubtypeId = (!irregularitySubtypes.isEmpty() && irrSubIdx >= 0)
                ? irregularitySubtypes.get(irrSubIdx).getId()
                : irrSubIdx + 1;

        int actionIdx = cbAction.getSelectedIndex();
        data.actionPerformedId = (!performedProcesses.isEmpty() && actionIdx >= 0)
                ? performedProcesses.get(actionIdx).getId()
                : actionIdx + 1;

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
