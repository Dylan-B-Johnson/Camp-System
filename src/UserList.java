import java.util.ArrayList;
import java.util.UUID;

/**
 * A list of Users
 * 
 * @author Row 3
 */
public class UserList {

    /**
     * Checks to see if the email entered is available for use
     * 
     * @param email Email being tested for availability
     * @return True if email is available, false if it is not
     */
    public static boolean emailAvailable(String email) {
        for (User user : DataReader.getUsers().values()) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks which type of user it is, then adds them to the system
     * 
     * @param user User being added to the User List
     */
    public static void addUser(User user) {
        switch (user.getTypeOfUser()) {
            case DIRECTOR:
                DataWriter.updateDirector((Director) user);
                break;
            case COUNSELOR:
                DataWriter.createCounselor((Counselor) user);
                break;
            case CUSTOMER:
                DataWriter.createCustomer((Customer) user);
                break;
            default:
                System.out.println("bruh");
        }
    }

    /**
     * Gets a list of all users
     * 
     * @return ArrayList of all users currently in the system
     */
    public static ArrayList<User> getUsers() {
        return new ArrayList<User>(DataReader.getUsers().values());
    }

    /**
     * Gets a list of all customers
     * 
     * @return ArrayList of all customers currently in the system
     */
    public static ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    /**
     * Gets a list of all counselors
     * 
     * @return ArrayList of all counselors currently in the system
     */
    public static ArrayList<Counselor> getCounselors() {
        return new ArrayList<Counselor>(DataReader.getCounselors().values());
    }

    /**
     * Gets a list of all campers
     * 
     * @return ArrayList of all campers currently in the system
     */
    public static ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    /**
     * Gets the director
     * 
     * @return The director of the camp
     */
    public static Director getDirector() {
        return DataReader.getDirector();
    }

    /**
     * Checks to see if the ID entered is valid, then gets a specific user based on
     * the ID entered
     * 
     * @param id ID of the User being found
     * @return User associated with the ID
     */
    public static User getUser(UUID id) {
        if (!DataReader.getUsers().containsKey(id))
            return null;
        return DataReader.getUsers().get(id);
    }
}
