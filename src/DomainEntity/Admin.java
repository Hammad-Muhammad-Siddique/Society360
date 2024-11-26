package DomainEntity;

public class Admin {
    private static Admin instance; // Static instance of the class
    private String name;
    private int age;
    private String gender;
    private String username;
    private String password;
    private String experience;

    // Private constructor to restrict instantiation
    private Admin(String name, int age, String gender, String username, String password, String experience) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.experience = experience;
    }

    // Public static method to provide access to the single instance
    public static Admin getInstance(String name, int age, String gender, String username, String password, String experience) {
        if (instance == null) {
            instance = new Admin(name, age, gender, username, password, experience);
        }
        return instance;
    }

    // Update method to modify admin details
    public void updateAdminDetails(String name, int age, String gender, String username, String password, String experience) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.experience = experience;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getExperience() { return experience; }
}
