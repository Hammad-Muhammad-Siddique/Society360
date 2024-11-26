package UI;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewCrimeOrIncidentReportsScreen extends JFrame {

    public ViewCrimeOrIncidentReportsScreen() {
        setTitle("View Crime or Incident Reports");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gradient background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.DARK_GRAY, 0, getHeight(), Color.LIGHT_GRAY));
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

        // Title label
        JLabel titleLabel = new JLabel("Crime or Incident Reports", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display reports
        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"Name", "Age", "Username", "Location", "Description", "Date", "Time"}, 0);
        JTable reportTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        reportTable.setRowHeight(30);
        reportTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        reportTable.getTableHeader().setBackground(new Color(0, 102, 204));
        reportTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Fetch reports and populate the table
        populateReportsTable(tableModel);
    }

    private void populateReportsTable(DefaultTableModel tableModel) {
        String query = "SELECT Name, Age, Username, Location, Description, OccurrenceDate, OccurrenceTime FROM ReportCrimeOrIncident";

        try (Connection connection = DatabaseConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String username = resultSet.getString("Username");
                String location = resultSet.getString("Location");
                String description = resultSet.getString("Description");
                String date = resultSet.getString("OccurrenceDate");
                String time = resultSet.getString("OccurrenceTime");

                tableModel.addRow(new Object[]{name, age, username, location, description, date, time});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to fetch reports from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
