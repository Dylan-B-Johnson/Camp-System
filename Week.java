//Copyright Row 3
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDate;

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
        return false;
    }

    public void registerCamper(){

    }

    public boolean currentWeek(){
        return false;
    }

    public boolean futureWeek(){
        return false;
    }

    public boolean pastWeek(){
        return false;
    }

    public String toString(){
        return null;
    }
}
