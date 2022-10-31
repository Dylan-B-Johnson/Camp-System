// Copyright 2022 Row 3

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Counselor extends User {
    private ArrayList<String> allergies;
    private LocalDate birthday;
    private Contact primaryEmergencyContact;
    private Contact secondaryEmergencyContact;
    private Contact primaryCarePhysician;

    public Counselor(ArrayList<String> allergies, LocalDate birthday, String email, String firstName,
            String lastName, String password,
            CampLocation campLocation, Contact primaryEmergencyContact, Contact secondaryEmergencyContact,
            Contact primaryCarePhysician) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.COUNSELOR);
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
    }

    public Counselor(UUID id, ArrayList<String> allergies, LocalDate birthday, String email,
            String firstName, String lastName, String password,
            CampLocation campLocation, Contact primaryEmergencyContact, Contact secondaryEmergencyContact,
            Contact primaryCarePhysician) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.COUNSELOR);
        this.allergies = allergies;
        this.birthday = birthday;
        this.primaryEmergencyContact = primaryEmergencyContact;
        this.secondaryEmergencyContact = secondaryEmergencyContact;
        this.primaryCarePhysician = primaryCarePhysician;
    }

    public void setAllergies(ArrayList<String> allergies) {
        if (allergies != null) {
            this.allergies = allergies;
        }
    }

    public ArrayList<String> getAllergies() {
        return this.allergies;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday != null) {
            this.birthday = birthday;
        }
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setPrimaryEmergencyContact(Contact primaryEmergencyContact) {
        if (primaryEmergencyContact != null) {
            this.primaryEmergencyContact = primaryEmergencyContact;
        }
    }

    public Contact getPrimaryEmergencyContact() {
        return this.primaryEmergencyContact;
    }

    public void setSecondaryEmergencyContact(Contact secondaryEmergencyContact) {
        if (secondaryEmergencyContact != null) {
            this.secondaryEmergencyContact = secondaryEmergencyContact;
        }
    }

    public Contact getSecondaryEmergencyContact() {
        return this.secondaryEmergencyContact;
    }

    public void setPrimaryCarePhysician(Contact primaryCarePhysician) {
        if (primaryCarePhysician != null) {
            this.primaryCarePhysician = primaryCarePhysician;
        }
    }

    public Contact getPrimaryCarePhysician() {
        return this.primaryCarePhysician;
    }

    public String toString() {
        return "Counselor Name: " + this.firstName + " " + this.lastName +
                "\nAllergies: " + this.allergies.toString() +
                "\nBirthday: " + this.birthday.toString() +
                "\nEmergency Contact One: " + this.primaryEmergencyContact.toString() +
                "\nEmergency Contact Two: " + this.secondaryEmergencyContact.toString() +
                "\nPrimary Care Physician: " + this.primaryCarePhysician.toString();
    }
}
