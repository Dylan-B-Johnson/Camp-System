// Copyright 2022 Row 3

import java.util.ArrayList;
import java.util.UUID;

public class Customer extends User {
    private ArrayList<Camper> campers;

    public Customer() {
        super();
    }

    public Customer(UUID id){
        super();
        super.setId(UUID.randomUUID());
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
