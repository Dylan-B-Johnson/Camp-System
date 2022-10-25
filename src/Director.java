// Copyright 2022 Row 3

import java.util.UUID;

public class Director extends User {
    public Director(UUID id, String email, String firstName, String lastName, String password,
            CampLocation campLocation) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.DIRECTOR);
    }

    public Director(String email, String firstName, String lastName, String password,
            CampLocation campLocation) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.DIRECTOR);
    }

    public void removeCamper(String id) {

    }

    public void removeCounselor(String id) {

    }

    public void removeUser(String id) {

    }

}
