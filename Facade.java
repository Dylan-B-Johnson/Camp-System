import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDate;

public class Facade{

    private User user;

    public Facade(){

    }

    public User login(String email, String password){
        return null;
    }

    public User signUp(String email, String password){
        return null;
    }

    public Week getCurrentWeek(){
        return null;
    }

    public ArrayList<Week> getWeeksAvailableForRegistration(){
        return null; 
    }

    public ArrayList<Activity> getActivities(){
        return null;
    }

    public void quitAndSave(){

    }

    public ArrayList<Camper> getCampter(String firstName){
        return null; 
    }

    public ArrayList<User> getUser(String firstName){
        return null;
    }

    public User getCustomers(){
        return null;
    }

    public ArrayList<Camper> getCampers(){
        return null;
    }

    public double getTotalPrice(){
        return -404;
    }

    public ArrayList<User> getCounselors(){
        return null;
    }

    public ArrayList<User> getCounselor(String firstName){
        return null;
    }

    public Contact makeContact(String firstName, String lastName, String email, 
    String phoneNum, String relationship, String address){
        return null;
    }

    public boolean registerCamper(String firstName, String lastName, ArrayList<String> allergies,
    LocalDate birthday, String relationToCustomer, Contact primaryEmergencyContact,
    Contact secondaryEmergencyContact, Contact primaryCarePhysician){
        return false;
    }

    public boolean addActivity(Activity activity){
        return false;     
    }

}