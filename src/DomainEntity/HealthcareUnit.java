
package DomainEntity;

public class HealthcareUnit {
    private int id;
    private String name;
    private String location;
    private String type;

    public HealthcareUnit() {}

    public HealthcareUnit(int id, String name, String location, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public int getId() { return id; }
public String getName() { return name; }
public String getLocation() { return location; }
public String getType() { return type; }

}

