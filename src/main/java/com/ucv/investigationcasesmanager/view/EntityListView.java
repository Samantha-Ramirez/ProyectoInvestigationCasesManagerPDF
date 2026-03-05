package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.EntityController;
import com.ucv.investigationcasesmanager.iterator.EntityIterator;
import com.ucv.investigationcasesmanager.model.EntityType;
import com.ucv.investigationcasesmanager.model.SystemEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/*
 * Vista de lista para UC09 – muestra los registros de una entidad con acciones de editar.
 * PDyF: Iterator – usa EntityIterator para poblar la tabla sin exponer la colección interna.
 */
public class EntityListView extends BaseView {
    private static final int EDIT_COLUMN = 1;

    private final EntityController entityController;
    private final EntityType entityType;
    private final List<SystemEntity> entities = new ArrayList<>();

    public EntityListView(EntityType entityType) {
        super("Gestión de " + entityType.getLabel(), true, false);
        this.entityController = new EntityController();
        this.entityType = entityType;
        initComponents();
    }

    @Override
    protected void initComponents() {
        setupTitle("Gestión de " + entityType.getLabel(), "Registrar",
                e -> navigate(this, new EntityFormView(entityType, null)));

        JPanel card = createCard();
        card.add(createTable(new String[] {"Nombre", "Acción"}), java.awt.BorderLayout.CENTER);

        // Por qué: la columna de acción muestra sólo el ícono de edición, sin encabezado de texto
        table.getColumnModel().getColumn(EDIT_COLUMN).setMaxWidth(52);
        table.getColumnModel().getColumn(EDIT_COLUMN).setMinWidth(52);
        table.getColumnModel().getColumn(EDIT_COLUMN)
                .setCellRenderer(new EditIconRenderer(uiFactory.getPrimaryColor()));

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

    // PDyF: Iterator – recorre la colección a través del iterador sin acceder directamente a la
    // lista
    private void loadData() {
        entities.clear();
        tableModel.setRowCount(0);
        EntityIterator<SystemEntity> it = entityController.getIterator(entityType);
        while (it.hasNext()) {
            SystemEntity entity = it.next();
            entities.add(entity);
            tableModel.addRow(new Object[] {entity.getName(), "✎"});
        }
    }

    private void openEditForm(int row) {
        if (row >= 0 && row < entities.size()) {
            navigate(this, new EntityFormView(entityType, entities.get(row)));
        }
    }

    // Renderizador de la celda de acción con ícono de edición en color primario
    private static class EditIconRenderer extends DefaultTableCellRenderer {
        private final Color primaryColor;

        EditIconRenderer(Color primaryColor) {
            this.primaryColor = primaryColor;
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            JLabel lbl = new JLabel("✎", SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.PLAIN, 16));
            lbl.setForeground(primaryColor);
            lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lbl.setOpaque(true);
            lbl.setBackground(isSelected ? new Color(242, 236, 247) : Color.WHITE);
            return lbl;
        }
    }
}
