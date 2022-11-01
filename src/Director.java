// Copyright 2022 Row 3

import java.util.UUID;

/**
 * A Director, which is a child of user
 * 
 * @author Row 3
 */
public class Director extends User {

    /**
     * Creates an instance of Director, setting its id to the supplied parameter
     * 
     * @param id           The id of the new director
     * @param email        The email of the new director
     * @param firstName    The first name of the new director
     * @param lastName     The last name of the new director
     * @param password     The password of the new director
     * @param campLocation The camp location associated with the new director
     */
    public Director(UUID id, String email, String firstName, String lastName, String password,
            CampLocation campLocation) {
        super(id, email, firstName, lastName, password, campLocation, TypeOfUser.DIRECTOR);
    }

    /**
     * Creates an instance of Director and sets it's id to a random UUID
     * 
     * @param email        The email of the new director
     * @param firstName    The first name of the new director
     * @param lastName     The last name of the new director
     * @param password     The password of the new director
     * @param campLocation The camp location associated with the new director
     */
    public Director(String email, String firstName, String lastName, String password,
            CampLocation campLocation) {
        super(email, firstName, lastName, password, campLocation, TypeOfUser.DIRECTOR);
    }

    /**
     * Removes a camper from the list of campers in the camp system
     * 
     * @param firstName The first name of the camper that is going to be removed
     * @param lastName  The last name of the camper that is going to be removed
     */
    public void removeCamper(String firstName, String lastName) {
        for (Camper camper : UserList.getCampers()) {
            if (firstName.equals(camper.getFirstName()) && lastName.equals(camper.getLastName())) {
                DataWriter.deleteCamper(camper.getId());
            }
        }
    }

    /**
     * Removes a counselor from the list of counselors in the camp system
     * 
     * @param email The email of the counselor that is going to be removed
     */
    public void removeCounselor(String email) {
        for (Counselor counselor : UserList.getCounselors()) {
            if (email.equals(counselor.getEmail())) {
                DataWriter.deleteCounselor(counselor.getId());
            }
        }

    }

    /**
     * Removes a customer from the list of customers in the camp system
     * 
     * @param email The email of the customer that is going to be removed
     */
    public void removeCustomer(String email) {
        for (Customer customer : UserList.getCustomers()) {
            if (email.equals(customer.getEmail())) {
                DataWriter.deleteCustomer(customer.getId());
            }
        }
    }

    /**
     * Gives an entire week a random schedule
     * 
     * @param week The week that receives the randomized schedules
     */
    public void giveWeekRandomSchedule(Week week) {
        for (Group group : week.getGroups()) {
            group.getRandomSchedule(week);
        }
    }

}
