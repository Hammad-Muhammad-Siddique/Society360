package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import DomainEntity.CrimeOrIncident;


public class ReportCrimeOrTrafficIncidentForm extends JFrame {
    public ReportCrimeOrTrafficIncidentForm() {
        setTitle("Report Crime or Traffic Incident");
        setSize(500, 500); // Reduced height to fit components
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

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 20, 20, labelFont);
        JTextField nameField = createTextField(170, 20, fieldWidth, fieldHeight);

        JLabel cnicLabel = createLabel("Username:", 20, 60, labelFont);
        JTextField cnicField = createTextField(170, 60, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 20, 100, labelFont);
        JTextField ageField = createTextField(170, 100, fieldWidth, fieldHeight);

        JLabel locationLabel = createLabel("Location:", 20, 140, labelFont);
        JTextField locationField = createTextField(170, 140, fieldWidth, fieldHeight);

        JLabel descriptionLabel = createLabel("Description:", 20, 180, labelFont);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setBounds(170, 180, fieldWidth, 60);
        styleTextField(descriptionArea);

        JLabel dateLabel = createLabel("Date (YYYY-MM-DD):", 20, 260, labelFont);
        JFormattedTextField dateField = createDateField(170, 260, fieldWidth, fieldHeight);

        JLabel timeLabel = createLabel("Time (HH:MM:SS):", 20, 300, labelFont);
        JFormattedTextField timeField = createTimeField(170, 300, fieldWidth, fieldHeight);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(170, 380, 150, 30);
        styleButton(submitButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(locationLabel);
        contentPanel.add(locationField);
        contentPanel.add(descriptionLabel);
        contentPanel.add(descriptionArea);
        contentPanel.add(dateLabel);
        contentPanel.add(dateField);
        contentPanel.add(timeLabel);
        contentPanel.add(timeField);
        contentPanel.add(submitButton);

        // Submit button action listener
submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String username = cnicField.getText(); // "Username"
        String ageText = ageField.getText();
        String location = locationField.getText();
        String description = descriptionArea.getText();
        String date = dateField.getText();
        String time = timeField.getText();

        int age = Integer.parseInt(ageText);
        
        CrimeOrIncident c = new CrimeOrIncident(name, age, username, location, description, date, time);
        
        if (!name.isEmpty() && !username.isEmpty() && !ageText.isEmpty() && !location.isEmpty() &&
                !description.isEmpty() && !date.isEmpty() && !time.isEmpty()) {

                 // Convert age to integer
                    // Call the function from BusinessLogicLayer
                    BusinessLogicLayer businessLogic = new BusinessLogicLayer();
                    boolean success = businessLogic.submitCrimeOrIncidentReport(c);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Report submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Close the form
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to submit the report. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                

        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});


        setLocationRelativeTo(null); // Center the form on the screen
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

    private JFormattedTextField createTimeField(int x, int y, int width, int height) {
        JFormattedTextField timeField = null;
        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##:##");
            timeFormatter.setPlaceholderCharacter('_');
            timeField = new JFormattedTextField(timeFormatter);
            timeField.setBounds(x, y, width, height);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        styleTextField(timeField);
        return timeField;
    }

    private void styleTextField(JComponent textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    // Method to validate date format (yyyy-MM-dd)
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Method to validate time format (HH:mm:ss)
    private boolean isValidTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
        sdf.setLenient(false);
        try {
            sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
