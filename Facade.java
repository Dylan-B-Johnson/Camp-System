import java.util.ArrayList;
import java.util.UUID;

public class Facade{

    private User user;

    public Facade(){

    }

    public User login(String email, String password){

    }

    public User signUp(String email, String password){

    }

    public Week getCurrentWeek(){

    }

    public ArrayList<Week> getWeeksAvailableForRegistration(){
        
    }

    public ArrayList<Activity> getActivities(){

    }

    public void quitAndSave(){

    }

    public ArrayList<Camper> getCampter(String firstName){
        
    }

    public ArrayList<User> getUser(String firstName){

    }

    public User getCustomers(){

    }

    public ArrayList<Camper> getCampers(){

    }

    public double getTotalPrice(){

    }

    public ArrayList<User> getCounselors(){

    }

    public ArrayList<User> getCounselor(String firstName){

    }

    public Contact makeContact(String firstName, String lastName, String email, 
    String phoneNum, String relationship, String address){

    }

    public boolean registerCamper(String firstName, String lastName, ArrayList<String> allergies,
    LocalDate birthday, String relationToCustomer, Contact primaryEmergencyContact,
    Contact secondaryEmergencyContact, Contact primaryCarePhysician){

    }

    public boolean addActivity(Activity activity){
        
    }

}