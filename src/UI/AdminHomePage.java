package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomePage extends JFrame {
    public AdminHomePage() {
        setTitle("Admin Home Page");
        setSize(500, 800); // Increased the size of the frame to accommodate the longer panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a JPanel with gradient background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(0, 102, 204), 0, getHeight(), new Color(0, 204, 255)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        // Title label with adjusted position and font
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setBounds(150, 20, 200, 40); // Adjusted position for better look
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(titleLabel);

        // Create buttons as boxes with actions
        createBox(panel, "Schedule Infrastructure Maintenance", 50, 70, e -> new InfrastructureMaintenanceForm().setVisible(true));
        createBox(panel, "View Crime & Incident Reports", 50, 120, e -> new ViewCrimeOrIncidentReportsScreen().setVisible(true));
        createBox(panel, "Digital Voting", 50, 170, e -> new AdminDigitalVotesScreen().setVisible(true));
        createBox(panel, "Manage Institutions", 50, 220, e -> new ManageInstitutionForm().setVisible(true));
        createBox(panel, "Manage Healthcare", 50, 270, e -> new ManageHealthCareForm().setVisible(true));
        createBox(panel, "View Property Registration Requests", 50, 320, e -> new PropertyRequestsScreen().setVisible(true));
        createBox(panel, "View Recreation Facility Reservation Requests", 50, 370, e -> new RecreationRequestsScreen().setVisible(true));
        createBox(panel, "View Public Feedback", 50, 420, e -> new ViewPublicFeedbackScreen().setVisible(true));
        createBox(panel, "View Public Service Requests", 50, 470, e -> new PublicServiceRequestsScreen().setVisible(true));
        createBox(panel, "View Business License Requests", 50, 520, e -> new ViewBusinessLicenseRequestsScreen().setVisible(true));
        createBox(panel, "View All Residents", 50, 570, e -> new ViewAllResidentsScreen().setVisible(true));
        createBox(panel, "Change Personal Details", 50, 620, e -> new AdminSignupScreen().setVisible(true));

        
        createButton(this, panel, "Logout", 200, 700, e -> new HomeScreen().setVisible(true));
        
        // Wrap the panel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set the scroll pane as the content pane of the JFrame
        setContentPane(scrollPane);

        // Make the window visible
        setVisible(true);
    }

    /**
     * Utility method to create and style boxed buttons.
     */
    private void createBox(JPanel panel, String text, int x, int y, ActionListener action) {
        JPanel box = new JPanel();
        box.setBounds(x, y, 400, 60); // Adjusted size of the box
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2)); // Border for box
        box.setLayout(new BorderLayout());
        box.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel("<html><center>" + text + "</center></html>", SwingConstants.CENTER);
        label.setForeground(new Color(0, 102, 204));
        label.setFont(new Font("Arial", Font.BOLD, 12));

        box.add(label, BorderLayout.CENTER);

        // Add click effect
        box.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                box.setBackground(new Color(0, 204, 255));
                label.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                box.setBackground(Color.WHITE);
                label.setForeground(new Color(0, 102, 204));
            }
        });

        panel.add(box);
    }

    private void createButton(JFrame currentFrame, JPanel panel, String text, int x, int y, ActionListener action) {
    JPanel box = new JPanel();
    box.setBounds(x, y, 100, 50); // Adjusted size of the box
    box.setBackground(Color.WHITE);
    box.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2)); // Border for box
    box.setLayout(new BorderLayout());
    box.setCursor(new Cursor(Cursor.HAND_CURSOR));

    JLabel label = new JLabel("<html><center>" + text + "</center></html>", SwingConstants.CENTER);
    label.setForeground(new Color(0, 102, 204));
    label.setFont(new Font("Arial", Font.BOLD, 12));

    box.add(label, BorderLayout.CENTER);

    // Add click effect
    box.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            currentFrame.dispose(); // Dispose of the current frame
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            box.setBackground(new Color(0, 204, 255));
            label.setForeground(Color.WHITE);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            box.setBackground(Color.WHITE);
            label.setForeground(new Color(0, 102, 204));
        }
    });

    panel.add(box);
}
    
    // Uncomment this to run the application
    // public static void main(String[] args) {
    //     new AdminHomePage();
    // }
}