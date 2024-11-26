
package DomainEntity;

public class CrimeOrIncident {
    private String name;
    private int age;
    private String username;
    private String location;
    private String description;
    private String occurrenceDate;
    private String occurrenceTime;

    public CrimeOrIncident() {}

    public CrimeOrIncident(String name, int age, String username, String location, String description,
                           String occurrenceDate, String occurrenceTime) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.location = location;
        this.description = description;
        this.occurrenceDate = occurrenceDate;
        this.occurrenceTime = occurrenceTime;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getLocation() { return location; }
public String getDescription() { return description; }
public String getOccurrenceDate() { return occurrenceDate; }
public String getOccurrenceTime() { return occurrenceTime; }

}
