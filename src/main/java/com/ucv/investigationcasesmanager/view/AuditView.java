package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.AuditController;
import com.ucv.investigationcasesmanager.model.AuditLog;

import java.util.List;

/*
 * Vista de trazabilidad y auditoría. Muestra la lista de trazas: usuario, acción y fecha/hora.
 * PDyF: el AuditSaveDecorator registra automáticamente cada operación de guardado en el sistema;
 * esta vista solo consulta y presenta dichos registros.
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
        card.add(createTable(new String[] {"Usuario", "Acción", "Detalle", "Fecha/Hora"}),
                java.awt.BorderLayout.CENTER);

        contentPanel.add(card, java.awt.BorderLayout.CENTER);
    }

    private void loadData() {
        List<AuditLog> logs = auditController.getAllLogs();
        for (AuditLog log : logs) {
            // Separar "Tipo de acción: detalle" en dos columnas para mayor claridad
            String[] parts = log.getAction().split(": ", 2);
            String actionType = parts[0];
            String detail = parts.length > 1 ? parts[1] : "";
            tableModel.addRow(
                    new Object[] {log.getUsername(), actionType, detail, log.getActionDate()});
        }
    }
}
