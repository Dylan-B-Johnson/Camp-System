import java.util.ArrayList;
import java.util.UUID;

/**
 * A customer
 * 
 * @author Row 3
 */
public class Customer extends User {
    private ArrayList<Camper> campers;
    private Contact contactInfo;

    /**
     * Creates a Customer given the following parameters
     * 
     * @param email        Customers email
     * @param firstName    Customers first name
     * @param lastName     Customers last name
     * @param password     Customers password
     * @param campLocation Customers camp location
     * @param campers      List of Customers campers
     * @param contact      Customers contact info
     */
    public Customer(String email, String firstName, String lastName, String password,
            CampLocation campLocation,
            ArrayList<Camper> campers, Contact contact) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.CUSTOMER);
        this.campers = campers;
        this.contactInfo = contact;
    }

    /**
     * Creates a Customer given the following parameters, including ID
     * 
     * @param id           Customers ID
     * @param email        Customers email
     * @param firstName    Customers first name
     * @param lastName     Customers last name
     * @param password     Customers password
     * @param campLocation Customers camp location
     * @param campers      List of Customers campers
     * @param contact      Customers contact info
     */
    public Customer(UUID id, String email, String firstName, String lastName, String password,
            CampLocation campLocation,
            ArrayList<Camper> campers, Contact contact) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.CUSTOMER);
        this.campers = campers;
        this.contactInfo = contact;
    }

    /**
     * Gets a list of the Customers campers
     * 
     * @return Customers campers
     */
    public ArrayList<Camper> getCampers() {
        return this.campers;
    }

    /**
     * Gets the Customers contact information
     * 
     * @return Customers contact information
     */
    public Contact getContactInfo() {
        return this.contactInfo;
    }

    /**
     * Sets the Customers contact information
     * 
     * @param self Customers contact information
     */
    public void setContact(Contact self) {
        this.contactInfo = self;
        DataWriter.updateCustomer(this.id, this);
    }

    /**
     * Gets the customers first and last name
     * 
     * @return Customers full name
     */
    public String getName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    /**
     * Adds a camper to a Customer
     * 
     * @param camper Camper being added to customer
     */
    public void addCamper(Camper camper) {
        if (camper != null) {
            this.campers.add(camper);
            DataWriter.updateCustomer(this.id, this);
        }
    }

    /**
     * Removes a Camper from a Customer
     * 
     * @param id ID of camper being removed
     */
    public void removeCamper(UUID id) {
        for (Camper camper : this.campers) {
            if (camper.getId().equals(id)) {
                this.campers.remove(camper);
                DataWriter.updateCustomer(this.id, this);
            }
        }
    }

    /**
     * Edits a campers information
     * 
     * @param id     ID of Camper
     * @param camper Camper being editted
     */
    public void editCamper(UUID id, Camper camper) {
        for (Camper campers : this.campers) {
            if (campers.getId().equals(id)) {
                this.campers.set(this.campers.indexOf(camper), camper);
                DataWriter.updateCustomer(this.id, this);
            }
        }
    }

    /**
     * Gets the discount as a percentage
     * 
     * @return The discount as a percentage off of the price of enrollment
     */
    public double getDiscount() {
        for (Camper camper : this.campers) {
            if (camper.getPastEnrollment() != 0) {
                return 10;
            }
        }
        return 0;
    }

    /**
     * Creates a string representation of a Contacts attributes
     */
    public String toString() {
        String rtn = super.toString() + "Camper(s): ";
        for (int i = 0; i < campers.size(); i++) {
            rtn += campers.get(i).getFirstName() + " " + campers.get(i).getLastName();
            if (i < campers.size() - 1)
                rtn += ", ";
        }
        return rtn;
    }
}
