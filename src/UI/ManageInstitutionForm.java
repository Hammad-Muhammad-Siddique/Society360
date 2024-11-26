package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import DomainEntity.Institution;


public class ManageInstitutionForm extends JFrame {

    // Create an instance of BusinessLogicLayer
    private BusinessLogicLayer businessLogicLayer = new BusinessLogicLayer();

    public ManageInstitutionForm() {
        setTitle("Manage Institutions");
        setSize(450, 700); // Increase the height to accommodate table
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

        // Add title label
        JLabel titleLabel = new JLabel("Manage Institutions");
        titleLabel.setBounds(50, 20, 350, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(titleLabel);

        // Shared styles
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 200;
        int fieldHeight = 30;

        // Institution ID
        JLabel idLabel = createLabel("Institution ID:", 50, 70, labelFont);
        JTextField idField = createTextField(200, 70, fieldWidth, fieldHeight);

        // Institution Name
        JLabel nameLabel = createLabel("Name:", 50, 110, labelFont);
        JTextField nameField = createTextField(200, 110, fieldWidth, fieldHeight);

        // Institution Location
        JLabel locationLabel = createLabel("Location:", 50, 150, labelFont);
        JTextField locationField = createTextField(200, 150, fieldWidth, fieldHeight);

        // Institution Type
        JLabel typeLabel = createLabel("Type:", 50, 190, labelFont);
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"University", "School", "College"});
        typeComboBox.setBounds(200, 190, fieldWidth, fieldHeight);
        styleComboBox(typeComboBox);

        // Add Button
        JButton addButton = new JButton("Add Institution");
        addButton.setBounds(100, 240, fieldWidth, fieldHeight);
        styleButton(addButton);

        addButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String location = locationField.getText();
            String type = (String) typeComboBox.getSelectedItem();

            Institution i = new Institution(id, name, location, type);
            
            if (!name.isEmpty() && !location.isEmpty() && type != null) {
                boolean success = businessLogicLayer.addInstitution(i);  // Use the instance
                if (success) {
                    JOptionPane.showMessageDialog(null, "Institution Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    idField.setText("");
                    nameField.setText("");
                    locationField.setText("");
                    typeComboBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Error adding institution.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show Institutions Button
        JButton showButton = new JButton("Show Institutions");
        showButton.setBounds(100, 380, fieldWidth, fieldHeight);
        styleButton(showButton);

        showButton.addActionListener(e -> {
            List<String[]> institutions = businessLogicLayer.getAllInstitutions();  // Use the instance
            String[] columnNames = {"ID", "Name", "Location", "Type"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (String[] institution : institutions) {
                tableModel.addRow(institution);
            }

            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(50, 420, 350, 120);
            contentPanel.add(scrollPane);
        });

        // Delete Institution
        JLabel deleteIdLabel = createLabel("Delete Institution ID:", 50, 290, labelFont);
        JTextField deleteIdField = createTextField(200, 290, fieldWidth, fieldHeight);

        JButton deleteButton = new JButton("Delete Institution");
        deleteButton.setBounds(100, 330, fieldWidth, fieldHeight);
        styleButton(deleteButton);

        deleteButton.addActionListener(e -> {
            int institutionId = Integer.parseInt(deleteIdField.getText());

            if (institutionId > 0) {
                boolean success = businessLogicLayer.deleteInstitution(institutionId);  // Use the instance
                if (success) {
                    JOptionPane.showMessageDialog(null, "Institution Deleted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    deleteIdField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Error deleting institution.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid Institution ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 600, 100, fieldHeight);
        styleButton(backButton);

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

        // Set content panel
        setContentPane(contentPanel);
        setLocationRelativeTo(null); // Center on screen
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
