import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    
    private ArrayList<User> users;
    private static UserList userList;

    private UserList(){

    }

    public static UserList getInstance(){
        return null;
    }

    public boolean addUser(String email, String password){
        return false;
    }

    public ArrayList<User> getCustomers(){
        return null;
    }

    public ArrayList<User> getCounselors(){
        return null;
    }

    public ArrayList<Camper> getCampers(){
        return null;
    }

    public User getDirector(){
        return null;
    }

    public User getUser(UUID id){
        return null;  
    }
}
