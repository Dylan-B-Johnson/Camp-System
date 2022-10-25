
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

    public void addUser(User user) {
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

    public ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    public ArrayList<Counselor> getCounselors() {
        return new ArrayList<Counselor>(DataReader.getCounselors().values());
    }

    public ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    public Director getDirector() {
        return DataReader.getDirector();
    }

    public User getUser(UUID id) {
        if (!DataReader.getUsers().containsKey(id))
            return null;
        return DataReader.getUsers().get(id);
    }
}
