import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A camper
 * 
 * @author Row 3
 */
public class Camper {
    private UUID id;
    private String firstName;
    private String lastName;
    private ArrayList<String> allergies;
    private LocalDate birthday;
    private Contact primaryEmergencyContact;
    private Contact secondaryEmergencyContact;
    private Contact primaryCarePhysician;
    private int pastEnrollment;
    private String swimTestResult;
    private String relationshipToCustomer;

    /**
     * Creates a Camper given the following parameters, including ID
     * 
     * @param id                        ID of Camper
     * @param firstName                 First name of Camper
     * @param lastName                  Last name of Camper
     * @param allergies                 Allergies of Camper
     * @param birthday                  Campers birthday
     * @param primaryEmergencyContact   Campers Primary emergency contact
     * @param secondaryEmergencyContact Campers Secondary emergency contact
     * @param primaryCarePhysician      Campers PCP
     * @param pastEnrollment            Campers past enrollment
     * @param swimTestResult            Campers Swim Test Result
     * @param relationshipToCustomer    Campers relationship to the customer
     *                                  associated with them
     */
    public Camper(UUID id, String firstName, String lastName, ArrayList<String> allergies, LocalDate birthday,
            Contact primaryEmergencyContact, Contact secondaryEmergencyContact, Contact primaryCarePhysician,
            int pastEnrollment, String swimTestResult, String relationshipToCustomer) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
        this.pastEnrollment = pastEnrollment;
        this.swimTestResult = swimTestResult;
        this.relationshipToCustomer = relationshipToCustomer;
    }

    /**
     * Creates a camper given the following parameters
     * 
     * @param firstName                 Campers first name
     * @param lastName                  Campers last name
     * @param allergies                 Campers allergies
     * @param birthday                  Campers birthday
     * @param primaryEmergencyContact   Campers primary emergency contact
     * @param secondaryEmergencyContact Campers secondary emergency contact
     * @param primaryCarePhysician      Campers PCP
     * @param pastEnrollment            Campers past enrollment status
     * @param swimTestResult            Campers swim test result
     * @param relationshipToCustomer    Campers relationship to customer associated
     *                                  with them
     */
    public Camper(String firstName, String lastName, ArrayList<String> allergies, LocalDate birthday,
            Contact primaryEmergencyContact, Contact secondaryEmergencyContact, Contact primaryCarePhysician,
            int pastEnrollment, String swimTestResult, String relationshipToCustomer) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
        this.pastEnrollment = pastEnrollment;
        this.swimTestResult = swimTestResult;
        this.relationshipToCustomer = relationshipToCustomer;
    }

    /**
     * Gets the ID of the camper
     * 
     * @return ID of Camper
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets the age of camper by finding the difference between their birthday and
     * the start of the current week
     * 
     * @param week The current week for the camp
     * @return The age of the camper
     */
    public int getAge(Week week) {
        Period diffPeriod = Period.between(birthday, week.getStartOfWeek());
        return diffPeriod.getYears();
    }

    /**
     * Passes through a String and if it is not empty, sets it as the Campers first
     * name
     * 
     * @param firstName Campers first name
     */
    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the first name of the specified camper
     * 
     * @return First name of camper
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Passes through a String and if it is not empty, sets it as the Campers last
     * name
     * 
     * @param lastName Campers last name
     */
    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the last name of the specified camper
     * 
     * @return Last name of camper
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Passes through a String ArrayList and if not empty, sets it to campers
     * allergies
     * 
     * @param allergies Campers allergies
     */
    public void setAllergies(ArrayList<String> allergies) {
        if (allergies != null) {
            this.allergies = allergies;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the allergies for the specified camper
     * 
     * @return String ArrayList for camper
     */
    public ArrayList<String> getAllergies() {
        return this.allergies;
    }

    /**
     * Passes through a LocalDate and if not empty, sets it as the Campers birthday
     * 
     * @param birthday Campers birthday
     */
    public void setBirthday(LocalDate birthday) {
        if (birthday != null) {
            this.birthday = birthday;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the birthday of the specified camper
     * 
     * @return Campers birthday
     */
    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * Passes through a contact, if not empty sets it as campers primary emergency
     * contact
     * 
     * @param primaryEmergencyContact Campers primary emergency contact
     */
    public void setPrimaryEmergencyContact(Contact primaryEmergencyContact) {
        if (primaryEmergencyContact != null) {
            this.primaryEmergencyContact = primaryEmergencyContact;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the specified campers primary emergency contact
     * 
     * @return Campers primary emergency contact
     */
    public Contact getPrimaryEmergencyContact() {
        return this.primaryEmergencyContact;
    }

    /**
     * Passes a contact, if not empty sets as campers second emergency contact
     * 
     * @param secondaryEmergencyContact Campers secondary emergency contact
     */
    public void setSecondaryEmergencyContact(Contact secondaryEmergencyContact) {
        if (secondaryEmergencyContact != null) {
            this.secondaryEmergencyContact = secondaryEmergencyContact;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the specified campers second emergency contact
     * 
     * @return Campers secondary emergency contact
     */
    public Contact getSecondaryEmergencyContact() {
        return this.secondaryEmergencyContact;
    }

    /**
     * Passes through Contact, if not empty sets as campers PCP
     * 
     * @param primaryCarePhysician Campers PCP
     */
    public void setPrimaryCarePhysician(Contact primaryCarePhysician) {
        if (primaryCarePhysician != null) {
            this.primaryCarePhysician = primaryCarePhysician;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Returns specified campers PCP
     * 
     * @return Campers PCP
     */
    public Contact getPrimaryCarePhysician() {
        return this.primaryCarePhysician;
    }

    /**
     * Passes through an int, if greater than 0 sets as campers past enrollment
     * 
     * @param pastEnrollment Campers past enrollment status
     */
    public void setPastEnrollment(int pastEnrollment) {
        if (pastEnrollment >= 0) {
            this.pastEnrollment = pastEnrollment;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets specified campers past enrollment
     * 
     * @return Campers past enrollment
     */
    public int getPastEnrollment() {
        return this.pastEnrollment;
    }

    /**
     * Passes through a String, if not empty sets as Campers swim test result
     * 
     * @param swimTestResult Campers swim test result
     */
    public void setSwimTestResult(String swimTestResult) {
        if (swimTestResult != null) {
            this.swimTestResult = swimTestResult;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets campers swim test result
     * 
     * @return Campers swim test result
     */
    public String getSwimTestResult() {
        return this.swimTestResult;
    }

    /**
     * Passes through a string, if not empty sets the relationship to customer
     * 
     * @param relationshipToCustomer Campers relationship to customer associated
     *                               with them
     */
    public void setRelationshipToCustomer(String relationshipToCustomer) {
        if (relationshipToCustomer != null) {
            this.relationshipToCustomer = relationshipToCustomer;
            DataWriter.updateCamper(this.id, this);
        }
    }

    /**
     * Gets the specified campers relationship to customer
     * 
     * @return Campers relationship to customer
     */
    public String getRelationshipToCustomer() {
        return relationshipToCustomer;
    }

    /**
     * Creates a String representation of campers name, allergies, birthday, etc,
     * PCP, and swim test
     * 
     * @return String representation of campers attributes
     */
    public String toString() {
        String allergiesString = "";
        if (allergies.size() == 0) {
            allergiesString = "none";
        } else {
            for (int i = 0; i < allergies.size(); i++) {
                allergiesString += "\n\t" + allergies.get(i);
            }
        }
        return "Camper Name: " + this.firstName + " " + this.lastName +
                "\nAllergies: " + allergiesString +
                "\nBirthday: " + this.birthday.format(DateTimeFormatter.ofPattern("E, LLL d, uuuu")) +
                "\nEmergency Contact One:\n" + this.primaryEmergencyContact.toString() +
                "\nEmergency Contact Two:\n" + this.secondaryEmergencyContact.toString() +
                "\nPrimary Care Physician:\n" + this.primaryCarePhysician.toString() +
                "\nSwim Test Status: " + this.swimTestResult +
                "\nFuture or Current Enrollments:\n" + getEnrolledWeeksString();
    }

    /**
     * Finds the user that registered the camper
     * 
     * @return The user that registered the camper
     */
    public Customer getParent() {
        for (Customer customer : UserList.getCustomers()) {
            for (Camper camper : customer.getCampers()) {
                if (camper.getId().compareTo(this.id) == 0)
                    return customer;
            }
        }
        return null;
    }

    /**
     * Gets all future or current weeks that the camper is enrolled in
     * 
     * @return All future or current groups that contain the camper
     */
    public ArrayList<Week> getEnrolledWeeks() {
        ArrayList<Week> rtn = new ArrayList<Week>();
        for (Week week : WeekList.getFutureOrCurrentWeeks()) {
            boolean fullBreak = false;
            for (Group group : week.getGroups()) {
                for (Camper camper : group.getCampers()) {
                    if (camper.getId().equals(getId())) {
                        rtn.add(week);
                        fullBreak = true;
                        break;
                    }
                }
                if (fullBreak) {
                    break;
                }
            }
        }
        return rtn;
    }

    /**
     * Increases pastEnrollment by 1
     */
    public void incrementPastEnrollments() {
        pastEnrollment++;
        DataWriter.updateCamper(this.id, this);
    }

    private String getEnrolledWeeksString() {
        String rtn = "";
        ArrayList<Week> weeks = getEnrolledWeeks();
        for (int i = 0; i < weeks.size(); i++) {
            rtn += weeks.get(i).toString();
            if (i != weeks.size() - 1) {
                rtn += "\n";
            }
        }
        return rtn;
    }
}
