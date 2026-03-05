package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.DeniedFilesController;
import com.ucv.investigationcasesmanager.model.StolenEquipment;
import com.ucv.investigationcasesmanager.ui.SideMenuIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/*
 * UC10 – Vista de lista de seriales de equipos reportados robados con botón Agregar y acciones de
 * edición por fila.
 */
public class StolenEquipmentListView extends BaseView {
    private static final int EDIT_COLUMN = 1;
    private final DeniedFilesController controller;
    private final List<StolenEquipment> equipments = new ArrayList<>();

    public StolenEquipmentListView() {
        super("Marcación de seriales de equipos reportados robados", true, false);
        this.controller = new DeniedFilesController();
        initComponents();
    }

    @Override
    protected void initComponents() {
        setupTitle("Marcación de seriales de equipos reportados robados", "Agregar",
                e -> navigate(this, new StolenEquipmentFormView(null)));

        JPanel card = createCard();
        card.add(createTable(new String[] {"Serial – Tipo – Marca – Modelo", "Acción"}),
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
        equipments.clear();
        tableModel.setRowCount(0);
        for (StolenEquipment e : controller.getAllStolenEquipment()) {
            equipments.add(e);
            String type = e.getEquipmentType() != null ? e.getEquipmentType() : "";
            String brand = e.getBrand() != null ? e.getBrand() : "";
            String model = e.getModel() != null ? e.getModel() : "";
            tableModel.addRow(new Object[] {
                    e.getSerial() + (type.isBlank() ? "" : " – " + type)
                            + (brand.isBlank() ? "" : " – " + brand)
                            + (model.isBlank() ? "" : " – " + model),
                    "✎"});
        }
    }

    private void openEditForm(int row) {
        if (row >= 0 && row < equipments.size()) {
            navigate(this, new StolenEquipmentFormView(equipments.get(row)));
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
