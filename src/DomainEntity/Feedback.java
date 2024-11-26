
package DomainEntity;

public class Feedback {
    private String name;
    private int age;
    private String username;
    private String feedbackTopic;
    private String feedbackDetails;
    private String urgencyLevel;
    private String contactMethod;
    private String feedbackDate;

    // Constructor
    public Feedback(String name, int age, String username, String feedbackTopic, String feedbackDetails, String urgencyLevel, String contactMethod, String feedbackDate) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.feedbackTopic = feedbackTopic;
        this.feedbackDetails = feedbackDetails;
        this.urgencyLevel = urgencyLevel;
        this.contactMethod = contactMethod;
        this.feedbackDate = feedbackDate;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getFeedbackTopic() { return feedbackTopic; }
public String getFeedbackDetails() { return feedbackDetails; }
public String getUrgencyLevel() { return urgencyLevel; }
public String getContactMethod() { return contactMethod; }
public String getFeedbackDate() { return feedbackDate; }

}
