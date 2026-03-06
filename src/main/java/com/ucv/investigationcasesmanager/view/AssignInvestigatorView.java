package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseAssignmentController;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.model.User;
import com.ucv.investigationcasesmanager.view.decorator.PanelBorderDecorator;
import com.ucv.investigationcasesmanager.view.decorator.PanelComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelConcreteComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelTitleDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignInvestigatorView extends BaseView {
    private final CaseAssignmentController assignmentController;
    private final Case currentCase;
    private JComboBox<String> cmbInvestigators;
    private List<User> investigators;
    private JPanel detailPanel;

    public AssignInvestigatorView(Case caseObj) {
        super("Asignar Investigador - Administrador", true, false);
        this.currentCase = caseObj;
        this.assignmentController = new CaseAssignmentController();
        getContentPane().setBackground(Color.WHITE);

        initComponents();
        loadInvestigators();
    }

    @Override
    protected void initComponents() {
        setupTitle("Asignar / Reasignar Investigador", "Volver",
                e -> navigate(this, new CaseDetailView(currentCase, currentUser)));

        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        // Panel principal con decoradores
        JPanel baseJPanel = new JPanel(new BorderLayout(10, 10));
        baseJPanel.setBackground(Color.WHITE);
        baseJPanel.setOpaque(true);
        PanelComponent baseComponent = new PanelConcreteComponent(baseJPanel);

        PanelBorderDecorator borderDecorator = new PanelBorderDecorator(15, 15, 15, 15);
        borderDecorator.setComponent(baseComponent);

        PanelTitleDecorator titleDecorator =
                new PanelTitleDecorator("Seleccione el nuevo investigador");
        titleDecorator.setComponent(borderDecorator);

        JPanel decoratedPanel = titleDecorator.build();
        decoratedPanel.setBackground(Color.WHITE);
        decoratedPanel.setOpaque(true);

        // Contenido interno
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(Color.WHITE);
        content.setOpaque(true);

        // Panel superior: selector de investigador
        content.add(createSelectorPanel(), BorderLayout.NORTH);

        // Panel central: detalles del caso
        detailPanel = createDetailPanel();
        content.add(detailPanel, BorderLayout.CENTER);

        decoratedPanel.add(content, BorderLayout.CENTER);
        contentPanel.add(decoratedPanel, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setOpaque(false);

        JButton btnAssign = createPrimaryButton("Asignar Investigador", e -> handleAssign());

        bottomPanel.add(btnAssign);

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSelectorPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

        panel.add(new JLabel("Investigador disponible:"));

        cmbInvestigators = new JComboBox<>();
        cmbInvestigators.setPreferredSize(new Dimension(300, 30));
        panel.add(cmbInvestigators);

        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createTitledBorder("Información del caso"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Número de expediente
        panel.add(new JLabel("Expediente:"), gbc);
        gbc.gridx = 1;
        JLabel lblCaseNumber = new JLabel(currentCase.getCaseNumber());
        panel.add(lblCaseNumber, gbc);

        // Estatus actual
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Estatus:"), gbc);
        gbc.gridx = 1;
        JLabel lblStatus = new JLabel(currentCase.getStatus());
        if ("Cerrado".equals(currentCase.getStatus())) {
            lblStatus.setForeground(Color.RED);
        }
        panel.add(lblStatus, gbc);

        // Investigador actual
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Investigador actual:"), gbc);
        gbc.gridx = 1;
        JLabel lblCurrentInvestigator = new JLabel(String.valueOf(currentCase.getInvestigatorId()));
        panel.add(lblCurrentInvestigator, gbc);

        return panel;
    }

    private void loadInvestigators() {
        investigators = assignmentController.getAvailableInvestigators();
        cmbInvestigators.removeAllItems();

        if (investigators == null || investigators.isEmpty()) {
            cmbInvestigators.addItem("No hay investigadores disponibles");
            cmbInvestigators.setEnabled(false);
        } else {
            for (User inv : investigators) {
                String display = inv.getFirstName() + " " + inv.getLastName()
                        + (inv.getId() == currentCase.getInvestigatorId() ? " (actual)" : "");
                cmbInvestigators.addItem(display);
            }
            cmbInvestigators.setEnabled(true);
        }
    }

    private void handleAssign() {
        if (investigators == null || investigators.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay investigadores disponibles para asignar",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedIndex = cmbInvestigators.getSelectedIndex();
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un investigador", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User selectedInvestigator = investigators.get(selectedIndex);

        // Si es el mismo investigador, preguntar si realmente quiere reasignar
        if (selectedInvestigator.getId() == currentCase.getInvestigatorId()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "El caso ya está asignado a este investigador. ¿Desea reasignarlo de todas formas?",
                    "Confirmar reasignación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        String error = assignmentController.reassignCase(currentCase.getId(),
                selectedInvestigator.getId(), currentUser);

        if (error == null) {
            JOptionPane.showMessageDialog(this,
                    "Caso reasignado exitosamente.\nNuevo investigador: "
                            + selectedInvestigator.getFirstName() + " "
                            + selectedInvestigator.getLastName(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            navigate(this, new CaseDetailView(currentCase, currentUser));
        } else {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
