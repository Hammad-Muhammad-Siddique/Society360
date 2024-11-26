package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

import DomainEntity.Maintenance;


public class InfrastructureMaintenanceForm extends JFrame {

    public InfrastructureMaintenanceForm() {
        setTitle("Schedule Infrastructure Maintenance");
        setSize(450, 600); // Increased the height to 600
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Content panel without scrolling
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

        // Add a title label
        JLabel titleLabel = new JLabel("Schedule Infrastructure Maintenance");
        titleLabel.setBounds(40, 20, 350, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(titleLabel);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 200;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 40, 70, labelFont);
        JTextField nameField = createTextField(180, 70, fieldWidth, fieldHeight);

        JLabel contactLabel = createLabel("Contact Number:", 40, 110, labelFont);
        JTextField contactField = createTextField(180, 110, fieldWidth, fieldHeight);

        JLabel locationLabel = createLabel("Location:", 40, 150, labelFont);
        JTextField locationField = createTextField(180, 150, fieldWidth, fieldHeight);

JLabel dateLabel = createLabel("Date of Maintenance:", 40, 190, labelFont);
JFormattedTextField dateField = createFormattedField("####-##-##", 180, 190, fieldWidth, fieldHeight);

JLabel timeLabel = createLabel("Time of Maintenance:", 40, 230, labelFont);
JFormattedTextField timeField = createFormattedField("##:##:##", 180, 230, fieldWidth, fieldHeight);


        JLabel typeLabel = createLabel("Type of Infrastructure:", 40, 270, labelFont);
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{
            "Transport", "Healthcare", "Electricity", "Water", "Sanitary", "Recreation"
        });
        typeComboBox.setBounds(180, 270, fieldWidth, fieldHeight);
        styleComboBox(typeComboBox);

        JLabel costLabel = createLabel("Allocated Cost:", 40, 310, labelFont);
        JTextField costField = createTextField(180, 310, fieldWidth, fieldHeight);

        JLabel descriptionLabel = createLabel("Description:", 40, 350, labelFont);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setBounds(180, 350, fieldWidth, 60);
        styleTextField(descriptionArea);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 420, fieldWidth, fieldHeight);
        styleButton(submitButton);

        // Submit button action listener
submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String designation = "Residence Admin"; // Replace with the actual designation logic
        String location = locationField.getText();
        String date = dateField.getText(); // Format: YYYY-MM-DD
        String time = timeField.getText(); // Format: HH:MM:SS
        String type = (String) typeComboBox.getSelectedItem();
        String cost = costField.getText();
        String description = descriptionArea.getText();

        int allocatedCost = Integer.parseInt(cost);
        
        Maintenance m = new Maintenance(name, designation, location, date, time, type, allocatedCost, description);
        
        if (!name.isEmpty() && !designation.isEmpty() && !location.isEmpty() &&
                !date.isEmpty() && !time.isEmpty() && !type.isEmpty() &&
                !cost.isEmpty() && !description.isEmpty()) {

            try {
                

                BusinessLogicLayer businessLogic = new BusinessLogicLayer();
                boolean isInserted = businessLogic.scheduleMaintenance(m);

                if (isInserted) {
                    JOptionPane.showMessageDialog(null, "Maintenance Scheduled Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the form after submission
                } else {
                    JOptionPane.showMessageDialog(null, "Error scheduling maintenance. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid cost. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});


        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 460, 100, 30); // Adjusted to align with the submit button
        styleButton(backButton);
        contentPanel.add(backButton);

        backButton.addActionListener(e -> {
            dispose();
            new AdminHomePage().setVisible(true);
        });

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(contactLabel);
        contentPanel.add(contactField);
        contentPanel.add(locationLabel);
        contentPanel.add(locationField);
        contentPanel.add(dateLabel);
        contentPanel.add(dateField);
        contentPanel.add(timeLabel);
        contentPanel.add(timeField);
        contentPanel.add(typeLabel);
        contentPanel.add(typeComboBox);
        contentPanel.add(costLabel);
        contentPanel.add(costField);
        contentPanel.add(descriptionLabel);
        contentPanel.add(descriptionArea);
        contentPanel.add(submitButton);

        // Set content pane
        setContentPane(contentPanel);

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
        comboBox.setBackground(Color.WHITE); // Set the background to white
        comboBox.setForeground(Color.BLACK); // Set the text color to black
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204))); // Add a border matching the theme
        comboBox.setFocusable(false); // Remove the focus highlight for better aesthetics
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    

    
// Utility method to create formatted text fields
private JFormattedTextField createFormattedField(String mask, int x, int y, int width, int height) {
    JFormattedTextField formattedField = null;
    try {
        MaskFormatter formatter = new MaskFormatter(mask);
        formatter.setPlaceholderCharacter('_'); // Set placeholder character for unfilled parts
        formattedField = new JFormattedTextField(formatter);
        formattedField.setBounds(x, y, width, height);
        styleTextField(formattedField);
    } catch (ParseException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error creating formatted field: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    return formattedField;
}
}
