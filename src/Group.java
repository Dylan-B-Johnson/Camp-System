import java.util.UUID;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Group {
    private UUID id;
    private ArrayList<Camper> campers;
    private int groupSize;
    private ArrayList<DaySchedule> schedule;
    private Counselor counselor;
    public static final int MAX_CAMPERS = 8;

    public Group(UUID id, ArrayList<Camper> campers, int groupSize,
            ArrayList<DaySchedule> schedule, Counselor counselor) {
        this.id = id;
        setCampers(campers);
        setGroupSize(groupSize);
        setSchedule(schedule);
    }

    public Group(ArrayList<Camper> campers, int groupSize,
            ArrayList<DaySchedule> schedule, Counselor counselor) {
        this.id = UUID.randomUUID();
        setCampers(campers);
        setGroupSize(groupSize);
        setSchedule(schedule);
    }

    public UUID getId() {
        return this.id;
    }

    public ArrayList<DaySchedule> getSchedule() {
        return this.schedule;
    }

    public Counselor getCounselor() {
        return this.counselor;
    }

    public boolean setCounselor(Counselor counselor, Week week) {
        for(Group group : week.getGroups()){
            if(group.getCounselor().id.equals(counselor.getId())){
                return false;
            }
        }
        this.counselor = counselor;
        return true;
    }

    public void setSchedule(ArrayList<DaySchedule> schedule) {
        this.schedule = schedule;
    }

    public int getGroupSize() {
        return this.groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public ArrayList<Camper> getCampers() {
        return this.campers;
    }

    public void setCampers(ArrayList<Camper> campers) {
        this.campers = campers;
    }

    public void getRandomSchedule(Week week) {
        for (int i = 0; i < 5; i++) {
            DaySchedule schedule = new DaySchedule(null, null, null);
            this.schedule.add(schedule.getRandomDaySchedule(week, week.getStartOfWeek().plusDays(i)));
        }
    }

    public boolean canRegisterCamper(){
        if (groupSize < MAX_CAMPERS) {
            return true;
        } else {
            for (Camper aCamper : this.campers) {
                if (aCamper == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addCamper(Camper camper) {
        if (groupSize < MAX_CAMPERS) {
            campers.add(camper);
        } else {
            for (Camper aCamper : this.campers) {
                if (aCamper == null) {
                    campers.set(this.campers.indexOf(aCamper), camper);
                }
            }
        }
    }

    // probably will need a equals method in Camper
    public boolean removeCamper(Camper camper) {
        for (Camper foundCamper : this.campers) {
            if (foundCamper.getId().equals(camper.getId())) {
                this.campers.remove(foundCamper);
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "\nMinimum age: " + this.minAge() +
                "\nMaximum age: " + this.maxAge() +
                "\nCampers: " + this.campers.toString() +
                "\nGroup size: " + groupSize +
                "\nSchedule: " + this.schedule.toString();
    }

    private int minAge() {

        LocalDate min = LocalDate.of(1900, 1, 1);
        int rtn = 0;

        for (Camper camper : this.campers) {
            if (camper.getBirthday().isAfter(min)) {
                min = camper.getBirthday();
                rtn = Period.between(min, LocalDate.now()).getYears();
            }
        }

        return rtn;
    }

    private int maxAge() {

        LocalDate max = LocalDate.now();
        int rtn = 0;

        for (Camper camper : this.campers) {
            if (camper.getBirthday().isBefore(max)) {
                max = camper.getBirthday();
                rtn = Period.between(max, LocalDate.now()).getYears();
            }
        }

        return rtn;
    }

}