package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.ReportController;
import com.ucv.investigationcasesmanager.factory.ReportProduct;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.List;

/*
 * Vista de reportes - recibe el tipo de reporte seleccionado desde el menú lateral y lo genera
 * automáticamente, sin combo de selección en pantalla (UC08).
 */
public class ReportsView extends BaseView {
    private final ReportController reportController;
    private final String reportType;

    // Por qué: el tipo de reporte se elige en el popup del menú (BaseView.showReportsPopup)
    // para cumplir con el flujo UC08 que indica selección desde el menú, no desde la pantalla.
    public ReportsView(String reportType) {
        super("Reportes", true);
        this.reportController = new ReportController();
        this.reportType = reportType;
    }

    @Override
    protected void initComponents() {
        setupTitle("Reporte: " + reportType, null, null);

        JPanel card = createCard();
        card.add(createTable(new String[] {"Reporte", "Resultado"}), BorderLayout.CENTER);
        contentPanel.add(card, BorderLayout.CENTER);

        generateReport();
    }

    private void generateReport() {
        ReportProduct report = reportController.resolveReport(reportType);

        tableModel.setColumnIdentifiers(report.getColumns());
        tableModel.setRowCount(0);

        List<Object[]> rows = reportController.generateRows(report);
        if (rows.isEmpty()) {
            tableModel.addRow(new Object[] {"Sin datos", "No hay información para este criterio"});
        } else {
            for (Object[] row : rows) {
                tableModel.addRow(row);
            }
        }
    }
}
