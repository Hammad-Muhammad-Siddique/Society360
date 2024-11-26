package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import BusinessLogic.BusinessLogicLayer;
import java.awt.*;
import java.util.List;

public class AdminDigitalVotesScreen extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private BusinessLogicLayer logic;

    public AdminDigitalVotesScreen() {
        setTitle("Digital Votes Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        logic = new BusinessLogicLayer();

        // Background panel with gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.BLUE, 0, getHeight(), Color.CYAN));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Title label
        JLabel titleLabel = new JLabel("Digital Votes Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Table to display votes
        model = new DefaultTableModel(new String[]{"Name", "Age", "Username", "Initiative Type"}, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Load votes into table
        populateTable();

        // Back button
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.setBounds(440, 500, 120, 40);
        backButton.addActionListener(e -> dispose());
        backgroundPanel.add(backButton, BorderLayout.SOUTH);
    }

    private void populateTable() {
        model.setRowCount(0); // Clear existing rows
        List<String[]> votes = logic.getDigitalVotes();
        for (String[] vote : votes) {
            model.addRow(vote);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
}
