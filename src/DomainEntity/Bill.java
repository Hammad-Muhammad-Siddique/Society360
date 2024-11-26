
package DomainEntity;

public class Bill {
    private String name;
    private int age;
    private String username;
    private String billType;
    private String billingMonth;
    private int billingAmount;
    private String paymentMethod;
    private String dueDate;
    private int accountNumber;

    // Constructor
    public Bill(String name, int age, String username, String billType, String billingMonth, int billingAmount, String paymentMethod, String dueDate, int accountNumber) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.billType = billType;
        this.billingMonth = billingMonth;
        this.billingAmount = billingAmount;
        this.paymentMethod = paymentMethod;
        this.dueDate = dueDate;
        this.accountNumber = accountNumber;
    }

    public String getName() { return name; }
public int getAge() { return age; }
public String getUsername() { return username; }
public String getBillType() { return billType; }
public String getBillingMonth() { return billingMonth; }
public int getBillingAmount() { return billingAmount; }
public String getPaymentMethod() { return paymentMethod; }
public String getDueDate() { return dueDate; }
public int getAccountNumber() { return accountNumber; }

}

