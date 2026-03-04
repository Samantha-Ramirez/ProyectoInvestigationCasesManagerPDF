package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.controller.ReportController;
import com.ucv.investigationcasesmanager.factory.ReportProduct;
import com.ucv.investigationcasesmanager.view.decorator.PanelBorderDecorator;
import com.ucv.investigationcasesmanager.view.decorator.PanelComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelConcreteComponent;
import com.ucv.investigationcasesmanager.view.decorator.PanelTitleDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * Vista de reportes - permite seleccionar y generar reportes estadísticos del sistema.
 */
public class ReportsView extends BaseView {
    private final ReportController reportController;
    private JComboBox<String> cmbReportType;

    public ReportsView() {
        super("Reportes", true);
        this.reportController = new ReportController();
    }

    @Override
    protected void initComponents() {
        setupTitle("Generar reporte", null, null);

        JPanel card = createCard();
        JPanel selectionPanel = createReportSelectionPanel();

        PanelComponent decorated = new PanelBorderDecorator(
                new PanelTitleDecorator(new PanelConcreteComponent(selectionPanel), "Filtros"), 6,
                8, 6, 8);

        card.add(decorated.build(), BorderLayout.NORTH);
        card.add(createTable(new String[] {"Reporte", "Resultado"}), BorderLayout.CENTER);

        contentPanel.add(card, BorderLayout.CENTER);
    }

    private JPanel createReportSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);

        panel.add(new JLabel("Tipo de reporte:"));
        cmbReportType = new JComboBox<>(new String[] {"Empresas con mayores casos",
                "Investigadores con mayores casos", "Casos con más de 3 casos relacionados"});
        cmbReportType.setPreferredSize(new Dimension(330, 34));
        panel.add(cmbReportType);

        JButton btnGenerate = createPrimaryButton("Generar reporte", e -> generateReport());
        panel.add(btnGenerate);

        return panel;
    }

    private void generateReport() {
        String type = String.valueOf(cmbReportType.getSelectedItem());
        ReportProduct report = reportController.resolveReport(type);

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
