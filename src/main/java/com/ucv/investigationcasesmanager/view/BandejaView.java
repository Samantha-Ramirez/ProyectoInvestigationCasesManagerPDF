package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.dao.CasoDAO;
import com.ucv.investigationcasesmanager.model.Caso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BandejaView extends JFrame {
    private JTable tablaCasos;
    private DefaultTableModel modelo;

    public BandejaView(int idInvestigador) {
        setTitle("Inicio / Bandeja de casos");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 1. Cabecera Morada ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(128, 0, 128));
        header.setPreferredSize(new Dimension(1000, 70));
        JLabel lblUser = new JLabel("👤 "); // Icono simulado
        lblUser.setForeground(Color.GREEN);
        header.add(lblUser, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- 2. Barra Lateral (Menú) ---
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(Color.WHITE);
        sideBar.setPreferredSize(new Dimension(150, 0));
        String[] menuItems = {"🏠 Inicio", "📊 Reportes", "🔍 Auditoría", "📂 Entidades"};
        for (String item : menuItems) {
            sideBar.add(new JLabel(item));
            sideBar.add(Box.createVerticalStrut(20));
        }
        add(sideBar, BorderLayout.WEST);

        // --- 3. Panel Central (Bandeja) ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Bandeja de casos");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(230, 230, 230));

        JPanel topActions = new JPanel(new BorderLayout());
        topActions.add(title, BorderLayout.WEST);
        topActions.add(btnRegistrar, BorderLayout.EAST);
        mainPanel.add(topActions, BorderLayout.NORTH);

        // --- 4. Tabla de Casos ---
        String[] columnas = {"Caso", "Tiempo", "Status", "Acción"};
        modelo = new DefaultTableModel(columnas, 0);
        tablaCasos = new JTable(modelo);
        tablaCasos.setRowHeight(40);
        mainPanel.add(new JScrollPane(tablaCasos), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        cargarDatos(idInvestigador);
    }

    private void cargarDatos(int idInvestigador) {
        CasoDAO dao = new CasoDAO();
        List<Caso> casos = dao.listarCasosPorInvestigador(idInvestigador);
        for (Caso c : casos) {
            modelo.addRow(new Object[] {c.getNroExpediente(), c.getTiempoSinAtencion(),
                    c.getEstatus(), "📝"});
        }
    }
}
