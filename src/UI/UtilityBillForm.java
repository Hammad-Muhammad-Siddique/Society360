package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import DomainEntity.Bill;


public class UtilityBillForm extends JFrame {
    
    private BusinessLogicLayer businessLogicLayer;
    
    public UtilityBillForm() {
        setTitle("Pay Utility Bill");
        setSize(600, 500);  // Increased width to make the screen wider
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        businessLogicLayer = new BusinessLogicLayer();  // Initialize BusinessLogicLayer

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
        contentPanel.setPreferredSize(new Dimension(500, 500));  // Adjusted to new width
        setContentPane(contentPanel);  // Set content panel without JScrollPane

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 300;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 20, 20, labelFont);
        JTextField nameField = createTextField(210, 20, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 20, 60, labelFont);
        JTextField ageField = createTextField(210, 60, fieldWidth, fieldHeight);

        JLabel cnicLabel = createLabel("Username:", 20, 100, labelFont);
        JTextField cnicField = createTextField(210, 100, fieldWidth, fieldHeight);

        JLabel BilltypeLabel = createLabel("Bill Type:", 20, 140, labelFont);
        JComboBox<String> BilltypeComboBox = new JComboBox<>(new String[]{"Electricity", "Water", "Gas"});
        BilltypeComboBox.setBounds(210, 140, fieldWidth, fieldHeight);
        styleComboBox(BilltypeComboBox);

        JLabel BillMonthtypeLabel = createLabel("Billing Month:", 20, 180, labelFont);
        JComboBox<String> BillMonthtypeComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        BillMonthtypeComboBox.setBounds(210, 180, fieldWidth, fieldHeight);
        styleComboBox(BillMonthtypeComboBox);

        JLabel billAmountLabel = createLabel("Bill Amount:", 20, 220, labelFont);
        JTextField billAmountField = createTextField(210, 220, fieldWidth, fieldHeight);

        JLabel paymentMethodLabel = createLabel("Payment Method:", 20, 260, labelFont);
        JComboBox<String> paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "Bank Transfer", "Cash"});
        paymentMethodComboBox.setBounds(210, 260, fieldWidth, fieldHeight);
        styleComboBox(paymentMethodComboBox);

        JLabel dueDateLabel = createLabel("Due Date (YYYY-MM-DD):", 20, 300, labelFont);
        JTextField dueDateField = createFormattedTextField("####-##-##", 210, 300, fieldWidth, fieldHeight);  // Mask for YYYY-MM-DD

        JLabel accountNumberLabel = createLabel("Account Number:", 20, 340, labelFont);
        JTextField accountNumberField = createTextField(210, 340, fieldWidth, fieldHeight);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(250, 380, 100, 30);
        styleButton(submitButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(BilltypeLabel);
        contentPanel.add(BilltypeComboBox);
        contentPanel.add(BillMonthtypeLabel);
        contentPanel.add(BillMonthtypeComboBox);
        contentPanel.add(billAmountLabel);
        contentPanel.add(billAmountField);
        contentPanel.add(paymentMethodLabel);
        contentPanel.add(paymentMethodComboBox);
        contentPanel.add(dueDateLabel);
        contentPanel.add(dueDateField);
        contentPanel.add(accountNumberLabel);
        contentPanel.add(accountNumberField);
        contentPanel.add(submitButton);

        // Submit button action listener
submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if any required field is empty
        if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || cnicField.getText().isEmpty() ||
                billAmountField.getText().isEmpty() || dueDateField.getText().isEmpty() ||
                accountNumberField.getText().isEmpty()) {
            // Show error message if any field is empty
            JOptionPane.showMessageDialog(null, "Please fill all fields before submitting.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                // Attempt to parse bill amount as a number
                double billAmount = Double.parseDouble(billAmountField.getText());
                if (billAmount < 0) {
                    throw new NumberFormatException("Negative value not allowed");
                }

                // Validate the date format YYYY-MM-DD
                String dueDateText = dueDateField.getText();
                if (!dueDateText.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    throw new ParseException("Invalid date format", 0);
                }

                Bill bill = new Bill(nameField.getText(), Integer.parseInt(ageField.getText()), cnicField.getText(), (String) BilltypeComboBox.getSelectedItem(), (String) BillMonthtypeComboBox.getSelectedItem(), Integer.parseInt(billAmountField.getText()), (String) paymentMethodComboBox.getSelectedItem(), dueDateField.getText(), Integer.parseInt(accountNumberField.getText()));
                
                // Call the method to insert utility bill
                boolean isInserted = businessLogicLayer.insertUtilityBill(bill);

                if (isInserted) {
                    JOptionPane.showMessageDialog(null, "Utility bill details submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to submit utility bill details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException | ParseException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid data.", "Error", JOptionPane.ERROR_MESSAGE);
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

    private JTextField createFormattedTextField(String mask, int x, int y, int width, int height) {
        JTextField formattedTextField = new JFormattedTextField(createMaskFormatter(mask));
        formattedTextField.setBounds(x, y, width, height);
        styleTextField(formattedTextField);
        return formattedTextField;
    }

    private MaskFormatter createMaskFormatter(String mask) {
        try {
            MaskFormatter maskFormatter = new MaskFormatter(mask);
            maskFormatter.setPlaceholderCharacter('_');
            return maskFormatter;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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
