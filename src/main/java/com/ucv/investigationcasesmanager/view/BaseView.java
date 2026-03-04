package com.ucv.investigationcasesmanager.view;

import com.ucv.investigationcasesmanager.factory.StartupViewFactory;
import com.ucv.investigationcasesmanager.model.Session;
import com.ucv.investigationcasesmanager.model.User;
import com.ucv.investigationcasesmanager.ui.factory.ScreenAbstractFactory;
import com.ucv.investigationcasesmanager.ui.factory.ScreenConcreteFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

/*
 * Vista base con estructura compartida y métodos auxiliares para construir pantallas con FlatLaf.
 */
public abstract class BaseView extends JFrame {
    protected User currentUser;
    protected JPanel contentPanel;
    protected JPanel sideMenu;
    protected JPanel header;
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JPanel formPanel;
    protected JScrollPane formScroll;
    protected final ScreenAbstractFactory uiFactory;
    private int currentRow = 0;

    public BaseView(String title, Boolean showMenu) {
        this(title, showMenu, true);
    }

    public BaseView(String title, Boolean showMenu, boolean initialize) {
        this.currentUser = Session.getUser();
        this.uiFactory = new ScreenConcreteFactory();

        setTitle(title);
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(242, 242, 242));

        setupHeader();
        if (showMenu) {
            setupSideMenu();
        }
        setupContentPanel();

        setLocationRelativeTo(null);

        if (initialize) {
            initComponents();
        }
    }

    private void setupHeader() {
        header = new JPanel(new BorderLayout());
        header.setBackground(uiFactory.getPrimaryColor());
        header.setPreferredSize(new Dimension(1100, 50));

        String userInfo =
                (currentUser != null) ? currentUser.getFirstName() + " " + currentUser.getLastName()
                        : "Usuario 1";

        JLabel lblUser = new JLabel(userInfo + "  ");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.BOLD, 13));
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 16));

        header.add(lblUser, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);
    }

    private void setupSideMenu() {
        sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(Color.WHITE);
        sideMenu.setPreferredSize(new Dimension(160, 0));
        sideMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));

        sideMenu.add(Box.createVerticalStrut(12));
        addMenuButton("⌂  Inicio", e -> goHome());
        addMenuButton("⚑  Bandeja", e -> goHome());
        addMenuButton("↺  Reportes", e -> goToReports());
        addMenuButton("⚙  Entidades", e -> goHome());
        addMenuButton("◌  Auditoría", e -> goHome());
        sideMenu.add(Box.createVerticalGlue());
        addMenuButton("⇦  Cerrar sesión", e -> handleLogout());
        sideMenu.add(Box.createVerticalStrut(14));

        add(sideMenu, BorderLayout.WEST);
    }

    private void addMenuButton(String text, ActionListener action) {
        JButton btn = uiFactory.createMenuButton(text, action);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        sideMenu.add(btn);
        sideMenu.add(Box.createVerticalStrut(2));
    }

    private void goHome() {
        navigate(this, StartupViewFactory.getStartView(currentUser.getRole()));
    }

    private void goToReports() {
        navigate(this, new ReportsView());
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.logout();
            navigate(this, new LoginView());
        }
    }

    private void setupContentPanel() {
        contentPanel = new JPanel(new BorderLayout(12, 12));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        add(contentPanel, BorderLayout.CENTER);
    }

    protected void setupTitle(String sectionTitle, String buttonText, ActionListener action) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(sectionTitle);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblTitle, BorderLayout.WEST);

        if (buttonText != null && action != null) {
            topPanel.add(createHeaderButton(buttonText, action), BorderLayout.EAST);
        }

        contentPanel.add(topPanel, BorderLayout.NORTH);
    }

    protected JButton createHeaderButton(String text, ActionListener action) {
        return uiFactory.createHeaderButton(text, action);
    }

    protected JButton createPrimaryButton(String text, ActionListener action) {
        return uiFactory.createPrimaryButton(text, action);
    }

    protected JPanel createCard() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(232, 232, 232)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        return card;
    }

    protected JPanel createActionBar(String infoText, JButton rightButton) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);

        if (infoText != null && !infoText.isBlank()) {
            JLabel lblInfo = new JLabel(infoText);
            lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
            lblInfo.setForeground(new Color(95, 95, 95));
            bar.add(lblInfo, BorderLayout.WEST);
        }

        if (rightButton != null) {
            bar.add(rightButton, BorderLayout.EAST);
        }

        return bar;
    }

    protected JScrollPane createTable(String[] columns) {
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(34);
        table.getTableHeader().setReorderingAllowed(false);
        uiFactory.styleTable(table);

        if (columns.length > 2 && "Status".equalsIgnoreCase(columns[2])) {
            table.getColumnModel().getColumn(2).setCellRenderer(new StatusBadgeRenderer());
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(236, 236, 236)));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    protected void setupTable(String[] columns) {
        contentPanel.add(createTable(columns), BorderLayout.CENTER);
    }

    protected JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        return form;
    }

    protected int addField(JPanel form, int row, String label, JComponent field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(8, 4, 2, 4);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lbl = new JLabel(label + ":");
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        form.add(lbl, gbc);

        gbc.gridy = row + 1;
        gbc.insets = new Insets(0, 4, 8, 4);
        form.add(field, gbc);
        return row + 2;
    }

    protected JTextArea createTextArea(int rows, int cols, int preferredHeight) {
        JTextArea area = new JTextArea(rows, cols);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        area.setBackground(new Color(237, 237, 237));
        area.setPreferredSize(new Dimension(420, preferredHeight));
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        return area;
    }

    protected JScrollPane wrapInScroll(JComponent component) {
        JScrollPane scroll = new JScrollPane(component);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    protected void styleInput(JComponent component) {
        uiFactory.styleInput(component);
    }

    protected JPanel createBottomPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        panel.setOpaque(false);
        for (JButton btn : buttons) {
            if (btn != null) {
                panel.add(btn);
            }
        }
        return panel;
    }

    protected void setupFormPanel() {
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        currentRow = 0;

        formScroll = new JScrollPane(formPanel);
        formScroll.setBorder(null);
        formScroll.getViewport().setBackground(Color.WHITE);
        formScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScroll.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(formScroll, BorderLayout.CENTER);
    }

    protected void navigate(JFrame current, JFrame next) {
        current.dispose();
        next.setVisible(true);
    }

    protected void addFormField(JComponent component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = currentRow++;
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        if (component instanceof JTextField || component instanceof JTextArea
                || component instanceof JComboBox<?>) {
            stylePlaceholder(component);
        }

        formPanel.add(component, gbc);
        formPanel.revalidate();
    }

    private void stylePlaceholder(JComponent component) {
        uiFactory.styleInput(component);

        if (!(component instanceof JTextField || component instanceof JTextArea)) {
            return;
        }

        component.setForeground(Color.GRAY);
        String placeholder = (component instanceof JTextField) ? ((JTextField) component).getText()
                : ((JTextArea) component).getText();

        component.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                String current =
                        (component instanceof JTextField) ? ((JTextField) component).getText()
                                : ((JTextArea) component).getText();
                if (current.equals(placeholder)) {
                    if (component instanceof JTextField) {
                        ((JTextField) component).setText("");
                    } else {
                        ((JTextArea) component).setText("");
                    }
                    component.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String current =
                        (component instanceof JTextField) ? ((JTextField) component).getText()
                                : ((JTextArea) component).getText();
                if (current.isEmpty()) {
                    if (component instanceof JTextField) {
                        ((JTextField) component).setText(placeholder);
                    } else {
                        ((JTextArea) component).setText(placeholder);
                    }
                    component.setForeground(Color.GRAY);
                }
            }
        });
    }

    protected void addPrimaryButton(String text, ActionListener action) {
        contentPanel.add(createBottomPanel(createPrimaryButton(text, action)), BorderLayout.SOUTH);
    }

    protected JButton createRoundedButton(String text, Color bgColor, ActionListener action) {
        JButton btn = uiFactory.createHeaderButton(text, action);
        btn.setBackground(bgColor);
        return btn;
    }

    protected abstract void initComponents();

    private class StatusBadgeRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JLabel badge = uiFactory.createStatusBadge(String.valueOf(value));
            if (isSelected) {
                badge.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(185, 170, 212)),
                        BorderFactory.createEmptyBorder(2, 8, 2, 8)));
            }
            return badge;
        }
    }
}
