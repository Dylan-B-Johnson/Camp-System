// Copyright 2022 Row 3

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

    public Camper(UUID id, String firstName, String lastName, ArrayList<String> allergies, LocalDate birthday,
            Contact primaryEmergencyContact, Contact secondaryEmergencyContact, Contact primaryCarePhysician,
            int pastEnrollment, String swimTestResult, String relationshipToCustomer) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
        this.pastEnrollment = pastEnrollment;
        this.swimTestResult = swimTestResult;
        this.relationshipToCustomer = relationshipToCustomer;
    }

    public Camper(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }

    public int getAge() {
        return -1;
    }

    public String toString() {
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
