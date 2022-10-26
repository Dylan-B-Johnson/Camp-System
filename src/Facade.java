import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Facade {

    private User user;

    public Facade() {

    }

    public User getDirector() {
        return DataReader.getDirector();
    }

    public CampLocation getCampLocation() {
        return DataReader.getCampLocation();
    }

    public User login(String email, String password) {
        for(User user : UserList.getUsers()){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public User signUp(String email, String password) {
        return null;
    }

    public Week getCurrentWeek() {
        return null;
    }

    public ArrayList<Week> getWeeksAvailableForRegistration() {
        return null;
    }

    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values()) ;
    }

    public void quitAndSave() {

    }

    public ArrayList<Camper> getCamper(String firstName) {
        return null;
    }

    public ArrayList<User> getUser(String firstName) {
        return null;
    }

    public ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    public ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    public double getTotalPrice() {
        return -1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getCounselors() {
        return new ArrayList<User>(DataReader.getCounselors().values());
    }

    public ArrayList<User> getCounselor(String firstName) {
        return null;
    }

    public Contact makeContact(String firstName, String lastName, String email,
            String phoneNum, String relationship, String address) {
        return null;
    }

    public boolean registerCamper(String firstName, String lastName, ArrayList<String> allergies,
            LocalDate birthday, String relationToCustomer, Contact primaryEmergencyContact,
            Contact secondaryEmergencyContact, Contact primaryCarePhysician) {
        return false;
    }

    public boolean addActivity(Activity activity) {
        return false;
    }

    public String[] nextWeek() {
        String[] rtn = new String[7];
        LocalDate date = LocalDate.now();
        for (int i = 0; i < rtn.length; i++) {
            LocalDate plusDays = date.plusDays(i);
            rtn[i] = (plusDays.format(DateTimeFormatter.ofPattern("E, LLL d")));
        }
        return rtn;
    }

}