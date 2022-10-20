import java.util.UUID;
import java.util.ArrayList;

public class Group {
    private UUID id;
    private ArrayList<Camper> campers;
    private Counselor counselor;
    private int groupSize;
    private ArrayList<DaySchedule> schedule;
    private static final int MAX_CAMPERS = 6;

    public Group(UUID id, ArrayList<Camper> campers, Counselor counselor, int groupSize,
            ArrayList<DaySchedule> schedule) {
        this.id = id;
        this.campers = campers;
        this.counselor = counselor;
        this.groupSize = groupSize;
        this.schedule = schedule;
    }

    public UUID getId() {
        return this.id;
    }

    public void addCamper(Camper camper) {
        if (groupSize < MAX_CAMPERS){
            campers.add(camper);
        }
    }

    // probably will need a equals method in Camper
    public boolean removeCamper(Camper camper) {
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