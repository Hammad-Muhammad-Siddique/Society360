package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;

import DomainEntity.Feedback;


public class PublicFeedbackForm extends JFrame {
    public PublicFeedbackForm() {
        setTitle("Submit Public Feedback");
        setSize(625, 500); // Adjusted size to accommodate all fields and buttons
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
        contentPanel.setPreferredSize(new Dimension(550, 600));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 300;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 20, 20, labelFont);
        JTextField nameField = createTextField(240, 20, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 20, 60, labelFont);
        JTextField ageField = createTextField(240, 60, fieldWidth, fieldHeight);

        JLabel cnicLabel = createLabel("Username:", 20, 100, labelFont);
        JTextField cnicField = createTextField(240, 100, fieldWidth, fieldHeight);

        JLabel topicLabel = createLabel("Feedback Topic:", 20, 140, labelFont);
        JComboBox<String> topicComboBox = new JComboBox<>(new String[]{"Cleanliness", "Security", "Infrastructure", "Other"});
        topicComboBox.setBounds(240, 140, fieldWidth, fieldHeight);
        styleComboBox(topicComboBox);

        JLabel detailsLabel = createLabel("Feedback Details:", 20, 180, labelFont);
        JTextArea detailsArea = new JTextArea();
        detailsArea.setBounds(240, 180, fieldWidth, 60);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        styleTextArea(detailsArea);

        JLabel urgencyLabel = createLabel("Urgency Level:", 20, 250, labelFont);
        JComboBox<String> urgencyComboBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        urgencyComboBox.setBounds(240, 250, fieldWidth, fieldHeight);
        styleComboBox(urgencyComboBox);

        JLabel contactLabel = createLabel("Contact Method:", 20, 290, labelFont);
        JComboBox<String> contactComboBox = new JComboBox<>(new String[]{"Phone", "Email"});
        contactComboBox.setBounds(240, 290, fieldWidth, fieldHeight);
        styleComboBox(contactComboBox);

        JLabel dateLabel = createLabel("Feedback Date (YYYY-MM-DD):", 20, 330, labelFont);
        JFormattedTextField dateField = createFormattedTextField("####-##-##", 240, 330, fieldWidth, fieldHeight);
        
        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(220, 400, 100, 30);
        styleButton(submitButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(topicLabel);
        contentPanel.add(topicComboBox);
        contentPanel.add(detailsLabel);
        contentPanel.add(detailsArea);
        contentPanel.add(urgencyLabel);
        contentPanel.add(urgencyComboBox);
        contentPanel.add(contactLabel);
        contentPanel.add(contactComboBox);
        contentPanel.add(dateLabel);
        contentPanel.add(dateField);
        contentPanel.add(submitButton);

submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if any required field is empty
        if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || cnicField.getText().isEmpty() ||
                detailsArea.getText().isEmpty() || dateField.getText().isEmpty()) {
            // Show error message if any field is empty
            JOptionPane.showMessageDialog(null, "Please fill in all fields before submitting.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Get values from form fields
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String username = cnicField.getText().trim();
            String feedbackTopic = (String) topicComboBox.getSelectedItem();
            String feedbackDetails = detailsArea.getText().trim();
            String urgencyLevel = (String) urgencyComboBox.getSelectedItem();
            String contactMethod = (String) contactComboBox.getSelectedItem();
            String feedbackDate = dateField.getText().trim();

            Feedback f = new Feedback(name, age, username, feedbackTopic, feedbackDetails, urgencyLevel, contactMethod, feedbackDate);
            
            // Create BusinessLogicLayer object and insert feedback
            BusinessLogicLayer businessLogicLayer = new BusinessLogicLayer();
            boolean success = businessLogicLayer.insertPublicFeedback(f);

            // Show success or failure message
            if (success) {
                JOptionPane.showMessageDialog(null, "Feedback submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
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
        label.setBounds(x, y, 300, 20); // Adjusted width for longer text
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

    private JFormattedTextField createFormattedTextField(String mask, int x, int y, int width, int height) {
        try {
            MaskFormatter maskFormatter = new MaskFormatter(mask);
            maskFormatter.setPlaceholderCharacter('_');
            JFormattedTextField formattedTextField = new JFormattedTextField(maskFormatter);
            formattedTextField.setBounds(x, y, width, height);
            styleTextField(formattedTextField);
            return formattedTextField;
        } catch (Exception e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private void styleTextField(JComponent textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
