package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DomainEntity.Resident;

import BusinessLogic.BusinessLogicLayer;

public class ResidentSignupScreen extends JFrame {
    public ResidentSignupScreen() {
        setTitle("Resident Signup");
        setSize(670, 600); // Increased height for better visibility of submit button and back button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Custom content panel with gradient background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(0, 102, 153), 0, getHeight(), new Color(204, 229, 255)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        // Left side image (Society Image) adjusted to fill the screen height and touch the left border
        JLabel pictureLabel = createImageLabel("/society_image.jpg", 0, 0, 350, this.getHeight());
        if (pictureLabel != null) {
            contentPanel.add(pictureLabel);
        }

        // Title Label
        JLabel titleLabel = new JLabel("Resident Signup");
        titleLabel.setBounds(400, 20, 200, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        contentPanel.add(titleLabel);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 200;
        int fieldHeight = 30;

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:", 400, 70, labelFont);
        JTextField nameField = createTextField(400, 100, fieldWidth, fieldHeight);

        JLabel ageLabel = createLabel("Age:", 400, 140, labelFont);
        JTextField ageField = createTextField(400, 170, fieldWidth, fieldHeight);

        JLabel genderLabel = createLabel("Gender:", 400, 210, labelFont);
        JTextField genderField = createTextField(400, 240, fieldWidth, fieldHeight);

        JLabel usernameLabel = createLabel("Username:", 400, 280, labelFont);
        JTextField usernameField = createTextField(400, 310, fieldWidth, fieldHeight);

        JLabel passwordLabel = createLabel("Password:", 400, 350, labelFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 380, fieldWidth, fieldHeight);
        styleTextField(passwordField);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(400, 440, fieldWidth, fieldHeight);
        styleButton(submitButton);

        // Add components to the panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(genderLabel);
        contentPanel.add(genderField);
        contentPanel.add(usernameLabel);
        contentPanel.add(usernameField);
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField);
        contentPanel.add(submitButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(450, 490, 100, 30); // Adjusted position for new height
        styleButton(backButton);
        contentPanel.add(backButton);

        // Back button action listener
        backButton.addActionListener(e -> {
            dispose();
            new HomeScreen().setVisible(true);
        });

        // Submit button action listener
submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String ageText = ageField.getText();
                String gender = genderField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                int age = Integer.parseInt(ageText);
                
                Resident r = new Resident(name, age, gender, username, password); 
                
                if (!name.isEmpty() && !ageText.isEmpty() && !gender.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    try {
                        
                        BusinessLogicLayer businessLogic = new BusinessLogicLayer();
                        if (businessLogic.isUsernameTaken(username)) {
                            JOptionPane.showMessageDialog(null, "Username is already taken", "Signup Error", JOptionPane.ERROR_MESSAGE);
                        } else if (businessLogic.insertResident(r)) {
                            JOptionPane.showMessageDialog(null, "Signup Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            new ResidentHomePage().setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Signup Failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Age must be a number", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Signup Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Make the screen resizable
        setResizable(true);

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

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JLabel createImageLabel(String resourcePath, int x, int y, int width, int height) {
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(getClass().getResource(resourcePath));
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + resourcePath);
        }

        if (imageIcon == null || imageIcon.getIconWidth() <= 0) {
            System.err.println("Unable to load image: " + resourcePath);
            return null; // Return null if the image cannot be loaded
        }

        // Scale the image to fit the desired size
        Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(x, y, width, height);
        return imageLabel;
    }
}