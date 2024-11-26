package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypeScreen extends JFrame {
    public UserTypeScreen(String action) {
        setTitle("Select User Type");  // Removed action from title
        setSize(750, 450); // Adjusted size for a more balanced layout
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
        JLabel titleLabel = new JLabel("Select User Type");
        titleLabel.setBounds(420, 40, 250, 30); // Adjusted vertical position to better center
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        contentPanel.add(titleLabel);

        // Create Buttons
        JButton residentButton = new JButton("Resident");
        JButton adminButton = new JButton("Admin");
        JButton backButton = new JButton("Back");

        // Set button positions and sizes
        int buttonWidth = 200;
        int buttonHeight = 40;
        residentButton.setBounds(420, 100, buttonWidth, buttonHeight);
        adminButton.setBounds(420, 160, buttonWidth, buttonHeight);
        backButton.setBounds(420, 220, buttonWidth, 40); // Adjusted height for the back button

        // Style the buttons
        styleButton(residentButton);
        styleButton(adminButton);
        styleButton(backButton);

        // Add buttons to content panel
        contentPanel.add(residentButton);
        contentPanel.add(adminButton);
        contentPanel.add(backButton);

        // Add Action Listeners for the buttons
        residentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (action.equals("Signup")) {
                    new ResidentSignupScreen();
                } else {
                    new ResidentLoginScreen();
                }
                dispose();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (action.equals("Signup")) {
                    new AdminSignupScreen();
                } else {
                    new AdminLoginScreen();
                }
                dispose();
            }
        });

        // Back Button Action Listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the current window
                new HomeScreen().setVisible(true);
            }
        });

        // Make the screen resizable
        setResizable(false);

        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));  // Blue color for buttons
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