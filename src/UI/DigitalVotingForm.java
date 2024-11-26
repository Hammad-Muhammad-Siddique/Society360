package UI;

import BusinessLogic.BusinessLogicLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DomainEntity.DigitalVote;


public class DigitalVotingForm extends JFrame {

    public DigitalVotingForm() {
        setTitle("Digital Voting System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Scrollable content panel
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
        contentPanel.setPreferredSize(new Dimension(400, 700)); // Set preferred size for scrolling

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane);

        // Add a title label
        JLabel titleLabel = new JLabel("Digital Voting");
        titleLabel.setBounds(120, 20, 200, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        contentPanel.add(titleLabel);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 200;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 100, 70, labelFont);
        JTextField nameField = createTextField(100, 100, fieldWidth, fieldHeight);

        JLabel idLabel = createLabel("Username:", 100, 140, labelFont);
        JTextField idField = createTextField(100, 170, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 100, 210, labelFont);
        JTextField ageField = createTextField(100, 240, fieldWidth, fieldHeight);

        JLabel initiativeLabel = createLabel("Type of Initiative:", 100, 280, labelFont);
        JComboBox<String> initiativeComboBox = new JComboBox<>(new String[] {
            "Recreation", "Water", "Electricity", "Healthcare", "Sanitary", "Transport"
        });
        initiativeComboBox.setBounds(100, 310, fieldWidth, fieldHeight);
        styleComboBox(initiativeComboBox);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 380, fieldWidth, fieldHeight);
        styleButton(submitButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(idLabel);
        contentPanel.add(idField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(initiativeLabel);
        contentPanel.add(initiativeComboBox);
        contentPanel.add(submitButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 420, 100, 30);  // Positioning the back button below submit
        styleButton(backButton);
        contentPanel.add(backButton);

        // Back Button Action Listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Add your back navigation logic here if needed
            }
        });

        // Submit Button Action Listener
submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String age = ageField.getText().trim();
        String initiativeType = (String) initiativeComboBox.getSelectedItem();

        int ageValue = Integer.parseInt(age);
        
        DigitalVote d = new DigitalVote(name, ageValue, id, initiativeType);
        
        if (name.isEmpty() || id.isEmpty() || age.isEmpty() || initiativeType == null) {
            JOptionPane.showMessageDialog(null, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            if (ageValue <= 0) {
                JOptionPane.showMessageDialog(null, "Age must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BusinessLogicLayer logic = new BusinessLogicLayer();
            boolean success = logic.insertDigitalVote(d);

            if (success) {
                JOptionPane.showMessageDialog(null, "Vote Submitted Successfully!", "Submission Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to submit vote. Please try again.", "Submission Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number for Age.", "Input Error", JOptionPane.ERROR_MESSAGE);
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
}
