package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Database.DatabaseConnection;

public class ViewAllResidentsScreen extends JFrame {
    private JTable residentTable;
    private DefaultTableModel tableModel;

    public ViewAllResidentsScreen() {
        setTitle("View All Residents");
        setSize(1000, 600); // Increased size for better layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel with gradient background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.BLUE, 0, getHeight(), Color.CYAN));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Title Label
        JLabel titleLabel = new JLabel("All Residents", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display residents
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Gender", "Username", "Remove"}, 0);
        residentTable = new JTable(tableModel);
        residentTable.setRowHeight(30);
        residentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        residentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        residentTable.getTableHeader().setBackground(new Color(0, 102, 204));
        residentTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(residentTable);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Load residents data
        loadResidents();

        // Add "Back" button
        JButton backButton = new JButton("Back");
        styleButton(backButton); // Apply button style
        backgroundPanel.add(backButton, BorderLayout.SOUTH);

        // Action listener for "Back" button
        backButton.addActionListener(e -> {
            dispose();
            new AdminHomePage().setVisible(true);
        });

        setVisible(true);
    }

    private void loadResidents() {
        tableModel.setRowCount(0); // Clear existing rows
        try (Connection conn = DatabaseConnection.connectDatabase()) {
            String query = "SELECT * FROM Resident";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String name = rs.getString("Name");
                    int age = rs.getInt("Age");
                    String gender = rs.getString("Gender");
                    String username = rs.getString("Username");

                    Object[] rowData = {id, name, age, gender, username, "Remove"};
                    tableModel.addRow(rowData);

                    // Set up the "Remove" button in the table
                    residentTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                    residentTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), this, residentTable));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading residents: " + e.getMessage());
        }
    }

    // Custom renderer for buttons in JTable
// Button Renderer for Remove button
private static class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setFont(new Font("Arial", Font.BOLD, 12)); // Match font style from PropertyRequestsScreen
        setForeground(new Color(0, 102, 204)); // Blue text color
        setBackground(Color.WHITE); // White background
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Remove" : value.toString());
        return this;
    }
}



// Button Editor for Remove button
    private static class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private int row;
    private JTable residentTable;
    private ViewAllResidentsScreen viewScreen;

    public ButtonEditor(JCheckBox checkBox, ViewAllResidentsScreen viewScreen, JTable table) {
        super(checkBox);
        this.viewScreen = viewScreen; // Store the reference to ViewAllResidentsScreen
        this.residentTable = table;
        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Match font style
        button.setForeground(new Color(0, 102, 204)); // Blue text color
        button.setBackground(Color.WHITE); // White background
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Remove button click
                int residentId = (int) residentTable.getValueAt(row, 0); // Get the resident ID

                // Confirm deletion
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this resident?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    // Remove the resident from the SQL database
                    try (Connection conn = DatabaseConnection.connectDatabase()) {
                        String query = "DELETE FROM Resident WHERE ID = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(query)) {
                            stmt.setInt(1, residentId);
                            int rowsAffected = stmt.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Resident removed successfully.");
                                // Reload residents list
                                viewScreen.loadResidents();  // Call loadResidents through the viewScreen instance
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to remove resident.");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error removing resident: " + ex.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        label = (value == null) ? "Remove" : value.toString();
        button.setText(label);
        return button;
    }
}


    // Style the Back button to match the PropertyRequestsScreen
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 102, 204)); // Blue background
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
