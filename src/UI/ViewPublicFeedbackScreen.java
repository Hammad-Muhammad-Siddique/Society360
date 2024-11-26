package UI;

import BusinessLogic.BusinessLogicLayer;
import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;

public class ViewPublicFeedbackScreen extends JFrame {
    
    private JTable feedbackTable;
    private DefaultTableModel tableModel;
    private BusinessLogicLayer businessLogicLayer;

    public ViewPublicFeedbackScreen() {
        setTitle("Public Feedback");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        businessLogicLayer = new BusinessLogicLayer();

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
        backButton.setBounds(380, 490, 120, 40);
        styleButton(backButton);
        backgroundPanel.add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> dispose()); // Close the window on back button click
        
        // Add title label
        JLabel titleLabel = new JLabel("Public Feedback", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Create the table with column headers
        String[] columns = {"Name", "Age", "Username", "Feedback Topic", "Feedback Details", "Urgency Level", "Contact Method", "Feedback Date", "Action"};
        tableModel = new DefaultTableModel(columns, 0);
        feedbackTable = new JTable(tableModel);
        
        // Style the table
        feedbackTable.setRowHeight(30);
        feedbackTable.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        feedbackTable.getTableHeader().setBackground(new Color(0, 102, 204));
        feedbackTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Load the feedback data from the database
        loadFeedbackData();

        // Set custom renderer and editor for Action column
        feedbackTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        feedbackTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        setVisible(true);
    }

private void loadFeedbackData() {
    // Create the SQL query to fetch feedback data
    String query = "SELECT Name, Age, Username, FeedbackTopic, FeedbackDetails, UrgencyLevel, ContactMethod, FeedbackDate FROM SubmitPublicFeedback";
    
    try (Connection conn = DatabaseConnection.connectDatabase(); // Get the DB connection
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        
        // Clear the previous data in the table
        tableModel.setRowCount(0);
        
        // Add rows to the table
        while (rs.next()) {
            String name = rs.getString("Name");
            int age = rs.getInt("Age");
            String username = rs.getString("Username");
            String feedbackTopic = rs.getString("FeedbackTopic");
            String feedbackDetails = rs.getString("FeedbackDetails");
            String urgencyLevel = rs.getString("UrgencyLevel");
            String contactMethod = rs.getString("ContactMethod");
            Date feedbackDate = rs.getDate("FeedbackDate");
            
            // Add row to table model with "Delete" in the last column (Action column)
            Object[] row = new Object[]{name, age, username, feedbackTopic, feedbackDetails, urgencyLevel, contactMethod, feedbackDate, "Delete"};
            tableModel.addRow(row);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading feedback data.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void deleteFeedback(String username) {
        // Delete feedback from the database using BusinessLogicLayer
        boolean deleted = businessLogicLayer.deleteFeedback(username);
        
        if (deleted) {
            JOptionPane.showMessageDialog(this, "Feedback deleted successfully.");
            // Remove the row from the table after deletion
            int rowIndex = getRowIndexByUsername(username);
            if (rowIndex != -1) {
                tableModel.removeRow(rowIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting feedback.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getRowIndexByUsername(String username) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(username)) { // Username is in the third column
                return i;
            }
        }
        return -1; // Return -1 if username is not found
    }

    // Custom renderer for buttons in JTable
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
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
                int row = feedbackTable.getSelectedRow();
                String username = feedbackTable.getValueAt(row, 2).toString();
                deleteFeedback(username); // Call business logic to delete feedback
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
