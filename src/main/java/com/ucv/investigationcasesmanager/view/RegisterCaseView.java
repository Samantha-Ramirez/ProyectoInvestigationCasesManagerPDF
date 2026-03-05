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
    private JTextField txtCaseNumber, txtStartDate, txtDays, txtMonth;
    private JTextField txtMobile, txtVictim, txtIncident, txtDuration;
    private JTextArea txtModusOperandi, txtSupportArea, txtDetection, txtDiagnosis,
            txtConclusions, txtRecommendations, txtObservations, txtSupport;
    private JComboBox<String> cbCaseType, cbInvestigator, cbIrregularityType, cbSubtype;
    private List<User> investigators;
    private List<SystemEntity> caseTypes;
    private List<SystemEntity> irregularityTypes;
    private List<SystemEntity> irregularitySubtypes;

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
        txtStartDate = new JTextField();
        txtDays = new JTextField();
        txtMonth = new JTextField();
        txtMobile = new JTextField();
        txtVictim = new JTextField();
        txtIncident = new JTextField();
        txtDuration = new JTextField();

        investigators = new ArrayList<>();
        caseTypes = new ArrayList<>();
        irregularityTypes = new ArrayList<>();
        irregularitySubtypes = new ArrayList<>();

        cbInvestigator = loadInvestigatorsCombo();
        cbCaseType = loadEntityCombo(EntityType.CASE_TYPE, caseTypes);
        cbIrregularityType = loadEntityCombo(EntityType.IRREGULARITY_TYPE, irregularityTypes);
        cbSubtype = loadEntityCombo(EntityType.IRREGULARITY_SUBTYPE, irregularitySubtypes);

        txtModusOperandi = createTextArea(3, 20, 80);
        txtSupportArea = createTextArea(2, 20, 60);
        txtDetection = createTextArea(2, 20, 60);
        txtDiagnosis = createTextArea(3, 20, 80);
        txtConclusions = createTextArea(3, 20, 80);
        txtRecommendations = createTextArea(3, 20, 80);
        txtObservations = createTextArea(2, 20, 60);
        txtSupport = createTextArea(2, 20, 60);

        styleInput(txtCaseNumber);
        styleInput(txtStartDate);
        styleInput(txtDays);
        styleInput(txtMonth);
        styleInput(cbInvestigator);
        styleInput(txtMobile);
        styleInput(cbCaseType);
        styleInput(cbIrregularityType);
        styleInput(cbSubtype);
        styleInput(txtVictim);
        styleInput(txtIncident);
        styleInput(txtDuration);

        int row = 0;
        row = addField(form, row, "Nro. expediente", txtCaseNumber);
        row = addField(form, row, "Investigador", cbInvestigator);
        row = addField(form, row, "Fecha de inicio", txtStartDate);
        row = addField(form, row, "Días", txtDays);
        row = addField(form, row, "Mes", txtMonth);
        row = addField(form, row, "Móvil afectado", txtMobile);
        row = addField(form, row, "Tipos de Casos", cbCaseType);
        row = addField(form, row, "Tipo irregularidad", cbIrregularityType);
        row = addField(form, row, "Subtipo irregularidad", cbSubtype);
        row = addField(form, row, "Objetivo/Agraviado", txtVictim);
        row = addField(form, row, "Incidencia", txtIncident);
        row = addField(form, row, "Duración (días)", txtDuration);
        row = addField(form, row, "Descripción Modus Operandi", wrapInScroll(txtModusOperandi));
        row = addField(form, row, "Área Apoyo a Resolver", wrapInScroll(txtSupportArea));
        row = addField(form, row, "Detección / Procedencia del Caso", wrapInScroll(txtDetection));
        row = addField(form, row, "Diagnóstico / Detalle de Fraude", wrapInScroll(txtDiagnosis));
        row = addField(form, row, "Observaciones", wrapInScroll(txtObservations));
        row = addField(form, row, "Conclusiones", wrapInScroll(txtConclusions));
        row = addField(form, row, "Recomendaciones", wrapInScroll(txtRecommendations));
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
        data.startDate = txtStartDate.getText().trim();
        data.mobileAffected = txtMobile.getText();
        data.objectiveVictim = txtVictim.getText();
        data.incident = txtIncident.getText();
        data.duration = txtDuration.getText();
        data.modusOperandiDescription = txtModusOperandi.getText();
        data.supportArea = txtSupportArea.getText();
        data.detectionOrigin = txtDetection.getText();
        data.fraudDiagnosis = txtDiagnosis.getText();
        data.conclusions = txtConclusions.getText();
        data.recommendations = txtRecommendations.getText();
        data.observations = txtObservations.getText();
        data.support = txtSupport.getText();

        // Por qué: Días y Mes son opcionales; se usa 0 si el usuario los deja en blanco,
        // y el mediador los auto-completa si es necesario.
        try {
            String daysText = txtDays.getText().trim();
            data.daysElapsed = daysText.isEmpty() ? 0 : Integer.parseInt(daysText);
        } catch (NumberFormatException e) {
            data.daysElapsed = 0;
        }
        try {
            String monthText = txtMonth.getText().trim();
            data.month = monthText.isEmpty() ? 0 : Integer.parseInt(monthText);
        } catch (NumberFormatException e) {
            data.month = 0;
        }

        int caseTypeIdx = cbCaseType.getSelectedIndex();
        // Por qué: si no hay tipos de caso registrados se rechaza el formulario con mensaje claro,
        // evitando guardar un caseTypeId de 0 que violaría la intención del campo.
        if (caseTypes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay Tipos de Casos disponibles. Registre al menos uno en Entidades.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        data.caseTypeId = caseTypes.get(caseTypeIdx).getId();

        int irrTypeIdx = cbIrregularityType.getSelectedIndex();
        data.irregularityTypeId = (!irregularityTypes.isEmpty() && irrTypeIdx >= 0)
                ? irregularityTypes.get(irrTypeIdx).getId()
                : irrTypeIdx + 1;

        int irrSubIdx = cbSubtype.getSelectedIndex();
        data.irregularitySubtypeId = (!irregularitySubtypes.isEmpty() && irrSubIdx >= 0)
                ? irregularitySubtypes.get(irrSubIdx).getId()
                : irrSubIdx + 1;

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
