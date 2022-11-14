import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Caleb Henry
 */
public class CamperTesting {
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
    public void getNonexistentParent(){
        Camper testCamper = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
        assertNull(testCamper.getParent());
    }

    @Test
    public void getExistingParent(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
        testCustomer.addCamper(testCamper);
        DataWriter.createCamper(testCamper);
        UserList.addUser(testCustomer);
        assertEquals(testCustomer, testCamper.getParent());
    }

    @Test
    public void checkUnregisteredCamper(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
        testCustomer.addCamper(testCamper);
        DataWriter.createCamper(testCamper);
        UserList.addUser(testCustomer);
        assertEquals(0, testCamper.getEnrolledWeeks().size());
    }
    
    @Test
    public void checkRegisteredCamper(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        ArrayList<Camper> campers = new ArrayList<Camper>();
        Camper testCamper = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");
        campers.add(testCamper);  
        Group testGroup = new Group(campers, 6, null, null);
        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(testGroup);
        testWeek.setGroups(groups);
        assertEquals(testWeek, testCamper.getEnrolledWeeks().get(0));
    }
}
