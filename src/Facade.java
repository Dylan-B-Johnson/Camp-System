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

    public DaySchedule getDaySchedule(int daysFromNow, User counselor) {
        return WeekList.getInstance().getDaySchedule(LocalDate.now().plusDays(daysFromNow), counselor);
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

    public String displayTime(int[] time) {
        int hour = time[0];
        int minute = time[1];
        String half;
        int displayHour;
        if (hour >= 12) {
            half = "pm";
            if (hour >= 13) {
                displayHour = hour - 12;
            } else {
                displayHour = hour;
            }
        } else {
            half = "am";
            if (hour == 0) {
                displayHour = 12;
            } else {
                displayHour = hour;
            }
        }
        if (minute != 0) {
            if (minute > 9) {
                return displayHour + ":" + minute + " " + half;
            } else {
                return displayHour + ":0" + minute + " " + half;
            }

        } else {
            return displayHour + " " + half;
        }
    }

    public int[] addTime(int hour, int minute, int minutes) {
        minute += minutes;
        hour += minute / 60;
        minute %= 60;
        hour %= 24;
        return new int[] { hour, minute };
    }

}