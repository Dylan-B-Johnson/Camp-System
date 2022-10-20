//Copyright Row 3
import java.util.ArrayList;
import java.util.UUID;

public class UserList {
    
    private ArrayList<User> users;
    private ArrayList<Counselor> counselors;
    private ArrayList<Customer> customers;
    private Director director;
    private ArrayList<Camper> campers;
    private static UserList userList;

    private UserList(){
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Counselor> counselors = new ArrayList<Counselor>();
        ArrayList<Customer> customers = new ArrayList<Customer>();
        User director = new Director(null, null, null, null, null);
        ArrayList<Camper> campers = new ArrayList<Camper>();
    }

    public static UserList getInstance(){
        return userList;
    }

    public boolean emailAvailable(String email){
        for(int i=0; i < users.size(); i++){
            if(users.get(i).email.equals(email)){
                return false;
            }
        }
        return true;
    }

    public void addUser(User user){
        users.add(user);
        parseCustomers();
        parseCounselors();
        parseCampers();
        parseDirector();
    }

    private void parseCustomers(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for(User user : users){
            if(user.typeOfUser.equals(TypeOfUser.CUSTOMER)){
                customers.add((Customer)user);
            }
        }
        this.customers = customers;
    }

    public ArrayList<Customer> getCustomers(){
        return this.customers;
    }

    private void parseCounselors(){
        ArrayList<Counselor> counselors = new ArrayList<Counselor>();
        for(User user : users){
            if(user.typeOfUser.equals(TypeOfUser.COUNSELOR)){
                counselors.add((Counselor)user);
            }
        }
        this.counselors = counselors;
    }
    
    public ArrayList<Counselor> getCounselors(){
        return this.counselors;
    }

    private void parseCampers(){
        ArrayList<Camper> campers = new ArrayList<Camper>();
        for(Customer customer : customers){
            for(int j=0; j < customer.getCampers().size(); j++){
                campers.add(customer.getCampers().get(j));
            }
        }
        this.campers = campers;
    }

    public ArrayList<Camper> getCampers(){
        return this.campers;
    }

    private void parseDirector(){
        for(User user : users){
            if(user.typeOfUser.equals(TypeOfUser.DIRECTOR)){
                this.director = (Director)user;
            }
        }
    }

    public Director getDirector(){
        return this.director;
    }

    public User getUser(UUID id){
        for(User user : users){
            if(user.id.equals(id)){
                return user;
            }
        }
        return null;
    }
}
