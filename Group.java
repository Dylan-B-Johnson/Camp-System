import java.util.UUID;
import java.util.ArrayList;

public class Group{
    private UUID id;
    private ArrayList<Camper> campers;
    private Counselor counselor;
    private int groupSize;
    private ArrayList<DaySchedule> schedule;

    public Group(int size, Counselor counselor){

    }

    public void addCamper(Camper camper){

    }

    // probably will need a equals method in Camper
    public boolean removeCamper(Camper camper){
        return false;
    }

    public String toString(){
        return null;
    }

    private int minAge(){
        return -1;
    }

    private int maxAge(){
        return -1;
    }


}