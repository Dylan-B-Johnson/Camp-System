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
                "Email: " + email + "\n" +
                "Phone #: " + phoneNum + "\n" +
                "Relationship: " + relationship + "\n" +
                "Address: " + address;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null)
            this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null)
            this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null)
            this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        if (phoneNum != null)
            this.phoneNum = phoneNum;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        if (relationship != null)
            this.relationship = relationship;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null)
            this.address = address;
    }

}