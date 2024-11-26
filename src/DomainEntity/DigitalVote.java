
package DomainEntity;

public class DigitalVote {
    private String name;
    private int age;
    private String username;
    private String initiativeType;

    public DigitalVote() {}

    public DigitalVote(String name, int age, String username, String initiativeType) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.initiativeType = initiativeType;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getInitiativeType() { return initiativeType; }

}

