package BusinessLogic;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import DomainEntity.*;


public class BusinessLogicLayer {
    private final DatabaseConnection dbConnection;

    public BusinessLogicLayer() {
        dbConnection = new DatabaseConnection();
    }

    // Method to check if a username already exists
    public boolean isUsernameTaken(String username) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.userNameCheckQuery)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to insert a new resident
    public boolean insertResident(Resident r) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.residentInsertQuery)) {
            preparedStatement.setString(1, r.getName());
            preparedStatement.setInt(2, r.getAge());
            preparedStatement.setString(3, r.getGender());
            preparedStatement.setString(4, r.getUsername());
            preparedStatement.setString(5, r.getPassword());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to reserve a facility
    public boolean reserveFacility(RecreationFacility RF) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.reserveFacilityInsertQuery)) {
            preparedStatement.setString(1, RF.getName());
            preparedStatement.setInt(2, RF.getAge());
            preparedStatement.setString(3, RF.getUsername());
            preparedStatement.setString(4, RF.getFacility());
            preparedStatement.setString(5, RF.getDate());
            preparedStatement.setString(6, RF.getTime());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
        public boolean insertProperty(Property p) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.propertyInsertQuery)) {
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getAge());
            preparedStatement.setString(3, p.getUsername());
            preparedStatement.setString(4, p.getPropertyAddress());
            preparedStatement.setString(5, p.getPropertyType());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
        
     // Method to insert public feedback
    public boolean insertPublicFeedback(Feedback f) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.publicFeedbackInsertQuery)) {
            preparedStatement.setString(1, f.getName());
            preparedStatement.setInt(2, f.getAge());
            preparedStatement.setString(3, f.getUsername());
            preparedStatement.setString(4, f.getFeedbackTopic());
            preparedStatement.setString(5, f.getFeedbackDetails());
            preparedStatement.setString(6, f.getUrgencyLevel());
            preparedStatement.setString(7, f.getContactMethod());
            preparedStatement.setString(8, f.getFeedbackDate());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insertUtilityBill(Bill b) {
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.utilityBillInsertQuery)) {

        preparedStatement.setString(1, b.getName());
        preparedStatement.setInt(2, b.getAge());
        preparedStatement.setString(3, b.getUsername());
        preparedStatement.setString(4, b.getBillType());
        preparedStatement.setString(5, b.getBillingMonth());
        preparedStatement.setDouble(6, b.getBillingAmount());
        preparedStatement.setString(7, b.getPaymentMethod());
        preparedStatement.setString(8, b.getDueDate());
        preparedStatement.setInt(9, b.getAccountNumber());

        return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
        public boolean insertPublicServiceRequest(PublicService p) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.publicServiceInsertQuery)) {
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getAge());
            preparedStatement.setString(3, p.getUsername());
            preparedStatement.setString(4, p.getServiceType());
            preparedStatement.setString(5, p.getDescription());
            preparedStatement.setString(6, p.getLocation());
            preparedStatement.setString(7, p.getUrgencyLevel());
            preparedStatement.setString(8, p.getExpectedCompletionDate());
            preparedStatement.setString(9, p.getContactInformation());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        public boolean insertBusinessLicense(BusinessLicense b) {
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.businessLicenseInsertQuery)) {
        preparedStatement.setString(1, b.getName());
        preparedStatement.setInt(2, b.getAge());
        preparedStatement.setString(3, b.getUsername());
        preparedStatement.setString(4, b.getBusinessName());
        preparedStatement.setString(5, b.getBusinessType());
        preparedStatement.setString(6, b.getBusinessAddress());
        preparedStatement.setString(7, b.getLicenseType());
        preparedStatement.setInt(8, b.getEstimatedAnnualRevenue());
        preparedStatement.setInt(9, b.getEmployees());
        preparedStatement.setString(10, b.getStartDate());
        return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

        public boolean insertDigitalVote(DigitalVote d) {
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.digitalVotingInsertQuery)) {
        preparedStatement.setString(1, d.getName());
        preparedStatement.setInt(2, d.getAge());
        preparedStatement.setString(3, d.getUsername());
        preparedStatement.setString(4, d.getInitiativeType());
        return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

            public boolean submitCrimeOrIncidentReport(CrimeOrIncident c) {
        try (PreparedStatement preparedStatement = dbConnection.connectDatabase().prepareStatement(DatabaseConnection.INSERT_CRIME_REPORT_QUERY)) {
            preparedStatement.setString(1, c.getName());
            preparedStatement.setInt(2, c.getAge());
            preparedStatement.setString(3, c.getUsername());
            preparedStatement.setString(4, c.getLocation());
            preparedStatement.setString(5, c.getDescription());
            preparedStatement.setString(6, c.getOccurrenceDate());
            preparedStatement.setString(7, c.getOccurrenceTime());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        
         public List<Object[]> getPropertyRequests() {
        List<Object[]> requests = new ArrayList<>();
        try (Connection connection = dbConnection.connectDatabase();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM RegisterProperty")) {

            while (resultSet.next()) {
                requests.add(new Object[]{
                        resultSet.getString("Name"),
                        resultSet.getInt("Age"),
                        resultSet.getString("Username"),
                        resultSet.getString("PropertyAddress"),
                        resultSet.getString("PropertyType"),
                        resultSet.getString("Status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updatePropertyRequestStatus(String username, String propertyAddress, String status) {
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement ps = connection.prepareStatement("UPDATE RegisterProperty SET Status = ? WHERE Username = ? AND PropertyAddress = ?")) {
            ps.setString(1, status);
            ps.setString(2, username);
            ps.setString(3, propertyAddress);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<String[]> getDigitalVotes() {
        List<String[]> votes = new ArrayList<>();
        String query = "SELECT Name, Age, Username, InitiativeType FROM ResidentDigitalVoting";

        try (Connection conn = DatabaseConnection.connectDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String username = rs.getString("Username");
                String initiativeType = rs.getString("InitiativeType");
                votes.add(new String[] { name, String.valueOf(age), username, initiativeType });
            }

        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly
        }
        return votes;
    }
    
        public boolean deleteVote(String username) {
        String query = "DELETE FROM DigitalVotes WHERE username = ?";
        try (Connection connection = dbConnection.connectDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was deleted
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }
        
            public boolean deleteFeedback(String username) {
        String query = "DELETE FROM SubmitPublicFeedback WHERE Username = ?";
        try (Connection conn = DatabaseConnection.connectDatabase();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, username);  // Set the username parameter for deletion
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;  // Return true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }
            
    public boolean scheduleMaintenance(Maintenance m) {
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.insertInfrastructureMaintenanceQuery)) {

        preparedStatement.setString(1, m.getName());
        preparedStatement.setString(2, m.getDesignation());
        preparedStatement.setString(3, m.getLocation());
        preparedStatement.setDate(4, java.sql.Date.valueOf(m.getDate())); // Convert string date to SQL Date
        preparedStatement.setTime(5, java.sql.Time.valueOf(m.getTime())); // Convert string time to SQL Time
        preparedStatement.setString(6, m.getInfrastructureType());
        preparedStatement.setInt(7, m.getAllocatedCost());
        preparedStatement.setString(8, m.getDescription());

        return preparedStatement.executeUpdate() > 0; // Return true if the insertion was successful
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
        public ResultSet getBusinessLicenseRequests() {
        String query = "SELECT * FROM AcquireBusinessLicense";
        ResultSet resultSet = null;
        
        try {
            Connection connection = DatabaseConnection.connectDatabase();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resultSet;
    }
        
        public void updateBusinessLicenseStatus(String username, String businessName, String status) {
    String query = "UPDATE AcquireBusinessLicense SET Status = ? WHERE Username = ? AND BusinessName = ?";

    try (Connection connection = DatabaseConnection.connectDatabase();
         PreparedStatement stmt = connection.prepareStatement(query)) {

        stmt.setString(1, status);
        stmt.setString(2, username);
        stmt.setString(3, businessName);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Business license status updated to: " + status);
        } else {
            System.out.println("No matching record found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void loadBusinessLicenseRequests(JTable table) {
    BusinessLogicLayer logicLayer = new BusinessLogicLayer();
    ResultSet resultSet = logicLayer.getBusinessLicenseRequests();

    // Define the column names
    String[] columnNames = {"Name", "Age", "Username", "Business Name", "Business Type", "Business Address", "License Type", "Estimated Revenue", "Employees", "Start Date", "Status", "Action"};
    
    // Create a DefaultTableModel
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    
    try {
        // Add rows to the table from the result set
        while (resultSet.next()) {
            Object[] row = {
                resultSet.getString("Name"),
                resultSet.getInt("Age"),
                resultSet.getString("Username"),
                resultSet.getString("BusinessName"),
                resultSet.getString("BusinessType"),
                resultSet.getString("BusinessAddress"),
                resultSet.getString("LicenseType"),
                resultSet.getInt("EstimatedAnnualRevenue"),
                resultSet.getInt("Employees"),
                resultSet.getDate("StartDate"),
                resultSet.getString("Status") // Display status
            };
            tableModel.addRow(row);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Set the table model
    table.setModel(tableModel);
}

// Method to add a new institution
public boolean addInstitution(Institution i) {
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.insertInstitutionQuery)) {
        preparedStatement.setInt(1, i.getId());
        preparedStatement.setString(2, i.getName());
        preparedStatement.setString(3, i.getLocation());
        preparedStatement.setString(4, i.getType());
        return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

// Method to delete an institution by ID
public boolean deleteInstitution(int id) {
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.deleteInstitutionQuery)) {
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

// Method to retrieve all institutions
public List<String[]> getAllInstitutions() {
    List<String[]> institutions = new ArrayList<>();
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(dbConnection.selectAllInstitutionsQuery);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
            String[] institution = new String[4];
            institution[0] = String.valueOf(resultSet.getInt("ID"));
            institution[1] = resultSet.getString("Name");
            institution[2] = resultSet.getString("Location");
            institution[3] = resultSet.getString("Type");
            institutions.add(institution);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return institutions;
}

  // Add Healthcare Unit
    public boolean addHealthcareUnit(HealthcareUnit h) {
        try (Connection connection = DatabaseConnection.connectDatabase();
             PreparedStatement stmt = connection.prepareStatement(DatabaseConnection.INSERT_HEALTHCARE_UNIT_QUERY)) {
            stmt.setInt(1, h.getId());
            stmt.setString(2, h.getName());
            stmt.setString(3, h.getLocation());
            stmt.setString(4, h.getType());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Healthcare Unit
    public boolean deleteHealthcareUnit(int id) {
        try (Connection connection = DatabaseConnection.connectDatabase();
             PreparedStatement stmt = connection.prepareStatement(DatabaseConnection.DELETE_HEALTHCARE_UNIT_QUERY)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Retrieve all healthcare units
    public List<String[]> getAllHealthcareUnits() {
        List<String[]> healthcareUnits = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connectDatabase();
             PreparedStatement stmt = connection.prepareStatement(DatabaseConnection.SELECT_ALL_HEALTHCARE_UNITS_QUERY);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String[] unit = new String[4];
                unit[0] = String.valueOf(resultSet.getInt("ID")); // ID
                unit[1] = resultSet.getString("Name");            // Name
                unit[2] = resultSet.getString("Location");        // Location
                unit[3] = resultSet.getString("Type");            // Type
                healthcareUnits.add(unit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return healthcareUnits;
    }
    
    public boolean authenticateResident(String username, String password) {
    String query = "SELECT COUNT(*) FROM Resident WHERE Username = ? AND Password = ?";
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0; // Return true if credentials match
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Return false if credentials don't match or an error occurs
}

    public boolean authenticateAdmin(String username, String password) {
    String query = "SELECT COUNT(*) FROM Admin WHERE Username = ? AND Password = ?";
    try (Connection connection = dbConnection.connectDatabase();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean updateAdminDetails(Admin admin) {
    Connection conn = DatabaseConnection.connectDatabase();
    PreparedStatement stmt = null;

    try {
        String query = "UPDATE Admin SET Name = ?, Age = ?, Gender = ?, Username = ?, Password = ?, Experience = ?";
        stmt = conn.prepareStatement(query);
        
        // Set the parameters based on the admin object
        stmt.setString(1, admin.getName());
        stmt.setInt(2, admin.getAge());
        stmt.setString(3, admin.getGender());
        stmt.setString(4, admin.getUsername());
        stmt.setString(5, admin.getPassword());
        stmt.setString(6, admin.getExperience());

        int rowsAffected = stmt.executeUpdate();

        // Return true if at least one row is updated
        return rowsAffected > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    
}

