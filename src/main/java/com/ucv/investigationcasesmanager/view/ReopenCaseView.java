package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseReopenController;
import com.ucv.investigationcasesmanager.model.Case;
import com.ucv.investigationcasesmanager.view.decorator.PanelBorderDecorator;
import com.ucv.investigationcasesmanager.view.decorator.PanelComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelConcreteComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelTitleDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReopenCaseView extends BaseView {
    private final CaseReopenController reopenController;
    private JComboBox<String> cmbClosedCases;
    private JTextArea txtSupport;
    private JPanel detailPanel;
    private List<Case> closedCases;
    private Case selectedCase;

    public ReopenCaseView() {
        super("Reabrir Caso", true, false);
        this.reopenController = new CaseReopenController();
        getContentPane().setBackground(Color.WHITE);

        initComponents();
        loadClosedCases();
    }

    @Override
    protected void initComponents() {
        setupTitle("Reabrir Caso Cerrado", "Volver", e -> navigate(this, new BoardView()));


        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        JPanel baseJPanel = new JPanel(new BorderLayout(10, 10));
        baseJPanel.setBackground(Color.WHITE);
        baseJPanel.setOpaque(true);
        PanelComponent baseComponent = new PanelConcreteComponent(baseJPanel);

        PanelBorderDecorator borderDecorator = new PanelBorderDecorator(15, 15, 15, 15);
        borderDecorator.setComponent(baseComponent);

        PanelTitleDecorator titleDecorator =
                new PanelTitleDecorator("Seleccione un caso para reabrir");
        titleDecorator.setComponent(borderDecorator);

        JPanel decoratedPanel = titleDecorator.build();
        decoratedPanel.setBackground(Color.WHITE);
        decoratedPanel.setOpaque(true);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(Color.WHITE);
        content.setOpaque(true);

        content.add(createCaseSelectorPanel(), BorderLayout.NORTH);

        detailPanel = createDetailPanel();
        detailPanel.setVisible(false);
        content.add(detailPanel, BorderLayout.CENTER);

        decoratedPanel.add(content, BorderLayout.CENTER);

        contentPanel.add(decoratedPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        bottomPanel.setOpaque(false);

        // Botón Reabrir Caso
        JButton btnReopen = createPrimaryButton("Reabrir Caso", e -> handleReopen());

        bottomPanel.add(btnReopen);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createCaseSelectorPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);

        panel.add(new JLabel("Caso cerrado:"));

        cmbClosedCases = new JComboBox<>();
        cmbClosedCases.setPreferredSize(new Dimension(300, 30));
        cmbClosedCases.addActionListener(e -> onCaseSelected());
        panel.add(cmbClosedCases);

        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
        panel.setBorder(BorderFactory.createTitledBorder("Detalles del caso"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Expediente
        panel.add(new JLabel("Expediente:"), gbc);
        gbc.gridx = 1;
        JLabel lblCaseNumber = new JLabel();
        panel.add(lblCaseNumber, gbc);

        // Fecha inicio
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Fecha inicio:"), gbc);
        gbc.gridx = 1;
        JLabel lblStartDate = new JLabel();
        panel.add(lblStartDate, gbc);

        // Móvil afectado
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Móvil afectado:"), gbc);
        gbc.gridx = 1;
        JLabel lblMobile = new JLabel();
        panel.add(lblMobile, gbc);

        // Objetivo/Agraviado
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Objetivo/Agraviado:"), gbc);
        gbc.gridx = 1;
        JLabel lblVictim = new JLabel();
        panel.add(lblVictim, gbc);

        // Campo editable: Soporte
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Soporte (editable):"), gbc);
        gbc.gridx = 1;
        txtSupport = new JTextArea(3, 30);
        txtSupport.setLineWrap(true);
        txtSupport.setWrapStyleWord(true);
        styleInput(txtSupport);

        JScrollPane scrollSupport = new JScrollPane(txtSupport);
        scrollSupport.setPreferredSize(new Dimension(300, 60));
        scrollSupport.getViewport().setBackground(Color.WHITE);
        panel.add(scrollSupport, gbc);

        // Guardar referencias
        panel.putClientProperty("lblCaseNumber", lblCaseNumber);
        panel.putClientProperty("lblStartDate", lblStartDate);
        panel.putClientProperty("lblMobile", lblMobile);
        panel.putClientProperty("lblVictim", lblVictim);

        return panel;
    }

    private void loadClosedCases() {
        closedCases = reopenController.getClosedCases();
        cmbClosedCases.removeAllItems();

        if (closedCases == null || closedCases.isEmpty()) {
            cmbClosedCases.addItem("No hay casos cerrados");
            cmbClosedCases.setEnabled(false);
        } else {
            for (Case c : closedCases) {
                cmbClosedCases.addItem(c.getCaseNumber() + " - " + c.getStatus());
            }
            cmbClosedCases.setEnabled(true);
        }
    }

    private void onCaseSelected() {
        int index = cmbClosedCases.getSelectedIndex();
        if (index >= 0 && closedCases != null && index < closedCases.size()) {
            selectedCase = closedCases.get(index);
            updateDetailPanel();
            detailPanel.setVisible(true);
        }
    }

    private void updateDetailPanel() {
        if (selectedCase == null)
            return;

        JLabel lblCaseNumber = (JLabel) detailPanel.getClientProperty("lblCaseNumber");
        JLabel lblStartDate = (JLabel) detailPanel.getClientProperty("lblStartDate");
        JLabel lblMobile = (JLabel) detailPanel.getClientProperty("lblMobile");
        JLabel lblVictim = (JLabel) detailPanel.getClientProperty("lblVictim");

        if (lblCaseNumber != null)
            lblCaseNumber.setText(selectedCase.getCaseNumber());
        if (lblStartDate != null)
            lblStartDate.setText(
                    selectedCase.getStartDate() != null ? selectedCase.getStartDate() : "N/A");
        if (lblMobile != null)
            lblMobile.setText(
                    selectedCase.getMobileAffected() != null ? selectedCase.getMobileAffected()
                            : "N/A");
        if (lblVictim != null)
            lblVictim.setText(
                    selectedCase.getObjectiveVictim() != null ? selectedCase.getObjectiveVictim()
                            : "N/A");

        txtSupport.setText(selectedCase.getSupport() != null ? selectedCase.getSupport() : "");
    }

    private void handleReopen() {
        if (selectedCase == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un caso para reabrir", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String support = txtSupport.getText().trim();
        if (support.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Soporte no puede estar vacío",
                    "Campo requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String error = reopenController.reopenCase(selectedCase.getId(), support, currentUser);
        if (error == null) {
            JOptionPane.showMessageDialog(this,
                    "Caso reabierto exitosamente.\nEstatus actualizado a: Reabierto", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            navigate(this, new BoardView());
        } else {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
