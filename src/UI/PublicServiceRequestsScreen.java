package UI;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PublicServiceRequestsScreen extends JFrame {
    private JTable serviceRequestsTable;
    private DefaultTableModel tableModel;

    public PublicServiceRequestsScreen() {
        setTitle("View Public Service Requests");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Background Gradient
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.BLUE, 0, getHeight(), Color.CYAN));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());
        setContentPane(contentPanel);

        // Title Label
        JLabel titleLabel = new JLabel("Public Service Requests", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {
                "Name", "Age", "Username", "Service Type", "Description",
                "Location", "Urgency Level", "Expected Completion Date", "Contact Info", "Action"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        serviceRequestsTable = new JTable(tableModel);
        serviceRequestsTable.setRowHeight(30);
        serviceRequestsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        serviceRequestsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        serviceRequestsTable.getTableHeader().setBackground(new Color(0, 102, 204));
        serviceRequestsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(serviceRequestsTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Load data from the database
        loadPublicServiceRequests();

        // Set custom renderer and editor for Action column
        serviceRequestsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        serviceRequestsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Back Button
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> dispose());
        contentPanel.add(backButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void loadPublicServiceRequests() {
        try (Connection conn = DatabaseConnection.connectDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM RequestPublicService")) {

            tableModel.setRowCount(0); // Clear previous data

            while (rs.next()) {
                Object[] row = {
                        rs.getString("Name"),
                        rs.getInt("Age"),
                        rs.getString("Username"),
                        rs.getString("ServiceType"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("UrgencyLevel"),
                        rs.getDate("ExpectedCompletionDate"),
                        rs.getString("ContactInformation"),
                        "Delete" // Add Delete button
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading public service requests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteServiceRequest(String username) {
        try (Connection conn = DatabaseConnection.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM RequestPublicService WHERE Username = ?")) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Service request deleted successfully.");
                int rowIndex = getRowIndexByUsername(username);
                if (rowIndex != -1) {
                    tableModel.removeRow(rowIndex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete service request.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting service request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getRowIndexByUsername(String username) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(username)) { // Username is in the third column
                return i;
            }
        }
        return -1;
    }

    // Custom renderer for buttons in JTable
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Arial", Font.BOLD, 12));
            setForeground(new Color(0, 102, 204));
            setBackground(Color.WHITE);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom editor for buttons in JTable
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setForeground(new Color(0, 102, 204));
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(e -> {
                int row = serviceRequestsTable.getSelectedRow();
                String username = serviceRequestsTable.getValueAt(row, 2).toString();
                deleteServiceRequest(username);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
