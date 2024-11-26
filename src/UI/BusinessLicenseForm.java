package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import DomainEntity.BusinessLicense;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class BusinessLicenseForm extends JFrame {
    public BusinessLicenseForm() {
        setTitle("Acquire Business License Form");
        setSize(600, 600);
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
        int fieldWidth = 200;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 20, 20, labelFont);
        JTextField nameField = createTextField(270, 20, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 20, 60, labelFont);
        JTextField ageField = createTextField(270, 60, fieldWidth, fieldHeight);

        JLabel cnicLabel = createLabel("Username:", 20, 100, labelFont);
        JTextField cnicField = createTextField(270, 100, fieldWidth, fieldHeight);

        JLabel businessNameLabel = createLabel("Business Name:", 20, 140, labelFont);
        JTextField businessNameField = createTextField(270, 140, fieldWidth, fieldHeight);

        JLabel businessTypeLabel = createLabel("Business Type:", 20, 180, labelFont);
        JComboBox<String> businessTypeCombo = new JComboBox<>(new String[]{"Retail", "Service", "Manufacturing"});
        businessTypeCombo.setBounds(270, 180, fieldWidth, fieldHeight);
        styleComboBox(businessTypeCombo);

        JLabel businessAddressLabel = createLabel("Business Address:", 20, 220, labelFont);
        JTextField businessAddressField = createTextField(270, 220, fieldWidth, fieldHeight);

        JLabel licenseTypeLabel = createLabel("License Type:", 20, 260, labelFont);
        JComboBox<String> licenseTypeCombo = new JComboBox<>(new String[]{"Operational", "Trade", "Special"});
        licenseTypeCombo.setBounds(270, 260, fieldWidth, fieldHeight);
        styleComboBox(licenseTypeCombo);

        JLabel annualRevenueLabel = createLabel("Expected Revenue:", 20, 300, labelFont);
        JTextField annualRevenueField = createTextField(270, 300, fieldWidth, fieldHeight);

        JLabel numEmployeesLabel = createLabel("Number of Employees:", 20, 340, labelFont);
        JTextField numEmployeesField = createTextField(270, 340, fieldWidth, fieldHeight);

        JLabel businessStartDateLabel = createLabel("Business Start Date (YYYY-MM-DD):", 20, 380, labelFont);
        JFormattedTextField businessStartDateField = createDateField(270, 380, fieldWidth, fieldHeight);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(290, 460, 150, 30);
        styleButton(submitButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 460, 150, 30);
        styleButton(backButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(businessNameLabel);
        contentPanel.add(businessNameField);
        contentPanel.add(businessTypeLabel);
        contentPanel.add(businessTypeCombo);
        contentPanel.add(businessAddressLabel);
        contentPanel.add(businessAddressField);
        contentPanel.add(licenseTypeLabel);
        contentPanel.add(licenseTypeCombo);
        contentPanel.add(annualRevenueLabel);
        contentPanel.add(annualRevenueField);
        contentPanel.add(numEmployeesLabel);
        contentPanel.add(numEmployeesField);
        contentPanel.add(businessStartDateLabel);
        contentPanel.add(businessStartDateField);
        contentPanel.add(submitButton);
        contentPanel.add(backButton);

        // Submit button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate the fields
                if (nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int age = Integer.parseInt(ageField.getText().trim());
                    if (age <= 0) {
                        JOptionPane.showMessageDialog(null, "Age must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (cnicField.getText().trim().length() < 3) {
                    JOptionPane.showMessageDialog(null, "Username must be greater than 2 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (businessNameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Business Name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (businessAddressField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Business Address is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double annualRevenue = Double.parseDouble(annualRevenueField.getText().trim());
                    if (annualRevenue < 0) {
                        JOptionPane.showMessageDialog(null, "Annual Revenue cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for Annual Revenue.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int numEmployees = Integer.parseInt(numEmployeesField.getText().trim());
                    if (numEmployees < 0) {
                        JOptionPane.showMessageDialog(null, "Number of Employees cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for Number of Employees.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (businessStartDateField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Business Start Date is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

        String name = nameField.getText().trim();
        int age = Integer.parseInt(ageField.getText().trim());
        String username = cnicField.getText().trim();
        String businessName = businessNameField.getText().trim();
        String businessType = (String) businessTypeCombo.getSelectedItem();
        String businessAddress = businessAddressField.getText().trim();
        String licenseType = (String) licenseTypeCombo.getSelectedItem();
        int estimatedAnnualRevenue = Integer.parseInt(annualRevenueField.getText().trim());
        int employees = Integer.parseInt(numEmployeesField.getText().trim());
        String startDate = businessStartDateField.getText().trim();

        BusinessLicense BL = new BusinessLicense(name, age, username, businessName, businessType, businessAddress,
            licenseType, estimatedAnnualRevenue, employees, startDate);
        
        BusinessLogicLayer logicLayer = new BusinessLogicLayer();
        boolean isInserted = logicLayer.insertBusinessLicense(
            BL
        );

        if (isInserted) {
            JOptionPane.showMessageDialog(null, "Business License request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to submit the business license request.", "Error", JOptionPane.ERROR_MESSAGE);
        }            }
        });

        // Back button action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResidentHomePage().setVisible(true);
                dispose(); // Close the current form
            }
        });

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

    private JFormattedTextField createDateField(int x, int y, int width, int height) {
        JFormattedTextField dateField = null;
        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            dateFormatter.setPlaceholderCharacter('_');
            dateField = new JFormattedTextField(dateFormatter);
            dateField.setBounds(x, y, width, height);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        styleTextField(dateField);
        return dateField;
    }

    private void styleTextField(JComponent textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder());
    }
    


}
