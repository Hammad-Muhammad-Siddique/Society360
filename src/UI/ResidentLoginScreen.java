package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BusinessLogic.BusinessLogicLayer; 
        
public class ResidentLoginScreen extends JFrame {
    public ResidentLoginScreen() {
        setTitle("Resident Login");
        setSize(750, 450); // Adjusted height to make the layout more balanced
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Custom content panel with gradient background
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
        setContentPane(contentPanel);

        // Left side image (Society Image) adjusted to fit the height but with reduced width
        JLabel pictureLabel = createImageLabel("/society_image.jpg", 0, 0, 350, this.getHeight());
        if (pictureLabel != null) {
            contentPanel.add(pictureLabel);
        }

        // Title Label - Adjusted to push it down
        JLabel titleLabel = new JLabel("Resident Login");
        titleLabel.setBounds(420, 40, 200, 30); // Adjusted vertical position
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        contentPanel.add(titleLabel);

        // Styling common font and field size
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        int fieldWidth = 250;
        int fieldHeight = 30;

        // Create and style labels and fields - Adjusted vertical positions
        JLabel usernameLabel = createLabel("Username:", 420, 100, labelFont); // Pushed down
        JTextField usernameField = createTextField(420, 130, fieldWidth, fieldHeight); // Pushed down

        JLabel passwordLabel = createLabel("Password:", 420, 170, labelFont); // Pushed down
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(420, 200, fieldWidth, fieldHeight); // Pushed down
        styleTextField(passwordField);

        // Login button - Adjusted vertical position
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(420, 260, fieldWidth, fieldHeight); // Pushed down
        styleButton(loginButton);

        // Add components to the panel
        contentPanel.add(usernameLabel);
        contentPanel.add(usernameField);
        contentPanel.add(passwordLabel);
        contentPanel.add(passwordField);
        contentPanel.add(loginButton);

        // Back button - Adjusted vertical position
        JButton backButton = new JButton("Back");
        backButton.setBounds(420, 310, fieldWidth, 30); // Pushed down
        styleButton(backButton);
        contentPanel.add(backButton);

        // Back button action listener
        backButton.addActionListener(e -> {
            dispose();
            new UserTypeScreen("Login").setVisible(true);
        });

        BusinessLogicLayer businessLogicLayer = new BusinessLogicLayer();
        
        // Login button action listener
loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, "Please fill in both username and password fields.", 
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            } else {
                if (businessLogicLayer.authenticateResident(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new ResidentHomePage().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                        null, "Invalid username or password.", 
                        "Login Failed", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Make the screen resizable
        setResizable(false);

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