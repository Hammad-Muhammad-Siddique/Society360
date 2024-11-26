package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/SDA_Project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "hammad123";
    
    public String updateAdminQuery = "UPDATE Admin SET Name = ?, Age = ?, Gender = ?, Username = ?, Password = ?, Experience = ? WHERE Username = ?";

    
        // Add healthcare unit
    public static final String INSERT_HEALTHCARE_UNIT_QUERY = 
        "INSERT INTO HealthcareUnit (ID, Name, Location, Type) VALUES (?, ?, ?, ?)";

    // Delete healthcare unit
    public static final String DELETE_HEALTHCARE_UNIT_QUERY = 
        "DELETE FROM HealthcareUnit WHERE ID = ?";
    
    // Query to retrieve all healthcare units
    public static final String SELECT_ALL_HEALTHCARE_UNITS_QUERY = 
    "SELECT ID, Name, Location, Type FROM HealthcareUnit";

    
    public String residentInsertQuery = "INSERT INTO Resident (Name, Age, Gender, Username, Password) VALUES (?, ?, ?, ?, ?)";
    public String userNameCheckQuery = "SELECT COUNT(*) FROM Resident WHERE Username = ?";
    public String reserveFacilityInsertQuery = "INSERT INTO ReserveRecreationFacility (Name, Age, Username, FacilityName, ReservationDate, ReservationTime) VALUES (?, ?, ?, ?, ?, ?)";
    public String propertyInsertQuery = "INSERT INTO RegisterProperty (Name, Age, Username, PropertyAddress, PropertyType) VALUES (?, ?, ?, ?, ?)";
    public String publicFeedbackInsertQuery = "INSERT INTO SubmitPublicFeedback (Name, Age, Username, FeedbackTopic, FeedbackDetails, UrgencyLevel, ContactMethod, FeedbackDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public String utilityBillInsertQuery = "INSERT INTO PayUtilityBills (Name, Age, Username, BillType, BillingMonth, BillingAmount, PaymentMethod, DueDate, AccountNumber) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public String publicServiceInsertQuery = 
        "INSERT INTO RequestPublicService (Name, Age, Username, ServiceType, Description, Location, UrgencyLevel, ExpectedCompletionDate, ContactInformation) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    public String businessLicenseInsertQuery = "INSERT INTO AcquireBusinessLicense " +
    "(Name, Age, Username, BusinessName, BusinessType, BusinessAddress, LicenseType, EstimatedAnnualRevenue, Employees, StartDate) " +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public String digitalVotingInsertQuery = "INSERT INTO ResidentDigitalVoting (Name, Age, Username, InitiativeType) VALUES (?, ?, ?, ?)";

    public static final String INSERT_CRIME_REPORT_QUERY =
            "INSERT INTO ReportCrimeOrIncident (Name, Age, Username, Location, Description, OccurrenceDate, OccurrenceTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    // Add this query to the DatabaseConnection class
    public String insertInfrastructureMaintenanceQuery = "INSERT INTO ScheduledMaintenances " +
        "(Name, Designation, Location, Date, Time, InfrastructureType, AllocatedCost, Description) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    // Add these queries to the DatabaseConnection class

// Query for inserting a new institution
public String insertInstitutionQuery = "INSERT INTO Institution (ID, Name, Location, Type) VALUES (?, ?, ?, ?)";

// Query for deleting an institution by ID
public String deleteInstitutionQuery = "DELETE FROM Institution WHERE ID = ?";

// Query to retrieve all institutions
public String selectAllInstitutionsQuery = "SELECT * FROM Institution";

    
    public static Connection connectDatabase() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
