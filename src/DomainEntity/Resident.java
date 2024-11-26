
package DomainEntity;

public class Resident {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String username;
    private String password;

    // Constructor
    public Resident(String name, int age, String gender, String username, String password) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }

    public int getId() { return id; }
public String getName() { return name; }
public int getAge() { return age; }
public String getGender() { return gender; }
public String getUsername() { return username; }
public String getPassword() { return password; }

}

