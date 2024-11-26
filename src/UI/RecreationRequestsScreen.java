package UI;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RecreationRequestsScreen extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public RecreationRequestsScreen() {
        setTitle("Recreation Facility Reservation Requests");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for the background gradient
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

        // Back Button
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> dispose());
        backgroundPanel.add(backButton, BorderLayout.SOUTH);

        // Add title label
        JLabel titleLabel = new JLabel("Recreation Facility Reservation Requests", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display recreation requests
        tableModel = new DefaultTableModel(
                new String[]{"Name", "Age", "Username", "Facility Name", "Reservation Date", "Reservation Time", "Status", "Action"}, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only the action column is editable
            }
        };
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Fetch data from the database
        loadRecreationRequests();

        // Add buttons for actions
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    private void loadRecreationRequests() {
        tableModel.setRowCount(0); // Clear existing rows
        try (Connection conn = DatabaseConnection.connectDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ReserveRecreationFacility")) {

            while (rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String username = rs.getString("Username");
                String facilityName = rs.getString("FacilityName");
                String reservationDate = rs.getDate("ReservationDate").toString();
                String reservationTime = rs.getTime("ReservationTime").toString();
                String status = rs.getString("Status");

                tableModel.addRow(new Object[]{name, age, username, facilityName, reservationDate, reservationTime, status, "Approve/Reject"});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom renderer for buttons in JTable
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Arial", Font.BOLD, 12));
            setForeground(new Color(0, 102, 204)); // Blue text
            setBackground(Color.WHITE); // White background
            setFocusPainted(false);
            setBorderPainted(false);
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
            button.setForeground(new Color(0, 102, 204)); // Blue text
            button.setBackground(Color.WHITE); // White background
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.getSelectedRow();
                    String username = table.getValueAt(row, 2).toString();
                    String facilityName = table.getValueAt(row, 3).toString();
                    String action = JOptionPane.showInputDialog("Enter 'Approve' or 'Reject':");

                    if ("Approve".equalsIgnoreCase(action)) {
                        updateRecreationRequestStatus(username, facilityName, "Approved");
                    } else if ("Reject".equalsIgnoreCase(action)) {
                        updateRecreationRequestStatus(username, facilityName, "Rejected");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid action entered.");
                    }

                    loadRecreationRequests(); // Refresh table
                }
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

    private void updateRecreationRequestStatus(String username, String facilityName, String status) {
        try (Connection conn = DatabaseConnection.connectDatabase();
             Statement stmt = conn.createStatement()) {
            String query = "UPDATE ReserveRecreationFacility SET Status='" + status + "' WHERE Username='" + username + "' AND FacilityName='" + facilityName + "'";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Request status updated to " + status);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating status: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
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
