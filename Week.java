//Copyright Row 3
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Week {
    
    private UUID id;
    private int maxCampers;
    private int currentCampers;
    private LocalDate startOfWeek;
    private ArrayList<Group> groups;
    private CampLocation campLocation;

    public Week(LocalDate startOfWeek, int maxCampers, CampLocation campLocation){
        this.startOfWeek = startOfWeek;
        this.maxCampers = maxCampers;
        this.campLocation = campLocation;
    }

    public boolean canRegisterCamper(){

    }

    public void registerCamper(){

    }

    public boolean currentWeek(){

    }

    public boolean futureWeek(){

    }

    public boolean pastWeek(){

    }

    public String toString(){

    }
}
