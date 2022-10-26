
//Copyright Row 3
import java.util.ArrayList;
import java.util.UUID;

public class UserList {

    public static boolean emailAvailable(String email) {
        for (User user : DataReader.getUsers().values()) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

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
        }
    }

    public static ArrayList<User> getUsers(){
        return new ArrayList<User>(DataReader.getUsers().values());
    }

    public static ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    public static ArrayList<Counselor> getCounselors() {
        return new ArrayList<Counselor>(DataReader.getCounselors().values());
    }

    public static ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    public static Director getDirector() {
        return DataReader.getDirector();
    }

    public static User getUser(UUID id) {
        if (!DataReader.getUsers().containsKey(id))
            return null;
        return DataReader.getUsers().get(id);
    }
}
