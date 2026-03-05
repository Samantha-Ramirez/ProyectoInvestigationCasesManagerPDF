package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseFollowUpController;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.CaseFollowUp;
import com.ucv.investigationcasesmanager.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
 * Vista de detalle de caso - muestra la información del caso y el historial de seguimientos.
 */
public class CaseDetailView extends BaseView {
    private final Case currentCase;
    private final User currentInvestigator;
    private final CaseFollowUpController followUpController;
    private DefaultTableModel followUpTableModel;

    public CaseDetailView(Case caseObj, User investigator) {
        super("Detalle del Caso - Expediente: " + caseObj.getCaseNumber(), true, false);
        this.currentCase = caseObj;
        this.currentInvestigator = investigator;
        this.followUpController = new CaseFollowUpController();
        initComponents();
        loadFollowUps();
    }

    @Override
    protected void initComponents() {
        setupTitle("Información del caso", "Volver", e -> goBack());

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 13));
        tabs.addTab("Información general", createGeneralInfoPanel());
        tabs.addTab("Historial de seguimientos", createFollowUpPanel());
        contentPanel.add(tabs, BorderLayout.CENTER);

        if ("Investigador".equals(currentUser.getRole())) {
            JButton btnNewFollowUp = createPrimaryButton("Nuevo seguimiento", e -> {
                new RegisterFollowUpView(currentCase, currentInvestigator).setVisible(true);
                dispose();
            });
            contentPanel.add(createBottomPanel(btnNewFollowUp), BorderLayout.SOUTH);
        }

        // Botón de reabrir caso (solo para administradores)
        if ("Administrador".equals(currentUser.getRole())
                && "Cerrado".equals(currentCase.getStatus())) {
            JButton btnReopen = createPrimaryButton("Reabrir Caso", e -> {
                new ReopenCaseView().setVisible(true);
                dispose();
            });
            // btnReopen.setBackground(new Color(242, 242, 242));
            contentPanel.add(createBottomPanel(btnReopen), BorderLayout.SOUTH);

            // bottomPanel.add(btnReopen);
        }

        // contentPanel.add(bottomPanel, BorderLayout.SOUTH);

    }

    private void goBack() {
        if ("Administrador".equals(currentUser.getRole())) {
            navigate(this, new BoardView());
        } else {
            navigate(this, new InboxView());
        }
    }

    private JComponent createGeneralInfoPanel() {
        JPanel card = createCard();
        JPanel form = createForm();

        int row = 0;
        row = addField(form, row, "Expediente", new JLabel(currentCase.getCaseNumber()));
        row = addField(form, row, "Estatus", new JLabel(currentCase.getStatus()));
        row = addField(form, row, "Fecha de inicio", new JLabel(
                currentCase.getStartDate() != null ? currentCase.getStartDate() : "N/A"));
        row = addField(form, row, "Duración (días)",
                new JLabel(String.valueOf(currentCase.getDurationDays())));
        row = addField(form, row, "Móvil afectado", new JLabel(
                currentCase.getMobileAffected() != null ? currentCase.getMobileAffected() : "N/A"));
        row = addField(form, row, "Objetivo/Agraviado",
                new JLabel(
                        currentCase.getObjectiveVictim() != null ? currentCase.getObjectiveVictim()
                                : "N/A"));
        row = addField(form, row, "Incidencia",
                new JLabel(currentCase.getIncident() != null ? currentCase.getIncident() : "N/A"));

        JTextArea txtModus = createTextArea(3, 30, 80);
        txtModus.setEditable(false);
        txtModus.setText(currentCase.getModusOperandiDescription() != null
                ? currentCase.getModusOperandiDescription()
                : "N/A");
        row = addField(form, row, "Modus operandi", wrapInScroll(txtModus));

        JTextArea txtObs = createTextArea(3, 30, 70);
        txtObs.setEditable(false);
        txtObs.setText(
                currentCase.getObservations() != null ? currentCase.getObservations() : "N/A");
        addField(form, row, "Observaciones", wrapInScroll(txtObs));

        card.add(wrapInScroll(form), BorderLayout.CENTER);
        return card;
    }

    private JComponent createFollowUpPanel() {
        JPanel card = createCard();

        String[] columns =
                {"Fecha", "Actividades", "Personas", "Monto", "Estatus", "Observaciones"};
        followUpTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable followUpTable = new JTable(followUpTableModel);
        followUpTable.setRowHeight(32);
        followUpTable.getTableHeader().setReorderingAllowed(false);
        uiFactory.styleTable(followUpTable);

        card.add(new JScrollPane(followUpTable), BorderLayout.CENTER);
        return card;
    }

    private void loadFollowUps() {
        followUpTableModel.setRowCount(0);
        List<CaseFollowUp> followUps = followUpController.getFollowUps(currentCase.getId());

        if (followUps.isEmpty()) {
            followUpTableModel
                    .addRow(new Object[] {"No hay seguimientos registrados", "", "", "", "", ""});
            return;
        }

        for (CaseFollowUp f : followUps) {
            followUpTableModel.addRow(new Object[] {
                    f.getRegistrationDate().toString().substring(0, 10), f.getActivitiesPerformed(),
                    f.getInvolvedPersons() != null ? f.getInvolvedPersons() : "",
                    String.format("$%,.2f", f.getExposedAmount()), f.getStatus(),
                    f.getObservations() != null ? f.getObservations() : ""});
        }
    }
}
