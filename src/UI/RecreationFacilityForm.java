package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;

import DomainEntity.RecreationFacility;

public class RecreationFacilityForm extends JFrame {
    public RecreationFacilityForm() {
        setTitle("Reserve Recreation Facility");
        setSize(600, 400); // Adjusted size to fit all fields and button
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
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        setContentPane(scrollPane);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 20, 20, labelFont);
        JTextField nameField = createTextField(260, 20, 250, 30);

        JLabel ageLabel = createLabel("Age:", 20, 60, labelFont);
        JTextField ageField = createTextField(260, 60, 250, 30);

        JLabel usernameLabel = createLabel("Username:", 20, 100, labelFont);
        JTextField usernameField = createTextField(260, 100, 250, 30);

        JLabel facilityLabel = createLabel("Facility Name:", 20, 140, labelFont);
        JComboBox<String> facilityCombo = new JComboBox<>(new String[]{"Gym", "Swimming Pool", "Tennis Court", "Cricket Ground", "Football Ground"});
        facilityCombo.setBounds(260, 140, 250, 30);

        JLabel dateLabel = createLabel("Reservation Date (YYYY-MM-DD):", 20, 180, labelFont);
        JFormattedTextField dateField = createFormattedTextField("####-##-##", 260, 180, 250, 30);

        JLabel timeLabel = createLabel("Reservation Time (HH:MM):", 20, 220, labelFont);
        JFormattedTextField timeField = createFormattedTextField("##:##", 260, 220, 250, 30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(200, 280, 100, 30);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(usernameLabel);
        contentPanel.add(usernameField);
        contentPanel.add(facilityLabel);
        contentPanel.add(facilityCombo);
        contentPanel.add(dateLabel); 
        contentPanel.add(dateField);
        contentPanel.add(timeLabel);
        contentPanel.add(timeField);
        contentPanel.add(submitButton);
        styleButton(submitButton);
        // Submit button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                        dateField.getText().isEmpty() || timeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String username = usernameField.getText();
                    String facilityName = facilityCombo.getSelectedItem().toString();
                    String reservationDate = dateField.getText();
                    String reservationTime = timeField.getText();

                    RecreationFacility RF = new RecreationFacility(name, age, username, facilityName, reservationDate, reservationTime);
                    
                    BusinessLogicLayer businessLogic = new BusinessLogicLayer();
                    boolean success = businessLogic.reserveFacility(RF);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Reservation submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        nameField.setText("");
                        ageField.setText("");
                        usernameField.setText("");
                        dateField.setText("");
                        timeField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to submit reservation.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
    
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
        return textField;
    }

    private JFormattedTextField createFormattedTextField(String mask, int x, int y, int width, int height) {
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
            JFormattedTextField field = new JFormattedTextField(formatter);
            field.setBounds(x, y, width, height);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return new JFormattedTextField(); 
        }
    }
}
