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

    public void removeCamper(String firstName, String lastName) {
        for(Camper camper : UserList.getCampers()){
            if(firstName.equals(camper.getFirstName())&&lastName.equals(camper.getLastName())){
                DataWriter.deleteCamper(camper.getId());
            }
        }
    }

    public void removeCounselor(String email) {
        for(Counselor counselor : UserList.getCounselors()){
            if(email.equals(counselor.getEmail())){
                DataWriter.deleteCounselor(counselor.getId());
            }
        }
        
    }

    public void removeCustomer(String email) {
        for(Customer customer : UserList.getCustomers()){
            if(email.equals(customer.getEmail())){
                DataWriter.deleteCustomer(customer.getId());
            }
        }
    }

}
