// Copyright 2022 Row 3

import java.util.ArrayList;
import java.util.UUID;

public class Customer extends User {
    private ArrayList<Camper> campers;
    private Contact contactInfo;

    public Customer(String email, String firstName, String lastName, String password,
            CampLocation campLocation,
            ArrayList<Camper> campers, Contact contact) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.CUSTOMER);
        this.campers = campers;
        this.contactInfo = contact;
    }

    public Customer(UUID id, String email, String firstName, String lastName, String password,
            CampLocation campLocation,
            ArrayList<Camper> campers, Contact contact) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.CUSTOMER);
        this.campers = campers;
        this.contactInfo = contact;
    }

    public ArrayList<Camper> getCampers() {
        return this.campers;
    }

    public Contact getContactInfo() {
        return this.contactInfo;
    }

    public String getName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public void addCamper(Camper camper) {
        if (camper != null) {
            this.campers.add(camper);
        }
    }

    public void removeCamper(UUID id) {
        for (Camper camper : this.campers) {
            if (camper.getId().equals(id)) {
                this.campers.remove(camper);
            }
        }
    }

    public void editCamper(UUID id, Camper camper) {
        for (Camper campers : this.campers) {
            if (campers.getId().equals(id)) {
                this.campers.set(this.campers.indexOf(camper), camper);
            }
        }
    }

    public double getDiscount() {
        for (Camper camper : this.campers) {
            if (camper.getPastEnrollment() != 0) {
                return 10;
            }
        }
        return 0;
    }

    public String toString() {
        String rtn = super.toString() + "Camper(s): ";
        for (int i = 0; i < campers.size(); i++) {
            rtn += campers.get(i).getFirstName() + " " + campers.get(i).getLastName();
            if (i < campers.size() - 1)
                rtn += ", ";
        }
        return rtn;
    }

    public static ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }
}
