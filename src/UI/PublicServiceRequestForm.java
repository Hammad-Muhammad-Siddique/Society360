package UI;

import BusinessLogic.BusinessLogicLayer;


import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DomainEntity.PublicService;


public class PublicServiceRequestForm extends JFrame {
    public PublicServiceRequestForm() {
        setTitle("Request New Public Service Form");
        setSize(700, 600);
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
        setContentPane(contentPanel);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 200;
        int fieldHeight = 30;
        int xFieldOffset = 350; // Increased x position for fields

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 50, 20, labelFont);
        JTextField nameField = createTextField(xFieldOffset, 20, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 50, 60, labelFont);
        JTextField ageField = createTextField(xFieldOffset, 60, fieldWidth, fieldHeight);

        JLabel cnicLabel = createLabel("Username:", 50, 100, labelFont);
        JTextField cnicField = createTextField(xFieldOffset, 100, fieldWidth, fieldHeight);

        JLabel serviceTypeLabel = createLabel("Service Type:", 50, 140, labelFont);
        JComboBox<String> serviceTypeCombo = new JComboBox<>(new String[]{"Garbage Collector", "Plumber", "Electrician", "Other"});
        serviceTypeCombo.setBounds(xFieldOffset, 140, fieldWidth, fieldHeight);
        styleComboBox(serviceTypeCombo);

        JLabel descriptionLabel = createLabel("Description of Service:", 50, 180, labelFont);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setBounds(xFieldOffset, 180, fieldWidth, 100);
        styleTextArea(descriptionArea);

        JLabel locationLabel = createLabel("Location:", 50, 300, labelFont);
        JTextField locationField = createTextField(xFieldOffset, 300, fieldWidth, fieldHeight);

        JLabel urgencyLabel = createLabel("Urgency Level:", 50, 340, labelFont);
        JComboBox<String> urgencyCombo = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        urgencyCombo.setBounds(xFieldOffset, 340, fieldWidth, fieldHeight);
        styleComboBox(urgencyCombo);

        JLabel expectedCompletionLabel = createLabel("Expected Completion Date (YYYY-MM-DD):", 50, 380, labelFont);
        JFormattedTextField expectedCompletionDateField = createDateTextField(xFieldOffset, 380, fieldWidth, fieldHeight); // Changed to JFormattedTextField

        JLabel contactInfoLabel = createLabel("Contact Information:", 50, 420, labelFont);
        JTextField contactInfoField = createTextField(xFieldOffset, 420, fieldWidth, fieldHeight);

        // Submit Button
        JButton submitButton = new JButton("Submit Request");
        submitButton.setBounds(xFieldOffset + 20, 480, 150, 30); // Slightly adjusted
        styleButton(submitButton);

submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Validate input fields
        if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || cnicField.getText().isEmpty() ||
                locationField.getText().isEmpty() || contactInfoField.getText().isEmpty() || 
                expectedCompletionDateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            if (age <= 0) {
                JOptionPane.showMessageDialog(null, "Age must be a positive number.");
                return;
            }

            String expectedCompletionDate = expectedCompletionDateField.getText();
            if (!expectedCompletionDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(null, "Expected Completion Date must follow the format YYYY-MM-DD.");
                return;
            }

            PublicService p = new PublicService(nameField.getText(),
                age,
                cnicField.getText(),
                (String) serviceTypeCombo.getSelectedItem(),
                descriptionArea.getText(),
                locationField.getText(),
                (String) urgencyCombo.getSelectedItem(),
                expectedCompletionDate,
                contactInfoField.getText());
            
            BusinessLogicLayer businessLogic = new BusinessLogicLayer();
            boolean isInserted = businessLogic.insertPublicServiceRequest(p);

            if (isInserted) {
                JOptionPane.showMessageDialog(null, "Request submitted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to submit the request.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Age must be a valid number.");
        }
    }
});


        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(serviceTypeLabel);
        contentPanel.add(serviceTypeCombo);
        contentPanel.add(descriptionLabel);
        contentPanel.add(descriptionArea);
        contentPanel.add(locationLabel);
        contentPanel.add(locationField);
        contentPanel.add(urgencyLabel);
        contentPanel.add(urgencyCombo);
        contentPanel.add(expectedCompletionLabel);
        contentPanel.add(expectedCompletionDateField); // Changed here
        contentPanel.add(contactInfoLabel);
        contentPanel.add(contactInfoField);
        contentPanel.add(submitButton);

        // Set the window visibility
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }

    // Utility methods for consistent styling
    private JLabel createLabel(String text, int x, int y, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 300, 20);
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

    private JFormattedTextField createDateTextField(int x, int y, int width, int height) {
        JFormattedTextField dateField = new JFormattedTextField();
        MaskFormatter dateFormatter;
        try {
            dateFormatter = new MaskFormatter("####-##-##");
            dateFormatter.setPlaceholderCharacter('_');
            dateFormatter.setValidCharacters("0123456789");
            dateFormatter.install(dateField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dateField.setBounds(x, y, width, height);
        styleTextField(dateField);
        return dateField;
    }

    private void styleTextField(JComponent textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
