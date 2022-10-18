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

    public Week(UUID id){
        this.id = id;
    }

    public void setMaxCampers(int maxCampers){
        if(maxCampers>0){
            this.maxCampers = maxCampers;
        }else{
            this.maxCampers = 0;
        }
    }

    public int getMaxCampers(){
        return this.maxCampers;
    }

    public void setCurrentCampers(int currentCampers){
        if(currentCampers>0){
            this.currentCampers = currentCampers;
        }else{
            this.currentCampers = 0;
        }
    }

    public int getCurrentCampers(){
        return this.currentCampers;
    }

    public void setStartOfWeek(LocalDate startOfWeek){
        if(startOfWeek != null){
            this.startOfWeek = startOfWeek;
        }
        else{
            this.startOfWeek = null;
        }
    }

    public LocalDate getStartOfWeek(){
        return this.startOfWeek;
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
