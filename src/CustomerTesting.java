import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class CustomerTesting {
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
    public void addValidCamper(){
            Customer testCustomer = new Customer("email", "hey", "there", "null", 
                CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
            Camper testCamper = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
            testCustomer.addCamper(testCamper);
            assertEquals(testCamper, testCustomer.getCampers().get(0));
    }

    @Test
    public void addNullCamper(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper = null;
        testCustomer.addCamper(testCamper);
        assertEquals(0, testCustomer.getCampers().size());
    }

    @Test
    public void addInvalidCamper(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper = new Camper(null, "test", allergies, null, 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " "); 
        testCustomer.addCamper(testCamper);
        assertEquals(0, testCustomer.getCampers().size());
    }

    @Test
    public void removeValidCamper(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper1 = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");
        Camper testCamper2 = new Camper("awda", "wadawd", allergies, LocalDate.now().minusYears(11), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
        testCustomer.addCamper(testCamper1);
        testCustomer.addCamper(testCamper2);
        testCustomer.removeCamper(testCamper1.getId());
        assertEquals(1, testCustomer.getCampers().size());
    }

    @Test
    public void removeInvalidCamper(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper1 = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");
        Camper testCamper2 = new Camper("awda", "wadawd", allergies, LocalDate.now().minusYears(11), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
        testCustomer.addCamper(testCamper1);
        testCustomer.addCamper(testCamper2);
        testCustomer.removeCamper(null);
        assertEquals(2, testCustomer.getCampers().size());
    }

    @Test
    public void editValidCamper(){
        Customer testCustomer = new Customer("email", "hey", "there", "null", 
            CampLocation.getCampLocation(), new ArrayList<Camper>(), primaryEmergencyContact);
        Camper testCamper1 = new Camper("caleb", "test", allergies, LocalDate.now().minusYears(12), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");
        Camper testCamper2 = new Camper("awda", "wadawd", allergies, LocalDate.now().minusYears(11), 
            primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0, " ", " ");  
        testCustomer.addCamper(testCamper1);
        testCustomer.editCamper(testCamper1.getId(), testCamper2);
        assertEquals(testCamper2, testCustomer.getCampers().get(0));
    }
}
