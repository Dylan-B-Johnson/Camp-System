import java.util.UUID;

public class Contact {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String relationship;
    private String address;

    public Contact(String firstName, String lastName, String email, String phoneNum, String relationship,
            String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.relationship = relationship;
        this.address = address;
        this.id = UUID.randomUUID();
    }

    public Contact(UUID id) {
        this.id = id;
    }

    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email+"\n"+
                "Phone #: "+phoneNum+"\n"+
                "Relationship: "+relationship+"\n"+
                "Address: "+address;
    }
}