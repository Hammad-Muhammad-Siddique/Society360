
package DomainEntity;

public class Institution {
    private int id;
    private String name;
    private String location;
    private String type;

    public Institution() {}

    public Institution(int id, String name, String location, String type) {
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

