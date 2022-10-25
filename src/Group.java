import java.util.UUID;
import java.util.ArrayList;

public class Group {
    private UUID id;
    private ArrayList<Camper> campers;
    private int groupSize;
    private ArrayList<DaySchedule> schedule;
    private static final int MAX_CAMPERS = 6;

    public Group(UUID id, ArrayList<Camper> campers, int groupSize,
            ArrayList<DaySchedule> schedule) {
        this.id = id;
        this.campers = campers;
        this.groupSize = groupSize;
        this.schedule = schedule;
    }

    public UUID getId() {
        return this.id;
    }

    public void setID(UUID id){
        this.id = id;
    }

    public ArrayList<DaySchedule> getSchedule(){
        return this.schedule;
    }

    public void setSchedule(ArrayList<DaySchedule> schedule){
        this.schedule = schedule;
    }

    public int getGroupSize(){
        return this.groupSize;
    }

    public void setGroupSize(int groupSize){
        this.groupSize = groupSize;
    }

    public ArrayList<Camper> getCampers(){
        return this.campers;
    }

    public void setCampers(ArrayList<Camper> campers){
        this.campers = campers;
    }

    public void addCamper(Camper camper) {
        if (groupSize < MAX_CAMPERS) {
            campers.add(camper);
        }else{
            
        }
    }

    // probably will need a equals method in Camper
    public boolean removeCamper(Camper camper) {
        for(Camper foundCamper: this.campers){
            if(foundCamper.getId().equals(camper.getId())){
                this.campers.remove(foundCamper);
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return null;
    }

    private int minAge() {
        return -1;
    }

    private int maxAge() {
        return -1;
    }

}