package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseFollowUpController;
import com.ucv.investigationcasesmanager.controller.EntityController;
import com.ucv.investigationcasesmanager.dto.FollowUpFormData;
import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.iterator.EntityIterator;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.EntityType;
import com.ucv.investigationcasesmanager.model.SystemEntity;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Vista de registro de seguimiento de casos.
 */
public class RegisterFollowUpView extends BaseView {
    private final Case currentCase;
    private final CaseFollowUpController followUpController;
    private final EntityController entityController;

    private JComboBox<String> cbActivities;
    private List<SystemEntity> activityTypes;
    private JTextField txtInvolvedPersons;
    private JTextField txtAmount;
    private JComboBox<String> cbStatus;
    private JComboBox<String> cbInvestigator;
    private List<User> investigators;

    public RegisterFollowUpView(Case caseObj, User investigator) {
        super("Cargando...", true, false);
        this.currentCase = caseObj;
        this.followUpController = new CaseFollowUpController();
        this.entityController = new EntityController();

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
        setupTitle("Seguimiento de casos", "Volver",
                e -> navigate(this, new CaseDetailView(currentCase, currentUser)));
        contentPanel.add(createFollowUpFormPanel(), BorderLayout.CENTER);
        contentPanel.add(createBottomPanel(createPrimaryButton("Registrar", e -> handleRegister())),
                BorderLayout.SOUTH);
    }

    private JComponent createFollowUpFormPanel() {
        JPanel card = createCard();
        JPanel form = createForm();

        activityTypes = new ArrayList<>();
        cbActivities = loadActivityCombo();
        cbStatus = new JComboBox<>(new String[] {"Seguimiento", "Cerrado", "Reabierto"});
        cbInvestigator = loadInvestigatorsCombo();
        txtInvolvedPersons = new JTextField();
        txtAmount = new JTextField("0.00");

        styleInput(cbActivities);
        styleInput(cbStatus);
        styleInput(cbInvestigator);
        styleInput(txtInvolvedPersons);
        styleInput(txtAmount);

        int row = 0;
        row = addField(form, row, "Actuaciones / Actividades Realizadas", cbActivities);
        row = addField(form, row, "Personas Involucradas", txtInvolvedPersons);
        row = addField(form, row, "Monto Expuesto", txtAmount);
        row = addField(form, row, "Status", cbStatus);
        addField(form, row, "Investigador", cbInvestigator);

        card.add(wrapInScroll(form), BorderLayout.CENTER);
        return card;
    }

    // PDyF: Iterator – puebla el combo de actividades usando EntityIterator para recorrer
    // las entidades sin exponer cómo están almacenadas internamente.
    private JComboBox<String> loadActivityCombo() {
        JComboBox<String> combo = new JComboBox<>();
        EntityIterator<SystemEntity> it =
                entityController.getIterator(EntityType.PERFORMED_ACTIVITY);
        SystemEntity entity = it.first();
        while (entity != null) {
            activityTypes.add(entity);
            combo.addItem(entity.getName());
            entity = it.next();
        }
        if (activityTypes.isEmpty()) {
            combo.addItem("Sin registros");
        }
        return combo;
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

        int actIdx = cbActivities.getSelectedIndex();
        if (activityTypes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay Actividades Realizadas disponibles. Registre al menos una en Entidades.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        data.activities = activityTypes.get(actIdx).getName();

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
