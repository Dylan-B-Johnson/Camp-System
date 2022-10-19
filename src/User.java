// Copyright 2022 Row 3

import java.util.UUID;

public abstract class User {
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

    public User(UUID id, String email, String firstName, String lastName, String password, CampLocation campLocation,
            TypeOfUser typeOfUser) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.campLocation = campLocation;
        this.typeOfUser = typeOfUser;
    }

    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Email: " + email + "\n" +
                "Type of User: " + typeOfUser;
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public static boolean emailIsValid(String email) {
        return email != null && email.contains("@") && email.contains(".") && email.length() >= 3
                && UserList.getInstance().emailAvailable(email);
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".") && email.length() >= 3
                && UserList.getInstance().emailAvailable(email))
            this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null)
            this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null)
            this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null)
            this.password = password;
    }

    public CampLocation getCampLocation() {
        return campLocation;
    }

    public void setCampLocation(CampLocation campLocation) {
        if (campLocation != null)
            this.campLocation = campLocation;
    }

    public TypeOfUser getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

}