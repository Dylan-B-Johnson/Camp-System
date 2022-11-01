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
     * @param allergies                 List of Counselors allergies
     * @param birthday                  Counselors birthday
     * @param email                     Counselors email
     * @param firstName                 Conselors first name
     * @param lastName                  Counselors last name
     * @param password                  Counselors password
     * @param campLocation              Counselors camp location
     * @param primaryEmergencyContact   Counselors primary emergency contact
     * @param secondaryEmergencyContact Counselors secondary emergency contact
     * @param primaryCarePhysician      Counselors PCP
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
     * @param id                        Counselors ID
     * @param allergies                 Counselors allergies
     * @param birthday                  Counselors birthday
     * @param email                     Counselors email
     * @param firstName                 Counselors first name
     * @param lastName                  Counselors last name
     * @param password                  Counselors password
     * @param campLocation              Counselors camp location
     * @param primaryEmergencyContact   Counselors primary emergency contact
     * @param secondaryEmergencyContact Counselors secondary emergency contact
     * @param primaryCarePhysician      Counselors PCP
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
     * @param allergies Counselors allergies
     */
    public void setAllergies(ArrayList<String> allergies) {
        if (allergies != null) {
            this.allergies = allergies;
            DataWriter.updateCounselor(this.id, this);
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
            DataWriter.updateCounselor(this.id, this);
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
     * @param primaryEmergencyContact Counselors primary emergency contact
     */
    public void setPrimaryEmergencyContact(Contact primaryEmergencyContact) {
        if (primaryEmergencyContact != null) {
            this.primaryEmergencyContact = primaryEmergencyContact;
            DataWriter.updateCounselor(this.id, this);
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
     * @param secondaryEmergencyContact Counselors secondary emergenc contact
     */
    public void setSecondaryEmergencyContact(Contact secondaryEmergencyContact) {
        if (secondaryEmergencyContact != null) {
            this.secondaryEmergencyContact = secondaryEmergencyContact;
            DataWriter.updateCounselor(this.id, this);
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
     * @param primaryCarePhysician Counselors PCP
     */
    public void setPrimaryCarePhysician(Contact primaryCarePhysician) {
        if (primaryCarePhysician != null) {
            this.primaryCarePhysician = primaryCarePhysician;
            DataWriter.updateCounselor(this.id, this);
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
