
package DomainEntity;

public class Maintenance {
    private int id;
    private String name;
    private String designation;
    private String location;
    private String date;
    private String time;
    private String infrastructureType;
    private int allocatedCost;
    private String description;

    public Maintenance() {}

    public Maintenance(String name, String designation, String location, String date, String time,
                       String infrastructureType, int allocatedCost, String description) {
        this.name = name;
        this.designation = designation;
        this.location = location;
        this.date = date;
        this.time = time;
        this.infrastructureType = infrastructureType;
        this.allocatedCost = allocatedCost;
        this.description = description;
    }

    public int getId() { return id; }
public String getName() { return name; }
public String getDesignation() { return designation; }
public String getLocation() { return location; }
public String getDate() { return date; }
public String getTime() { return time; }
public String getInfrastructureType() { return infrastructureType; }
public int getAllocatedCost() { return allocatedCost; }
public String getDescription() { return description; }

}

