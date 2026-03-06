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
 * Vista de detalle de caso - muestra información del caso e historial de seguimientos.
 */
public class CaseDetailView extends BaseView {
    private final Case currentCase;
    private final User currentInvestigator;
    private final CaseFollowUpController followUpController;
    private DefaultTableModel followUpTableModel;

    public CaseDetailView(Case caseObj, User investigator) {
        super("Expediente: " + caseObj.getCaseNumber(), true, false);
        this.currentCase = caseObj;
        this.currentInvestigator = investigator;
        this.followUpController = new CaseFollowUpController();
        initComponents();
        loadFollowUps();
    }

    @Override
    protected void initComponents() {
        setupTitle("Información de Caso", "Volver", e -> goBack());

        JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
        centerPanel.setOpaque(false);
        centerPanel.add(createCaseInfoPanel(), BorderLayout.NORTH);
        centerPanel.add(createFollowUpSection(), BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void goBack() {
        if ("Administrador".equals(currentUser.getRole())) {
            navigate(this, new BoardView());
        } else {
            navigate(this, new InboxView());
        }
    }

    private JComponent createCaseInfoPanel() {
        JPanel card = createCard();
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        int row = 0;
        row = addField(form, row, "Expediente", new JLabel(currentCase.getCaseNumber()));
        row = addField(form, row, "Estatus", buildStatusLabel(currentCase.getStatus()));
        row = addField(form, row, "Fecha de inicio", new JLabel(
                currentCase.getStartDate() != null ? currentCase.getStartDate() : "N/A"));
        row = addField(form, row, "Duración (días)",
                new JLabel(String.valueOf(currentCase.getDurationDays())));
        row = addField(form, row, "Móvil afectado", new JLabel(
                currentCase.getMobileAffected() != null ? currentCase.getMobileAffected() : "N/A"));
        addField(form, row, "Objetivo / Agraviado",
                new JLabel(
                        currentCase.getObjectiveVictim() != null ? currentCase.getObjectiveVictim()
                                : "N/A"));
        card.add(form, BorderLayout.CENTER);
        return card;
    }

    private JLabel buildStatusLabel(String status) {
        JLabel lbl = new JLabel(status != null ? status : "N/A");
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        if ("Cerrado".equals(status)) {
            lbl.setForeground(new Color(180, 30, 30));
        } else if ("Abierto".equals(status)) {
            lbl.setForeground(new Color(0, 153, 76));
        } else {
            lbl.setForeground(new Color(125, 21, 175));
        }
        return lbl;
    }

    private JComponent createFollowUpSection() {
        JPanel card = createCard();

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel lblTitle = new JLabel("Historial de seguimientos");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        header.add(lblTitle, BorderLayout.WEST);

        if ("Investigador".equals(currentUser.getRole())) {
            JButton btnNew = createHeaderButton("Registrar", e -> openNewFollowUp());
            header.add(btnNew, BorderLayout.EAST);
        }

        if (("Administrador".equals(currentUser.getRole())
                || "Investigador".equals(currentUser.getRole()))
                && "Cerrado".equals(currentCase.getStatus())) {

            JButton btnReopen = createPrimaryButton("Reabrir Caso", e -> openReopenView());

            header.add(btnReopen, BorderLayout.EAST);
        }

        card.add(header, BorderLayout.NORTH);
        card.add(createFollowUpTable(), BorderLayout.CENTER);
        return card;
    }

    private void openNewFollowUp() {
        new RegisterFollowUpView(currentCase, currentInvestigator).setVisible(true);
        dispose();
    }

    private JScrollPane createFollowUpTable() {
        String[] columns = {"Actividades realizadas", "Estatus", "Fecha"};
        followUpTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable followUpTable = new JTable(followUpTableModel);
        followUpTable.setRowHeight(36);
        followUpTable.getTableHeader().setReorderingAllowed(false);
        uiFactory.styleTable(followUpTable);

        followUpTable.getColumnModel().getColumn(1).setCellRenderer(createStatusBadgeRenderer());
        followUpTable.getColumnModel().getColumn(2).setMaxWidth(100);
        followUpTable.getColumnModel().getColumn(2).setMinWidth(80);

        JScrollPane scroll = new JScrollPane(followUpTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(236, 236, 236)));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private void loadFollowUps() {
        followUpTableModel.setRowCount(0);
        List<CaseFollowUp> followUps = followUpController.getFollowUps(currentCase.getId());

        if (followUps.isEmpty()) {
            followUpTableModel.addRow(new Object[] {"No hay seguimientos registrados.", "", ""});
            return;
        }

        for (CaseFollowUp f : followUps) {
            String fecha = f.getRegistrationDate() != null
                    ? f.getRegistrationDate().toString().substring(0, 10)
                    : "";
            String activities =
                    f.getActivitiesPerformed() != null ? f.getActivitiesPerformed() : "";
            followUpTableModel.addRow(new Object[] {activities, f.getStatus(), fecha});
        }
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel.setOpaque(false);

        if ("Administrador".equals(currentUser.getRole())
                && "Cerrado".equals(currentCase.getStatus())) {
            JButton btnReopen = createPrimaryButton("Reabrir Caso", e -> openReopenView());
            btnReopen.setBackground(Color.WHITE); // ← FONDO BLANCO
            btnReopen.setForeground(new Color(125, 21, 175));
            btnReopen.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(btnReopen);
        }
        if ("Administrador".equals(currentUser.getRole())) {
            JButton btnAssign = createPrimaryButton("Asignar Investigador", e -> openAssignView());
            btnAssign.setBackground(Color.WHITE);
            btnAssign.setForeground(new Color(125, 21, 175));
            btnAssign.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(btnAssign);
        }

        return panel;
    }

    private void openReopenView() {
        new ReopenCaseView(currentCase).setVisible(true);
        dispose();
    }

    private void openAssignView() {
        // Verificar que el caso no sea null
        if (currentCase == null) {
            JOptionPane.showMessageDialog(this, "Error: Caso no válido", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("Cerrado".equals(currentCase.getStatus())) {
            JOptionPane.showMessageDialog(this,
                    "No se puede reasignar un investigador a un caso cerrado.\n"
                            + "Debe reabrir el caso primero.",
                    "Caso Cerrado", JOptionPane.WARNING_MESSAGE);
            return; // No abre la vista
        }

        // Abrir la vista de asignación
        AssignInvestigatorView assignView = new AssignInvestigatorView(currentCase);
        assignView.setVisible(true);
        dispose(); // Cerrar la vista actual
    }

}
