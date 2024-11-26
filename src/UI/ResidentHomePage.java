package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResidentHomePage extends JFrame {
    public ResidentHomePage() {
        setTitle("Resident Home Page");
        setSize(500, 600); // Adjusted size for better layout
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

        // Adjusted x position of the title label to center it with a slight offset to the right
        int titleLabelWidth = 400;  // Width of the title label
        int frameWidth = 500; // Width of the JFrame
        int offsetX = 75;  // Adjust this value to push the title slightly to the right

        // Center the label horizontally and add the offset
        int xPosition = (frameWidth - titleLabelWidth) / 2 + offsetX;

        JLabel titleLabel = new JLabel("Resident Dashboard");
        titleLabel.setBounds(xPosition, 50, titleLabelWidth, 40); // Adjusted position for better look
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(titleLabel);

        // Adjusted y positions of the boxes to push them down as well
        createBox(panel, "Reserve Facility", 30, 120, e -> new RecreationFacilityForm().setVisible(true));
        createBox(panel, "Register Property", 30, 210, e -> new PropertyRegistrationForm().setVisible(true));
        createBox(panel, "Submit Feedback", 30, 300, e -> new PublicFeedbackForm().setVisible(true));
        createBox(panel, "Pay Bills", 30, 390, e -> new UtilityBillForm().setVisible(true));
        createBox(panel, "Request Service", 250, 120, e -> new PublicServiceRequestForm().setVisible(true));
        createBox(panel, "Get License", 250, 210, e -> new BusinessLicenseForm().setVisible(true));
        createBox(panel, "Vote Digitally", 250, 300, e -> new DigitalVotingForm().setVisible(true));
        createBox(panel, "Report Incident", 250, 390, e -> new ReportCrimeOrTrafficIncidentForm().setVisible(true));

        createButton(this, panel, "Logout", 200, 490, e -> new HomeScreen().setVisible(true));

        
        // Set the panel as the content pane of the JFrame
        setContentPane(panel);

        // Make the window visible
        setVisible(true);
    }

    /**
     * Utility method to create and style boxed buttons.
     */
    private void createBox(JPanel panel, String text, int x, int y, ActionListener action) {
        JPanel box = new JPanel();
        box.setBounds(x, y, 180, 70); // Adjusted size of the box
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
//    public static void main(String[] args) {
//        new ResidentHomePage();
//    }
}