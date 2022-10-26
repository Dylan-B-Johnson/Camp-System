import java.util.UUID;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        for (Counselor counselor : UserList.getCounselors()) {
            if (counselor.getGroup().getId().compareTo(this.id) == 0) {
                return counselor;
            }
        }
        return null;
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
        long rtn = 0;

        for (Camper camper : this.campers) {
            if (camper.getBirthday().isAfter(min)) {
                min = camper.getBirthday();
                rtn = ChronoUnit.YEARS.between(min, camper.getBirthday());
            }
        }

        return (int) rtn;
    }

    private int maxAge() {

        LocalDate max = LocalDate.now();
        long rtn = 0;

        for (Camper camper : this.campers) {
            if (camper.getBirthday().isBefore(max)) {
                max = camper.getBirthday();
                rtn = ChronoUnit.YEARS.between(max, camper.getBirthday());
            }
        }

        return (int) rtn;
    }

}