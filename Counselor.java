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

    public Counselor() {
        super();
        typeOfUser = TypeOfUser.COUNSELOR;
    }

    public Counselor(UUID id) {
        super();
        super.setId(UUID.randomUUID());
    }
}
