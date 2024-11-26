package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupScreen extends JFrame {
    public SignupScreen() {
        setTitle("Signup Screen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton residentButton = new JButton("Resident");
        //JButton adminButton = new JButton("Admin");
        JButton backButton = new JButton("Back");
        
        residentButton.setBounds(75, 50, 200, 30);
        //adminButton.setBounds(200, 50, 100, 30);
        backButton.setBounds(130, 100, 100, 30);
        

        add(residentButton);
        //add(adminButton);
        add(backButton);

        residentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ResidentSignupScreen();

                dispose();
            }
        });
        
        /*
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
        */
        
        // Back Button Action Listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window (HomeScreen) and return to the previous screen
                dispose();  // Close the current HomeScreen
                
                new HomeScreen().setVisible(true);
                
            }
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
}