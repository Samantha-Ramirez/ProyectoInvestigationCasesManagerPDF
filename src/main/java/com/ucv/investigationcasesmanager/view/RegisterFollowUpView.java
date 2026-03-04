package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseFollowUpController;
import com.ucv.investigationcasesmanager.dto.FollowUpFormData;
import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/*
 * Vista de registro de seguimiento - recopila datos del formulario y delega al
 * CaseFollowUpController toda la validación y persistencia.
 */
public class RegisterFollowUpView extends BaseView {
    private final Case currentCase;
    private final User currentInvestigator;
    private final CaseFollowUpController followUpController;

    private JTextArea txtActivities;
    private JTextArea txtInvolvedPersons;
    private JTextField txtAmount;
    private JComboBox<String> cbStatus;
    private JTextArea txtObservations;
    private JTextArea txtRecommendations;
    private JTextArea txtConclusions;

    public RegisterFollowUpView(Case caseObj, User investigator) {
        super("Cargando...", true, false);
        this.currentCase = caseObj;
        this.currentInvestigator = investigator;
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

        setTitle("Registrar Seguimiento - Expediente: " + caseObj.getCaseNumber());
        initComponents();
    }

    @Override
    protected void initComponents() {
        setupTitle("Seguimiento de caso", null, null);

        // Por qué: se agrupa el panel de info y el formulario en un panel
        // central para no solaparse con el título en BorderLayout.NORTH.
        JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
        centerPanel.setOpaque(false);
        centerPanel.add(createCaseInfoPanel(), BorderLayout.NORTH);
        centerPanel.add(createFollowUpFormPanel(), BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(
                createBottomPanel(createPrimaryButton("Registrar", e -> handleRegister())),
                BorderLayout.SOUTH);
    }

    private JPanel createCaseInfoPanel() {
        JPanel panel = createCard();
        panel.setLayout(new GridLayout(2, 4, 10, 6));

        panel.add(new JLabel("Expediente:"));
        panel.add(new JLabel(currentCase.getCaseNumber()));
        panel.add(new JLabel("Estatus actual:"));

        JLabel lblStatus = new JLabel(currentCase.getStatus());
        if ("Cerrado".equals(currentCase.getStatus())) {
            lblStatus.setForeground(Color.RED);
            lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
        }
        panel.add(lblStatus);

        panel.add(new JLabel("Investigador:"));
        panel.add(new JLabel(
                currentInvestigator.getFirstName() + " " + currentInvestigator.getLastName()));
        panel.add(new JLabel("Fecha:"));
        panel.add(new JLabel(LocalDateTime.now().toString().substring(0, 10)));

        return panel;
    }

    private JComponent createFollowUpFormPanel() {
        JPanel card = createCard();
        JPanel form = createForm();

        txtActivities = createTextArea(4, 30, 80);
        txtInvolvedPersons = createTextArea(3, 30, 60);
        txtAmount = new JTextField("0.00", 20);
        cbStatus = new JComboBox<>(new String[]{"En Seguimiento", "Cerrado", "Reabierto"});
        txtObservations = createTextArea(2, 30, 50);
        txtRecommendations = createTextArea(3, 30, 60);
        txtConclusions = createTextArea(3, 30, 60);

        styleInput(txtAmount);
        styleInput(cbStatus);

        int row = 0;
        row = addField(form, row, "Actividades realizadas", wrapInScroll(txtActivities));
        row = addField(form, row, "Personas involucradas", wrapInScroll(txtInvolvedPersons));
        row = addField(form, row, "Monto expuesto ($)", txtAmount);
        row = addField(form, row, "Cambiar estatus a", cbStatus);
        row = addField(form, row, "Observaciones", wrapInScroll(txtObservations));
        row = addField(form, row, "Recomendaciones", wrapInScroll(txtRecommendations));
        addField(form, row, "Conclusiones", wrapInScroll(txtConclusions));

        card.add(wrapInScroll(form), BorderLayout.CENTER);
        return card;
    }

    private void handleRegister() {
        FollowUpFormData data = new FollowUpFormData();
        data.activities = txtActivities.getText();
        data.involvedPersons = txtInvolvedPersons.getText();
        data.amountText = txtAmount.getText();
        data.status = (String) cbStatus.getSelectedItem();
        data.observations = txtObservations.getText();
        data.recommendations = txtRecommendations.getText();
        data.conclusions = txtConclusions.getText();
        data.caseNumber = currentCase.getCaseNumber();
        data.investigatorId = currentInvestigator.getId();

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
