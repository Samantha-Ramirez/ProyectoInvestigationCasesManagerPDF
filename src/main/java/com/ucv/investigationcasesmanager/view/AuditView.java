package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.AuditController;
import com.ucv.investigationcasesmanager.model.AuditLog;

import java.util.List;

/*
 * Vista de trazabilidad y auditoría. Muestra la lista de trazas: usuario, acción y fecha/hora.
 */
public class AuditView extends BaseView {
    private final AuditController auditController;

    public AuditView() {
        super("Trazabilidad y auditoría", true);
        this.auditController = new AuditController();
        loadData();
    }

    @Override
    protected void initComponents() {
        setupTitle("Trazabilidad y auditoría", null, null);

        javax.swing.JPanel card = createCard();
        card.add(createTable(new String[] {"Usuario", "Detalle", "Fecha/Hora"}),
                java.awt.BorderLayout.CENTER);

        contentPanel.add(card, java.awt.BorderLayout.CENTER);
    }

    private void loadData() {
        List<AuditLog> logs = auditController.getAllLogs();
        for (AuditLog log : logs) {
            String action = log.getAction();
            tableModel.addRow(new Object[] {log.getUsername(), action, log.getActionDate()});
        }
    }
}
