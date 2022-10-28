
//Copyright Row 3
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Week {

    private UUID id;
    private int maxCampers;
    private int currentCampers;
    private LocalDate startOfWeek;
    private ArrayList<Group> groups;
    private CampLocation campLocation;
    private String theme;
    private int[] ageRange;

    public Week(UUID id, int maxCampers, int currentCampers, LocalDate startOfWeek, ArrayList<Group> groups,
            CampLocation campLocation, String theme) {
        this.id = id;
        this.startOfWeek = startOfWeek;
        this.maxCampers = maxCampers;
        this.currentCampers = currentCampers;
        this.groups = groups;
        this.campLocation = campLocation;
        int[] ageRange = {8, 10, 12, 14, 16, 18};
        this.ageRange = ageRange;
    }

    public Week(int maxCampers, int currentCampers, LocalDate startOfWeek,
            CampLocation campLocation, String theme) {
        this.id = UUID.randomUUID();
        this.startOfWeek = startOfWeek;
        this.maxCampers = maxCampers;
        this.currentCampers = currentCampers;
        this.campLocation = campLocation;
        int[] ageRange = {8, 10, 12, 14, 16, 18};
        this.ageRange = ageRange;
    }

    public void setMaxCampers(int maxCampers) {
        if (maxCampers > 0) {
            this.maxCampers = maxCampers;
        } else {
            this.maxCampers = 0;
        }
    }

    public UUID getId() {
        return this.id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        if (theme != null) {
            this.theme = theme;
        }
    }

    public int getMaxCampers() {
        int totalCampers = 0;
        for(Group group : this.groups){
            totalCampers+=6;
        }
        return totalCampers;
    }

    public void setCurrentCampers(int currentCampers) {
        if (currentCampers > 0) {
            this.currentCampers = currentCampers;
        } else {
            this.currentCampers = 0;
        }
    }

    public int getCurrentCampers() {
        int totalCampers = 0;
        for(Group group : this.groups){
            for(Camper camper : group.getCampers()){
                if(camper != null){
                    totalCampers++;
                }
            }
        }
        return totalCampers;
    }

    public void setStartOfWeek(LocalDate startOfWeek) {
        if (startOfWeek != null) {
            this.startOfWeek = startOfWeek;
        } else {
            this.startOfWeek = null;
        }
    }

    public LocalDate getStartOfWeek() {
        return this.startOfWeek;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public void setCampLocation(CampLocation campLocation) {
        this.campLocation = campLocation;
    }

    public CampLocation getCampLocation() {
        return this.campLocation;
    }

    public boolean canRegisterCamper(Camper camper) {
        for(Group group : this.groups){
            if(camper.getAge(this) <= this.ageRange[groups.indexOf(group)] && group.canRegisterCamper()){
                return true;
            }
        }
        return false;
    }

    public void registerCamper(Camper camper) {
        boolean registered = false;
        for(Group group : this.groups){
            if(camper.getAge(this) <= this.ageRange[groups.indexOf(group)] && group.canRegisterCamper() && !registered){
                group.addCamper(camper);
                registered = true;
            }
        }
    }

    public boolean currentWeek() {
        for (Week week : DataReader.getWeeks().values()) {
            if (week.getStartOfWeek().until(LocalDate.now()).getDays() < 7
                    && week.getStartOfWeek().until(LocalDate.now()).getDays() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean futureWeek() {
        for (Week week : DataReader.getWeeks().values()) {
            if (week.getStartOfWeek().until(LocalDate.now()).getDays() > 7) {
                return true;
            }
        }
        return false;
    }

    public boolean pastWeek() {
        for (Week week : DataReader.getWeeks().values()) {
            if (week.getStartOfWeek().until(LocalDate.now()).getDays() < 0) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Group> setUpGroups(){
        Random rand = new Random();
        ArrayList<Group> groups = new ArrayList<Group>();
        for(int i=0; i<6; i++){
            groups.add(new Group(new ArrayList<Camper>(), 8, new ArrayList<DaySchedule>(), null));
            boolean set = false;
            while(!set){
                set = groups.get(i).setCounselor(UserList.getCounselors().get(rand.nextInt(UserList.getCounselors().size())), this);
            }
        }
        return groups;
    }

    public String toString() {
        return "\tStart of Week: " + this.startOfWeek.format(DateTimeFormatter.ofPattern("E, LLL d, uuuu"))
                + "\n\tEnd of Week: "
                + this.startOfWeek.plusDays(6).format(DateTimeFormatter.ofPattern("E, LLL d, uuuu"));
    }
}
