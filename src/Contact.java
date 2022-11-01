import java.util.UUID;

/**
 * A contact
 * 
 * @author Row 3
 */
public class Contact {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String relationship;
    private String address;

    /**
     * Creates a Contact given the following parameters
     * 
     * @param firstName    Contacts first name
     * @param lastName     Contacts last name
     * @param email        Contacts email
     * @param phoneNum     Contacts phone number
     * @param relationship Contacts relationship to counselor/camper
     * @param address      Contacts address
     */
    public Contact(String firstName, String lastName, String email, String phoneNum, String relationship,
            String address) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNum(phoneNum);
        setRelationship(relationship);
        setAddress(address);
        this.id = UUID.randomUUID();
    }

    /**
     * Creates a Contact with a UUID
     * 
     * @param id
     */
    public Contact(UUID id) {
        setId(id);
    }

    /**
     * Creates a string representation of Contacts attributes
     */
    public String toString() {
        return "\tName: " + firstName + " " + lastName + "\n" +
                "\tEmail: " + email + "\n" +
                "\tPhone #: " + phoneNum + "\n" +
                "\tRelationship: " + relationship + "\n" +
                "\tAddress: " + address;
    }

    /**
     * Gets the Contacts UUID
     * 
     * @return Contacts UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Takes in a UUID and sets it as the Contacts UUID
     * 
     * @param id Contacts UUID
     */
    public void setId(UUID id) {
        if (id != null)
            this.id = id;
    }

    /**
     * Gets the first name of a Contact
     * 
     * @return Contacts first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name for a new Contact
     * 
     * @param firstName Contacts first name
     */
    public void setFirstName(String firstName) {
        if (firstName != null)
            this.firstName = firstName;
    }

    /**
     * Gets the last name of a Contact
     * 
     * @return Contacts last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of a new Contact
     * 
     * @param lastName Cotacts last name
     */
    public void setLastName(String lastName) {
        if (lastName != null)
            this.lastName = lastName;
    }

    /**
     * Gets the email of a Contact
     * 
     * @return Contacts email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of a new Contact
     * 
     * @param email Contacts email
     */
    public void setEmail(String email) {
        if (email != null)
            this.email = email;
    }

    /**
     * Gets the phone number of a Contact
     * 
     * @return Contacts phone number
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets the phone number of a new Contact
     * 
     * @param phoneNum Contacts phone number
     */
    public void setPhoneNum(String phoneNum) {
        if (phoneNum != null)
            this.phoneNum = phoneNum;
    }

    /**
     * Gets the relationship of a Contact
     * 
     * @return Contacts relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Sets the relationship of a new Contact
     * 
     * @param relationship Contacts relationship
     */
    public void setRelationship(String relationship) {
        if (relationship != null)
            this.relationship = relationship;
    }

    /**
     * Gets the address of a Contact
     * 
     * @return Contacts address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of a new Contact
     * 
     * @param address Contacts address
     */
    public void setAddress(String address) {
        if (address != null)
            this.address = address;
    }

}