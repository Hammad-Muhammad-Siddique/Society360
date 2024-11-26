
package DomainEntity;

public class RecreationFacility {
    private String name;
    private int age;
    private String username;
    private String facilityName;
    private String reservationDate;
    private String reservationTime;

    // Constructor
    public RecreationFacility(String name, int age, String username, String facilityName, String reservationDate, String reservationTime) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.facilityName = facilityName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public String getName()
    {
        return name;
    }
    
    public int getAge()
    {
        return age;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getFacility()
    {
        return facilityName;
    }
        
    public String getDate()
    {
        return reservationDate;
    }
    
    public String getTime()
    {
        return reservationTime;
    }
    
    
}

