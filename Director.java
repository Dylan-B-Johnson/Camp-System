// Copyright 2022 Row 3
import java.util.UUID;

public class Director extends User {
    public Director() {
        super();
        typeOfUser=TypeOfUser.DIRECTOR;
    }

    public Director(UUID id){
        super();
        super.setId(UUID.randomUUID());
    }

    public void removeCamper(String id) {

    }

    public void removeCounselor(String id) {

    }

    public void removeUser(String id) {

    }

}
