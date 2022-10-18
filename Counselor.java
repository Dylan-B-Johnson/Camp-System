// Copyright 2022 Row 3

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Counselor extends User {
    private Group group;
    private ArrayList<String> allergies;
    private LocalDate birthday;
    private Contact primaryEmergencyContact;
    private Contact secondaryEmergencyContact;
    private Contact primaryCarePhysician;

    public Counselor(String email, String firstName, String lastName, String password,
            CampLocation campLocation) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.COUNSELOR);
    }

    public Counselor(UUID id, String email, String firstName, String lastName, String password,
            CampLocation campLocation) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.COUNSELOR);
    }
}
