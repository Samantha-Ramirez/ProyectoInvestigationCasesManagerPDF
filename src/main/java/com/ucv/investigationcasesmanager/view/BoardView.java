package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.CaseController;
import com.ucv.investigationcasesmanager.model.Case;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * Vista de cartelera para administradores - muestra todos los casos del sistema.
 */
public class BoardView extends BaseView {
    private static final int ACTION_COLUMN = 4;
    private final CaseController caseController;
    private final List<Case> allCases = new ArrayList<>();

    public BoardView() {
        super("Cartelera de casos", true);
        this.caseController = new CaseController();
        loadData(this.currentUser.getId());
    }

    @Override
    protected void initComponents() {
        setupTitle("Cartelera de casos", "Registrar", e -> navigate(this, new RegisterCaseView()));

        JPanel card = createCard();

        // Bbotones de ordenamiento
        JButton btnNewest = createHeaderButton("Más reciente → Más antiguo", e -> sortTable(false));
        JButton btnOldest = createHeaderButton("Más antiguo → Más reciente", e -> sortTable(true));
        JPanel sortBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        sortBar.setOpaque(false);
        sortBar.add(btnOldest);
        sortBar.add(btnNewest);
        card.add(sortBar, java.awt.BorderLayout.NORTH);

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
        allCases.clear();
        allCases.addAll(caseController.getAllCases());
        populateTable(allCases);
    }

    // Ordenar la tabla por fecha de inicio (ascendente = más antiguo primero)
    private void sortTable(boolean ascending) {
        List<Case> sorted = new ArrayList<>(allCases);
        Comparator<Case> byDate = Comparator.comparing(
                c -> c.getStartDate() != null ? c.getStartDate() : "",
                String.CASE_INSENSITIVE_ORDER);
        sorted.sort(ascending ? byDate : byDate.reversed());
        populateTable(sorted);
    }

    private void populateTable(List<Case> cases) {
        tableModel.setRowCount(0);
        for (Case c : cases) {
            tableModel.addRow(new Object[] {c.getCaseNumber(),
                    c.getInvestigatorName() != null ? c.getInvestigatorName() : "",
                    c.getTimeWithoutAttention(), c.getStatus(), "✎"});
        }
    }
}

