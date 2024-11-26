package UI;

import javax.swing.*;
import Database.DatabaseConnection;

public class Main {
    public static void main(String[] args)
    {
        // Launching the HomeScreen as the main entry point of the application
        SwingUtilities.invokeLater(() -> {
            HomeScreen homeScreen = new HomeScreen();
            homeScreen.setVisible(true);
        });
        
        // Test the database connection and insert a record into the Students table
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connectDatabase();
    }
}

