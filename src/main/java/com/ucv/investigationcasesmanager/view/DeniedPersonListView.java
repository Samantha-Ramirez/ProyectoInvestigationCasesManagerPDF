package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.DeniedFilesController;
import com.ucv.investigationcasesmanager.model.DeniedPerson;
import com.ucv.investigationcasesmanager.ui.SideMenuIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/*
 * Vista de lista de personal amonestado-desincorporado con botón Agregar y acciones de edición por
 * fila.
 */
public class DeniedPersonListView extends BaseView {
    private static final int EDIT_COLUMN = 1;
    private final DeniedFilesController controller;
    private final List<DeniedPerson> persons = new ArrayList<>();

    public DeniedPersonListView() {
        super("Marcación de personal amonestado-desincorporado", true, false);
        this.controller = new DeniedFilesController();
        initComponents();
    }

    @Override
    protected void initComponents() {
        setupTitle("Marcación de personal amonestado-desincorporado", "Agregar",
                e -> navigate(this, new DeniedPersonFormView(null)));

        JPanel card = createCard();
        card.add(createTable(new String[] {"CI – Nombre – Apellido – Empresa", "Acción"}),
                java.awt.BorderLayout.CENTER);

        table.getColumnModel().getColumn(EDIT_COLUMN).setMaxWidth(52);
        table.getColumnModel().getColumn(EDIT_COLUMN).setMinWidth(52);
        table.getColumnModel().getColumn(EDIT_COLUMN).setCellRenderer(new EditIconRenderer());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col == EDIT_COLUMN) {
                    openEditForm(row);
                }
            }
        });

        contentPanel.add(card, java.awt.BorderLayout.CENTER);
        loadData();
    }

    private void loadData() {
        persons.clear();
        tableModel.setRowCount(0);
        for (DeniedPerson p : controller.getAllDeniedPersons()) {
            persons.add(p);
            tableModel.addRow(
                    new Object[] {p.getCi() + " – " + p.getFirstName() + " " + p.getLastName()
                            + (p.getCompany() != null && !p.getCompany().isBlank()
                                    ? " – " + p.getCompany()
                                    : ""),
                            "✎"});
        }
    }

    private void openEditForm(int row) {
        if (row >= 0 && row < persons.size()) {
            navigate(this, new DeniedPersonFormView(persons.get(row)));
        }
    }

    private static class EditIconRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            JLabel lbl = new JLabel(SideMenuIcon.edit(), SwingConstants.CENTER);
            lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lbl.setOpaque(true);
            lbl.setBackground(isSelected ? new Color(242, 236, 247) : Color.WHITE);
            return lbl;
        }
    }
}
