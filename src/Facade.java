import java.util.ArrayList;
import java.util.UUID;
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
        for (User user : UserList.getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User signUp(String email, String password) {
       return null;
    }

    public Week getCurrentWeek() {
        return WeekList.getCurrentWeek();
    }

    public ArrayList<Week> getWeeksAvailableForRegistration() {
        return WeekList.getWeeksAvailableForRegistration();
    }

    public String[] getWeeksAvailableForRegistration() {
        ArrayList<Week> weeks = getWeeksAvailableForRegistrationWeek();
        String[] rtn = new String[weeks.size()];
        for (int i = 0; i < weeks.size(); i++) {
            rtn[i] = (weeks.get(i).toString());
        }
        return rtn;
    }

    public String[] getCamperStrings() {
        ArrayList<Camper> campers = ((Customer) user).getCampers();
        String[] rtn = new String[campers.size()];
        for (int i = 0; i < rtn.length; i++) {
            rtn[i]=campers.get(i).getFirstName()+" "+campers.get(i).getLastName();
        }
        return rtn;
    }

    public double getCostOfRegistration(){
        return -404;
    }

    public double getDiscoutOnRegistration(){
        return -404;
    }

    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values());
    }

    public void quitAndSave() {

    }

    public ArrayList<Camper> getCamper(String firstName) {
        ArrayList<Camper> campers = new ArrayList<Camper>();
        for(Camper camper : UserList.getCampers()){
            if(camper.getFirstName().equals(firstName)){
                campers.add(camper);
            }
        }
        return campers;
    }

    public ArrayList<User> getUser(String firstName) {
        ArrayList<User> users = new ArrayList<User>();
        for(User user : UserList.getUsers()){
            if(user.getFirstName().equals(firstName)){
                users.add(user);
            }
        }
        return users;
    }

    public ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    public ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    public double getTotalPrice() {
        return 100 - ((Customer)this.user).getDiscount();
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
        ArrayList<User> users = new ArrayList<User>();
        for(User user : UserList.getUsers()){
            if(user.getFirstName().equals(firstName)){
                users.add(user);
            }
        }
        return users;
    }

    public Contact makeContact(String firstName, String lastName, String email,
            String phoneNum, String relationship, String address) {
        return null;
    }

    public boolean registerCamper(UUID id) {
        return false;
    }

    public boolean addCamperToUser(String firstName, String lastName, ArrayList<String> allergies,
            LocalDate birthday, String relationToCustomer, Contact primaryEmergencyContact,
            Contact secondaryEmergencyContact, Contact primaryCarePhysician) {
        return false;
    }

    public boolean addActivity(Activity activity) {
        return false;
    }

    public DaySchedule getDaySchedule(int daysFromNow, User counselor) {
        return WeekList.getDaySchedule(LocalDate.now().plusDays(daysFromNow), counselor);
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

    public Group getGroup(User counselor) {
        return null;
    }

    /**
     * A method which gets the ith number properly formatted with the
     * gramatically-correct suffix.
     * 
     * @author Bohemian
     *         (https://stackoverflow.com/users/256196/bohemian)
     *         (https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name)
     *         Row 3 claims no ownership or copywrite over this method.
     * @param i The number to get the proper suffix for the ith number for
     * @return A string for the properly formatted ith number
     */
    public String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }

}