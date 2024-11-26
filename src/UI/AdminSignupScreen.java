package UI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

import BusinessLogic.BusinessLogicLayer;
import DomainEntity.Admin;
import Database.DatabaseConnection;

public class AdminSignupScreen extends JFrame {
    private JTextArea detailsArea;  // TextArea to display the list of all admins

    public AdminSignupScreen() {
        setTitle("Change Personal Details");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Content panel
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

        // Add a title label
        JLabel titleLabel = new JLabel("Change Details");
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

        JLabel ageLabel = createLabel("Age:", 100, 140, labelFont);
        JTextField ageField = createTextField(100, 170, fieldWidth, fieldHeight);

        JLabel genderLabel = createLabel("Gender:", 100, 210, labelFont);
        JTextField genderField = createTextField(100, 240, fieldWidth, fieldHeight);

        JLabel usernameLabel = createLabel("Username:", 100, 280, labelFont);
        JTextField usernameField = createTextField(100, 310, fieldWidth, fieldHeight);

        JLabel passwordLabel = createLabel("Password:", 100, 350, labelFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 380, fieldWidth, fieldHeight);
        styleTextField(passwordField);

        // Add new Experience field
        JLabel experienceLabel = createLabel("Experience:", 100, 420, labelFont);
        JTextField experienceField = createTextField(100, 450, fieldWidth, fieldHeight);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 500, fieldWidth, fieldHeight);
        styleButton(submitButton);

        // Show Details button
        JButton showDetailsButton = new JButton("Show Details");
        showDetailsButton.setBounds(100, 540, fieldWidth, fieldHeight);
        styleButton(showDetailsButton);

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
        contentPanel.add(experienceLabel);
        contentPanel.add(experienceField);
        contentPanel.add(submitButton);
        contentPanel.add(showDetailsButton);

        // Initialize the JTextArea to display admin details
        detailsArea = new JTextArea();
        detailsArea.setBounds(100, 580, 200, 150);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsArea.setBackground(Color.WHITE);
        contentPanel.add(detailsArea);

        // Submit button action listener
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String age = ageField.getText();
            String gender = genderField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String experience = experienceField.getText();

            if (!name.isEmpty() && !age.isEmpty() && !gender.isEmpty() && !username.isEmpty() && !password.isEmpty() && !experience.isEmpty()) {
                // Get the current admin instance from the Singleton pattern
                Admin admin = Admin.getInstance(name, Integer.parseInt(age), gender, username, password, experience);

                // Update the current admin instance details using the new values
                admin.updateAdminDetails(name, Integer.parseInt(age), gender, username, password, experience);

                // Update admin details in the database
                BusinessLogicLayer logicLayer = new BusinessLogicLayer();
                boolean isUpdated = logicLayer.updateAdminDetails(admin);

                if (isUpdated) {
                    JOptionPane.showMessageDialog(this, "Admin details updated successfully.");
                    dispose();
                    // new AdminHomePage().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating details. Please try again.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Signup Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Show Details button action listener
        showDetailsButton.addActionListener(e -> {
            // Fetch all admin records from the database
            ArrayList<Admin> admins = getAllAdminsFromDatabase();
            if (admins != null && !admins.isEmpty()) {
                // Display the list of admins in the details area
                StringBuilder detailsText = new StringBuilder("Admin Details:\n\n");
                for (Admin admin : admins) {
                    detailsText.append("Name: ").append(admin.getName())
                            .append("\nAge: ").append(admin.getAge())
                            .append("\nGender: ").append(admin.getGender())
                            .append("\nUsername: ").append(admin.getUsername())
                            .append("\nExperience: ").append(admin.getExperience())
                            .append("\n--------------------------\n");
                }
                detailsArea.setText(detailsText.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No admin details found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Make the screen non-resizable
        setResizable(false);
        setLocationRelativeTo(null);
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

    // Method to retrieve all admin details from the database
    private ArrayList<Admin> getAllAdminsFromDatabase() {
        ArrayList<Admin> admins = new ArrayList<>();
        try {
            Connection conn = DatabaseConnection.connectDatabase();
            String query = "SELECT * FROM Admin";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String gender = rs.getString("Gender");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String experience = rs.getString("Experience");
                Admin admin = Admin.getInstance(name, age, gender, username, password, experience);
                admins.add(admin);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }
}
