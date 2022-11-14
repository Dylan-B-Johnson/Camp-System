import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DirectorTesting {
    private ArrayList<String> allergies;
    private Contact primaryEmergencyContact;
    private Contact secondaryEmergencyContact;
    private Contact primaryCarePhysician;

    @BeforeEach
    public void setup(){
    primaryEmergencyContact = new Contact("Ryan", "Harp", "harr@gmail.com",
            "657-234-2231", "Friend",
            "55 Hoback St.");
    secondaryEmergencyContact = new Contact("Thomas", "Mandrus", "mandrusT@gmail.com",
            "456-586-2231", "Friend",
            "76 Hoback St.");
    primaryCarePhysician = new Contact("Ron", "Rhymer", "ron@pak.org", "811-234-2333",
            "PCP",
            "Pediatric Association of Knoxbville, 23 College Drive");
    allergies = new ArrayList<String>();    
    }
    
    @AfterEach
    public void tearDown(){

    }

    @Test
    public void removeValidCamper(){
        Director director = new Director("hey", "whats", "up", "with", CampLocation.getCampLocation());
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");
        testCustomer.addCamper(testCamper);
        UserList.addUser(testCustomer);
        DataWriter.createCamper(testCamper);
        director.removeCamper(testCamper.getFirstName(), testCamper.getLastName());
        assertFalse(UserList.getCampers().contains(testCamper));
    }

    @Test
    public void removeNullCamper(){
        Director director = new Director("hey", "whats", "up", "with", CampLocation.getCampLocation());
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        UserList.addUser(testCustomer);
        int priorSize = UserList.getCampers().size();
        director.removeCamper(null, null);
        assertEquals(priorSize, UserList.getCampers().size());
    }

    @Test
    public void removeValidCounselor(){
        Director director = new Director("hey", "whats", "up", "with", CampLocation.getCampLocation());
        Counselor counselor = new Counselor(allergies, LocalDate.now().minusYears(18), "validemail@gmail.com", "wdadwa", "AWdawd", "sadaw", CampLocation.getCampLocation(), primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician);
        UserList.addUser(counselor);
        director.removeCounselor(counselor.getEmail());
        assertFalse(UserList.getCounselors().contains(counselor));
    }

    @Test
    public void removenullCounselor(){
        Director director = new Director("hey", "whats", "up", "with", CampLocation.getCampLocation());
        Counselor counselor = new Counselor(allergies, LocalDate.now().minusYears(18), "validemail@gmail.com", "wdadwa", "AWdawd", "sadaw", CampLocation.getCampLocation(), primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician);
        UserList.addUser(counselor);
        int priorSize = UserList.getCounselors().size();
        director.removeCounselor(null);
        assertEquals(priorSize, UserList.getCounselors().size());
    }

    @Test
    public void removeValidCustomer(){
        Director director = new Director("hey", "whats", "up", "with", CampLocation.getCampLocation());
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        UserList.addUser(testCustomer);
        director.removeCustomer(testCustomer.getEmail());
        assertFalse(UserList.getCustomers().contains(testCustomer));
    }

    @Test
    public void removenullCustomer(){
        Director director = new Director("hey", "whats", "up", "with", CampLocation.getCampLocation());
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        UserList.addUser(testCustomer);
        int priorSize = UserList.getCustomers().size();
        director.removeCustomer(null);
        assertEquals(priorSize, UserList.getCustomers().size());
    }

}
