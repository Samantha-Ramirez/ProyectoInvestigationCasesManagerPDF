package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseController;
import com.ucv.investigationcasesmanager.model.Case;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/*
 * Vista de cartelera para administradores - muestra todos los casos del sistema.
 */
public class BoardView extends BaseView {
    private static final int ACTION_COLUMN = 4;
    private final CaseController caseController;

    public BoardView() {
        super("Cartelera de casos", true);
        this.caseController = new CaseController();
        loadData(this.currentUser.getId());
    }

    @Override
    protected void initComponents() {
        setupTitle("Cartelera de casos", "Registrar", e -> navigate(this, new RegisterCaseView()));

        JPanel card = createCard();
        card.add(createActionBar("Vista general de casos por investigador", null),
                java.awt.BorderLayout.NORTH);
        card.add(createTable(new String[] {"Caso", "Investigador", "Tiempo", "Status", "Acción"}),
                java.awt.BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == ACTION_COLUMN) {
                    openCaseDetail(row);
                }
            }
        });

        contentPanel.add(card, java.awt.BorderLayout.CENTER);
    }

    private void openCaseDetail(int row) {
        String caseNumber = (String) tableModel.getValueAt(row, 0);
        Case c = caseController.findByCaseNumber(caseNumber);
        if (c != null) {
            new CaseDetailView(c, currentUser).setVisible(true);
            dispose();
        }
    }

    private void loadData(int userId) {
        List<Case> cases = caseController.getAllCases();
        for (Case c : cases) {
            tableModel.addRow(new Object[] {c.getCaseNumber(),
                    c.getInvestigatorName() != null ? c.getInvestigatorName() : "",
                    c.getTimeWithoutAttention(), c.getStatus(), "✎"});
        }
    }
}
