// Copyright 2022 Row 3

import java.util.ArrayList;
import java.util.UUID;

public class Customer extends User {
    private ArrayList<Camper> campers;

    public Customer(UUID id, String email, String firstName, String lastName, String password,
            CampLocation campLocation,
            ArrayList<Camper> campers) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.CUSTOMER);
        this.campers = campers;
    }

    public ArrayList<Camper> getCampers() {
        return this.campers;
    }

    public String getName() {
        return this.firstName;
    }

    public void addCamper() {

    }

    public void removeCamper(String id) {

    }

    public void editCamper(String id, Camper camper) {

    }

    public double getDiscount() {
        return 404;
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
}
