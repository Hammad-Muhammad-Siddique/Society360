package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DomainEntity.Property;


public class PropertyRegistrationForm extends JFrame {
    public PropertyRegistrationForm() {
        setTitle("Register Property");
        setSize(500, 350);
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
        contentPanel.setLayout(null);
        contentPanel.setPreferredSize(new Dimension(500, 700));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 250;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 20, 20, labelFont);
        JTextField nameField = createTextField(160, 20, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 20, 60, labelFont);
        JTextField ageField = createTextField(160, 60, fieldWidth, fieldHeight);

        JLabel cnicLabel = createLabel("Username:", 20, 100, labelFont);
        JTextField cnicField = createTextField(160, 100, fieldWidth, fieldHeight);

        JLabel propertyLabel = createLabel("Property Address:", 20, 140, labelFont);
        JTextField propertyField = createTextField(160, 140, fieldWidth, fieldHeight);

        JLabel typeLabel = createLabel("Property Type:", 20, 180, labelFont);
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Residential", "Commercial"});
        typeCombo.setBounds(160, 180, fieldWidth, fieldHeight);
        styleComboBox(typeCombo);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 250, 100, 30);
        styleButton(submitButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(propertyLabel);
        contentPanel.add(propertyField);
        contentPanel.add(typeLabel);
        contentPanel.add(typeCombo);
        contentPanel.add(submitButton);

        // Submit button action listener
submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if any required field is empty
        if (nameField.getText().trim().isEmpty() || ageField.getText().trim().isEmpty() ||
                cnicField.getText().trim().isEmpty() || propertyField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields before submitting.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Get the values from the fields
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String username = cnicField.getText().trim();
            String propertyAddress = propertyField.getText().trim();
            String propertyType = (String) typeCombo.getSelectedItem();

            Property p = new Property(name, age, username, propertyAddress, propertyType);
            
            // Create a BusinessLogicLayer object and insert the property
            BusinessLogicLayer businessLogicLayer = new BusinessLogicLayer();
            boolean success = businessLogicLayer.insertProperty(p);

            // Show success or error message
            if (success) {
                JOptionPane.showMessageDialog(null, "Property registration submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to register property.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});


        // Set the window visibility
        setLocationRelativeTo(null); // Center the window on the screen
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
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
