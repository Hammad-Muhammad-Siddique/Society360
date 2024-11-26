
package DomainEntity;

public class PublicService {
    private String name;
    private int age;
    private String username;
    private String serviceType;
    private String description;
    private String location;
    private String urgencyLevel;
    private String expectedCompletionDate;
    private String contactInformation;

    // Constructor
    public PublicService(String name, int age, String username, String serviceType, String description, String location, String urgencyLevel, String expectedCompletionDate, String contactInformation) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.serviceType = serviceType;
        this.description = description;
        this.location = location;
        this.urgencyLevel = urgencyLevel;
        this.expectedCompletionDate = expectedCompletionDate;
        this.contactInformation = contactInformation;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getServiceType() { return serviceType; }
public String getDescription() { return description; }
public String getLocation() { return location; }
public String getUrgencyLevel() { return urgencyLevel; }
public String getExpectedCompletionDate() { return expectedCompletionDate; }
public String getContactInformation() { return contactInformation; }

}

