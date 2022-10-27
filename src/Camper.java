// Copyright 2022 Row 3

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
        setID(id);
        setFirstName(firstName);
        setLastName(lastName);
        setAllergies(allergies);
        setBirthday(birthday);
        setPrimaryEmergencyContact(primaryEmergencyContact);
        setSecondaryEmergencyContact(secondaryEmergencyContact);
        setPrimaryCarePhysician(primaryCarePhysician);
        setPastEnrollment(pastEnrollment);
        setSwimTestResult(swimTestResult);
        setRelationshipToCustomer(relationshipToCustomer);
    }

    public Camper(String firstName, String lastName, ArrayList<String> allergies, LocalDate birthday,
            Contact primaryEmergencyContact, Contact secondaryEmergencyContact, Contact primaryCarePhysician,
            int pastEnrollment, String swimTestResult, String relationshipToCustomer) {
        setID(UUID.randomUUID());
        setFirstName(firstName);
        setLastName(lastName);
        setAllergies(allergies);
        setBirthday(birthday);
        setPrimaryEmergencyContact(primaryEmergencyContact);
        setSecondaryEmergencyContact(secondaryEmergencyContact);
        setPrimaryCarePhysician(primaryCarePhysician);
        setPastEnrollment(pastEnrollment);
        setSwimTestResult(swimTestResult);
        setRelationshipToCustomer(relationshipToCustomer);
    }

    public void setID(UUID id) {
        if (id != null) {
            this.id = id;
        }
    }

    public UUID getId() {
        return this.id;
    }

    public int getAge(Week week) {
        Period diffPeriod = Period.between(birthday, week.getStartOfWeek());
        return diffPeriod.getYears();
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        }
    }

    public String getLastName() {
        return lastName;
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

    public void setPastEnrollment(int pastEnrollment) {
        if (pastEnrollment >= 0) {
            this.pastEnrollment = pastEnrollment;
        }
    }

    public int getPastEnrollment() {
        return this.pastEnrollment;
    }

    public void setSwimTestResult(String swimTestResult) {
        if (swimTestResult != null) {
            this.swimTestResult = swimTestResult;
        }
    }

    public String getSwimTestResult() {
        return this.swimTestResult;
    }

    public void setRelationshipToCustomer(String relationshipToCustomer) {
        if (relationshipToCustomer != null) {
            this.relationshipToCustomer = relationshipToCustomer;
        }
    }

    public String getRelationshipToCustomer() {
        return relationshipToCustomer;
    }

    public String toString() {
        String allergiesString = "";
        if (allergies.size() == 0) {
            allergiesString = "none";
        } else {
            for (int i = 0; i < allergies.size(); i++) {
                allergiesString += "\n\t" + allergies.get(i);
            }
        }
        return "Camper Name: " + this.firstName + " " + this.lastName +
                "\nAllergies: " + allergiesString +
                "\nBirthday: " + this.birthday.format(DateTimeFormatter.ofPattern("E, LLL d, uuuu")) +
                "\nEmergency Contact One:\n" + this.primaryEmergencyContact.toString() +
                "\nEmergency Contact Two:\n" + this.secondaryEmergencyContact.toString() +
                "\nPrimary Care Physician:\n" + this.primaryCarePhysician.toString() +
                "\nSwim Test Status: " + this.swimTestResult;
    }

}
