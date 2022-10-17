// Copyright 2022 Row 3

package campSystem;

import java.util.UUID;

public class User {
    protected UUID id;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected CampLocation campLocation;
    protected TypeOfUser typeOfUser;

    public User(String email, String firstName, String lastName, String password, CampLocation campLocation,
            TypeOfUser typeOfUser) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.campLocation = campLocation;
        this.typeOfUser = typeOfUser;
    }
}
