
package DomainEntity;

public class BusinessLicense {
    private String name;
    private int age;
    private String username;
    private String businessName;
    private String businessType;
    private String businessAddress;
    private String licenseType;
    private int estimatedAnnualRevenue;
    private int employees;
    private String startDate; // Use String for DATE compatibility with MySQL format

    public BusinessLicense() {}

    public BusinessLicense(String name, int age, String username, String businessName, String businessType, String businessAddress,
                            String licenseType, int estimatedAnnualRevenue, int employees, String startDate) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.businessName = businessName;
        this.businessType = businessType;
        this.businessAddress = businessAddress;
        this.licenseType = licenseType;
        this.estimatedAnnualRevenue = estimatedAnnualRevenue;
        this.employees = employees;
        this.startDate = startDate;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getBusinessName() { return businessName; }
public String getBusinessType() { return businessType; }
public String getBusinessAddress() { return businessAddress; }
public String getLicenseType() { return licenseType; }
public int getEstimatedAnnualRevenue() { return estimatedAnnualRevenue; }
public int getEmployees() { return employees; }
public String getStartDate() { return startDate; }

}

