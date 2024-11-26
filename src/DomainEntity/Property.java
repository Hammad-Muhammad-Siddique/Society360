
package DomainEntity;

public class Property {
    private String name;
    private int age;
    private String username;
    private String propertyAddress;
    private String propertyType;

    // Constructor
    public Property(String name, int age, String username, String propertyAddress, String propertyType) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.propertyAddress = propertyAddress;
        this.propertyType = propertyType;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getPropertyAddress() { return propertyAddress; }
public String getPropertyType() { return propertyType; }

}

