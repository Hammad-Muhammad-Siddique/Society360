package UI;

import BusinessLogic.BusinessLogicLayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import DomainEntity.HealthcareUnit;


public class ManageHealthCareForm extends JFrame {

    // Create an instance of BusinessLogicLayer
    private BusinessLogicLayer businessLogicLayer = new BusinessLogicLayer();

    public ManageHealthCareForm() {
        setTitle("Manage Healthcare");
        setSize(450, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Content panel with gradient background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(0, 204, 255)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Manage Healthcare Units");
        titleLabel.setBounds(40, 20, 350, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(titleLabel);

        // Styling
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 200;
        int fieldHeight = 30;

        // Healthcare ID
        JLabel idLabel = createLabel("ID:", 40, 70, labelFont);
        JTextField idField = createTextField(180, 70, fieldWidth, fieldHeight);

        // Healthcare Name
        JLabel nameLabel = createLabel("Name:", 40, 110, labelFont);
        JTextField nameField = createTextField(180, 110, fieldWidth, fieldHeight);

        // Healthcare Location
        JLabel locationLabel = createLabel("Location:", 40, 150, labelFont);
        JTextField locationField = createTextField(180, 150, fieldWidth, fieldHeight);

        // Healthcare Type
        JLabel typeLabel = createLabel("Type:", 40, 190, labelFont);
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Clinic", "Lab", "Hospital", "Dispensary"});
        typeComboBox.setBounds(180, 190, fieldWidth, fieldHeight);
        styleComboBox(typeComboBox);

        // Add button
        JButton addButton = new JButton("Add Healthcare Unit");
        addButton.setBounds(120, 240, fieldWidth, fieldHeight);
        styleButton(addButton);

        // Add button listener
        addButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String location = locationField.getText();
            String type = (String) typeComboBox.getSelectedItem();

            HealthcareUnit h = new HealthcareUnit(Integer.parseInt(id), name, location, type);
            
            if (!id.isEmpty() && !name.isEmpty() && !location.isEmpty() && type != null) {
                boolean success = businessLogicLayer.addHealthcareUnit(h);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Healthcare unit added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    idField.setText("");
                    nameField.setText("");
                    locationField.setText("");
                    typeComboBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding healthcare unit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show Healthcare Units Button
        JButton showButton = new JButton("Show Healthcare Units");
        showButton.setBounds(120, 390, fieldWidth, fieldHeight);
        styleButton(showButton);
        
showButton.addActionListener(e -> {
    List<String[]> healthcareUnits = businessLogicLayer.getAllHealthcareUnits();
    String[] columnNames = {"ID", "Name", "Location", "Type"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    for (String[] unit : healthcareUnits) {
        tableModel.addRow(unit);
    }

    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(50, 520, 350, 120);
    contentPanel.add(scrollPane);
    contentPanel.revalidate();
    contentPanel.repaint();
});


        // Delete Healthcare Unit Section
        JLabel deleteIdLabel = createLabel("Healthcare Unit ID:", 40, 300, labelFont);
        JTextField deleteIdField = createTextField(180, 300, fieldWidth, fieldHeight);

        JButton deleteButton = new JButton("Delete Healthcare Unit");
        deleteButton.setBounds(120, 350, fieldWidth, fieldHeight);
        styleButton(deleteButton);

        // Delete button listener
        deleteButton.addActionListener(e -> {
            String healthcareId = deleteIdField.getText();
            if (!healthcareId.isEmpty()) {
                boolean success = businessLogicLayer.deleteHealthcareUnit(Integer.parseInt(healthcareId));
                if (success) {
                    JOptionPane.showMessageDialog(this, "Healthcare unit deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    deleteIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting healthcare unit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter the Healthcare Unit ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 450, 100, 30);
        styleButton(backButton);

        // Back button listener
        backButton.addActionListener(e -> dispose());

        // Add components to content panel
        contentPanel.add(idLabel);
        contentPanel.add(idField);
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(locationLabel);
        contentPanel.add(locationField);
        contentPanel.add(typeLabel);
        contentPanel.add(typeComboBox);
        contentPanel.add(addButton);
        contentPanel.add(deleteIdLabel);
        contentPanel.add(deleteIdField);
        contentPanel.add(deleteButton);
        contentPanel.add(backButton);
        contentPanel.add(showButton);

        // Set content pane
        setContentPane(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Utility methods for consistent styling
    private JLabel createLabel(String text, int x, int y, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 20);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        styleTextField(textField);
        return textField;
    }

    private void styleTextField(JComponent textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        comboBox.setFocusable(false);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
