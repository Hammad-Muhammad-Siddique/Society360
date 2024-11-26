package UI;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {
    public HomeScreen() {
        setTitle("City Management System");
        setSize(700, 500); // Reduced screen size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

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

        // Society picture on the left
        JLabel pictureLabel = createImageLabel("/society_image.jpg", 0, 0, 350, getHeight());
        if (pictureLabel != null) {
            contentPanel.add(pictureLabel);
        }

        // Logo Label
        JLabel logoLabel = new JLabel("Society 360");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 40));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBounds(360, 150, 300, 50); // Adjusted x-coordinate to push left

        // Signup and Login buttons
        JButton signupButton = new JButton("Signup");
        JButton loginButton = new JButton("Login");

        signupButton.setBounds(400, 250, 120, 40); // Positioned under the logo
        loginButton.setBounds(530, 250, 120, 40);

        styleButton(signupButton);
        styleButton(loginButton);


        // Add components to the content panel
        contentPanel.add(logoLabel);
        contentPanel.add(signupButton);
        contentPanel.add(loginButton);


        // Button action listeners
        signupButton.addActionListener(e -> {
            new ResidentSignupScreen();
            dispose();
        });

        loginButton.addActionListener(e -> {
            new UserTypeScreen("Login");
            dispose();
        });

        // Set the content panel as the main content
        setContentPane(contentPanel);

        // Make the screen resizable and centered
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 51, 102));
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

    /*
    public static void main(String[] args) {
        new HomeScreen();
    }
    */
}