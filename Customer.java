// Copyright 2022 Row 3

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<Camper> campers;

    public Customer(String email, String firstName, String lastName, String password, CampLocation campLocation) {
        super(email, password);
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
}
