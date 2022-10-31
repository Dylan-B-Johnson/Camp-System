import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A counselor
 * 
 * @author Row 3
 */
public class Counselor extends User {
    private ArrayList<String> allergies;
    private LocalDate birthday;
    private Contact primaryEmergencyContact;
    private Contact secondaryEmergencyContact;
    private Contact primaryCarePhysician;

    /**
     * Creates a counselor given the following parameters
     * 
     * @param allergies
     * @param birthday
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     * @param campLocation
     * @param primaryEmergencyContact
     * @param secondaryEmergencyContact
     * @param primaryCarePhysician
     */
    public Counselor(ArrayList<String> allergies, LocalDate birthday, String email, String firstName,
            String lastName, String password,
            CampLocation campLocation, Contact primaryEmergencyContact, Contact secondaryEmergencyContact,
            Contact primaryCarePhysician) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.COUNSELOR);
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
    }

    /**
     * Creates a counselor given the following parameters, including ID
     * 
     * @param id
     * @param allergies
     * @param birthday
     * @param email
     * @param firstName
     * @param lastName
     * @param password
     * @param campLocation
     * @param primaryEmergencyContact
     * @param secondaryEmergencyContact
     * @param primaryCarePhysician
     */
    public Counselor(UUID id, ArrayList<String> allergies, LocalDate birthday, String email,
            String firstName, String lastName, String password,
            CampLocation campLocation, Contact primaryEmergencyContact, Contact secondaryEmergencyContact,
            Contact primaryCarePhysician) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.COUNSELOR);
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
    }

    /**
     * Passes a String ArrayList, if not empty, sets as Counselors allergies
     * 
     * @param allergies
     */
    public void setAllergies(ArrayList<String> allergies) {
        if (allergies != null) {
            this.allergies = allergies;
        }
    }

    /**
     * Gets the counselors allergies
     * 
     * @return Counselors allergies
     */
    public ArrayList<String> getAllergies() {
        return this.allergies;
    }

    /**
     * Passes a LocalDate, if not empty, sets as Counselors birthday
     */
    public void setBirthday(LocalDate birthday) {
        if (birthday != null) {
            this.birthday = birthday;
        }
    }

    /**
     * Gets counselors birthday
     * 
     * @return Counselors birthday
     */
    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * Passes through a Contact, if not empty, sets as counselors primary emergency
     * contact
     * 
     * @param primaryEmergencyContact
     */
    public void setPrimaryEmergencyContact(Contact primaryEmergencyContact) {
        if (primaryEmergencyContact != null) {
            this.primaryEmergencyContact = primaryEmergencyContact;
        }
    }

    /**
     * Gets specified counselors primary emergency contact
     * 
     * @return Counselors primary emergency contact
     */
    public Contact getPrimaryEmergencyContact() {
        return this.primaryEmergencyContact;
    }

    /**
     * Passes through a contact, if not empty, sets as counselors second emergency
     * contact
     * 
     * @param secondaryEmergencyContact
     */
    public void setSecondaryEmergencyContact(Contact secondaryEmergencyContact) {
        if (secondaryEmergencyContact != null) {
            this.secondaryEmergencyContact = secondaryEmergencyContact;
        }
    }

    /**
     * Gets counselors secondary emergency contact
     * 
     * @return Counselors secondary emergency contact
     */
    public Contact getSecondaryEmergencyContact() {
        return this.secondaryEmergencyContact;
    }

    /**
     * Passes through a Contact, if not empty, sets as Counselors PCP
     * 
     * @param primaryCarePhysician
     */
    public void setPrimaryCarePhysician(Contact primaryCarePhysician) {
        if (primaryCarePhysician != null) {
            this.primaryCarePhysician = primaryCarePhysician;
        }
    }

    /**
     * Gets specified counselors PCP
     * 
     * @return Counselors PCP
     */
    public Contact getPrimaryCarePhysician() {
        return this.primaryCarePhysician;
    }

    /**
     * Creates a string representation of counselors attributes
     * 
     * @return String representation of counselors attributes
     */
    public String toString() {
        return "Counselor Name: " + this.firstName + " " + this.lastName +
                "\nAllergies: " + this.allergies.toString() +
                "\nBirthday: " + this.birthday.toString() +
                "\nEmergency Contact One: " + this.primaryEmergencyContact.toString() +
                "\nEmergency Contact Two: " + this.secondaryEmergencyContact.toString() +
                "\nPrimary Care Physician: " + this.primaryCarePhysician.toString();
    }
}
