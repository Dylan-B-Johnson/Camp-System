import java.util.UUID;

/**
 * A user
 * 
 * @author Row 3
 */
public abstract class User {
    protected UUID id;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected CampLocation campLocation;
    protected TypeOfUser typeOfUser;

    /**
     * Creates a User given the following parameters
     * 
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     * @param campLocation
     * @param typeOfUser
     */
    public User(String email, String firstName, String lastName, String password, CampLocation campLocation,
            TypeOfUser typeOfUser) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.campLocation = campLocation;
        this.typeOfUser = typeOfUser;
    }

    /**
     * Creates a User given the following parameters, which includes ID
     * 
     * @param id
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     * @param campLocation
     * @param typeOfUser
     */
    public User(UUID id, String email, String firstName, String lastName, String password, CampLocation campLocation,
            TypeOfUser typeOfUser) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.campLocation = campLocation;
        this.typeOfUser = typeOfUser;
    }

    /**
     * Creates a String representation of characteristics for users
     * 
     * @return String with characteristics of users
     */
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Type of User: " + typeOfUser;
    }

    /**
     * gets the users ID
     * 
     * @return users ID
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * gets the users email
     * 
     * @return users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks to make sure the email being entered has a valid format
     * 
     * @param email
     * @return true if email is valid, false if it is not
     */
    public static boolean emailIsValid(String email) {
        return email != null && email.contains("@") && email.contains(".") && email.length() >= 3
                && UserList.emailAvailable(email);
    }

    /**
     * If email is valid, sets the email for user
     * 
     * @param email
     */
    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".") && email.length() >= 3
                && UserList.emailAvailable(email))
            this.email = email;
    }

    /**
     * Gets the first name of the user
     * 
     * @return users first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Takes in a String and sets it to users firstName
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        if (firstName != null)
            this.firstName = firstName;
    }

    /**
     * Gets the last name of the user
     * 
     * @return users last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * takes in a String and sets it to users lastName
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        if (lastName != null)
            this.lastName = lastName;
    }

    /**
     * Gets the users password
     * 
     * @return users password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Takes in a String and sets it to the users password
     * 
     * @param password
     */
    public void setPassword(String password) {
        if (password != null)
            this.password = password;
    }

    /**
     * Gets the current campLocation
     * 
     * @return campLocation
     */
    public CampLocation getCampLocation() {
        return campLocation;
    }

    /**
     * If campLocation is not empty, sets it to the user
     * 
     * @param campLocation
     */
    public void setCampLocation(CampLocation campLocation) {
        if (campLocation != null)
            this.campLocation = campLocation;
    }

    /**
     * Returns specific type of user
     * 
     * @return type of user: Counselor, Director, Customer
     */
    public TypeOfUser getTypeOfUser() {
        return typeOfUser;
    }

    /**
     * Sets the type of user
     * 
     * @param typeOfUser
     */
    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

}
