// Copyright 2022 Row 3

package campSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

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

    public Camper(String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAge() {

    }

    public String toString() {

    }
}
