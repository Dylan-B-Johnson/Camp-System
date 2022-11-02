import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * A Week
 * 
 * @author Row 3
 */

public class Week {

    private UUID id;
    private int maxCampers;
    private int currentCampers;
    private LocalDate startOfWeek;
    private ArrayList<Group> groups;
    private CampLocation campLocation;
    public static final int NUM_GROUPS = 6;
    public static final int WEEK_LENGTH = 7;
    private String theme;
    private int[] ageRange;

    /**
     * Creates a Week with the following parameters, including ID
     * 
     * @param id             ID for the week
     * @param maxCampers     Max number of campers for the week
     * @param currentCampers Current amount of campers for the week
     * @param startOfWeek    Start date of the week
     * @param groups         A list of all the groups for the week
     * @param campLocation   The campLocation of the week
     * @param theme          The theme for the week
     */
    public Week(UUID id, int maxCampers, int currentCampers, LocalDate startOfWeek, ArrayList<Group> groups,
            CampLocation campLocation, String theme) {
        this.id = id;
        this.startOfWeek = startOfWeek;
        this.maxCampers = maxCampers;
        this.currentCampers = currentCampers;
        this.groups = groups;
        this.campLocation = campLocation;
        int[] ageRange = { 8, 10, 12, 14, 16, 18 };
        this.ageRange = ageRange;
        this.theme = theme;
    }

    /**
     * Creates a week with the following parameters
     * 
     * @param maxCampers     The max number of campers for the week
     * @param currentCampers The current amount of campers for the week
     * @param startOfWeek    The starting date of the week
     * @param campLocation   The campLocation associated with the week
     * @param theme          The theme for the week
     */
    public Week(int maxCampers, int currentCampers, LocalDate startOfWeek,
            CampLocation campLocation, String theme) {
        this.id = UUID.randomUUID();
        this.startOfWeek = startOfWeek;
        this.maxCampers = maxCampers;
        this.currentCampers = currentCampers;
        this.campLocation = campLocation;
        int[] ageRange = { 8, 10, 12, 14, 16, 18 };
        this.ageRange = ageRange;
        this.groups = new ArrayList<Group>();
        this.theme = theme;
    }

    /**
     * Checks if the int passed is greater than 0, and if so sets maxCampers
     * 
     * @param maxCampers The max amount of campers for the week
     */
    public void setMaxCampers(int maxCampers) {
        if (maxCampers > 0) {
            this.maxCampers = maxCampers;
        } else {
            this.maxCampers = 0;
        }
        DataWriter.updateWeek(this.id, this);
    }

    /**
     * Gets the UUID for the week
     * 
     * @return UUID for week
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets the theme for the week
     * 
     * @return theme for week
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme for the week
     * 
     * @param theme The theme for the week
     */
    public void setTheme(String theme) {
        if (theme != null) {
            this.theme = theme;
            DataWriter.updateWeek(this.id, this);
        }
    }

    /**
     * Gets the max amount of campers for a week
     * 
     * @return Max amount of campers for week
     */
    public int getMaxCampers() {
        return NUM_GROUPS * Group.MAX_CAMPERS;
    }

    /**
     * Passes through an int, if greater than 0, sets the current amount of campers
     * 
     * @param currentCampers Current number of campers for the week
     */
    public void setCurrentCampers(int currentCampers) {
        if (currentCampers > 0) {
            this.currentCampers = currentCampers;
        } else {
            this.currentCampers = 0;
        }
        DataWriter.updateWeek(this.id, this);
    }

    /**
     * Gets the current amount of campers by going through each group and counting
     * each camper in each group
     * 
     * @return The total amount of campers currently enrolled in the specific week
     */
    public int getCurrentCampers() {
        int totalCampers = 0;
        for (Group group : this.groups) {
            for (Camper camper : group.getCampers()) {
                if (camper != null) {
                    totalCampers++;
                }
            }
        }
        return totalCampers;
    }

    /**
     * Passes through a LocalDate, if it isn't empty, then sets the start date of
     * the week
     * 
     * @param startOfWeek Starting date for the week
     */
    public void setStartOfWeek(LocalDate startOfWeek) {
        if (startOfWeek != null) {
            this.startOfWeek = startOfWeek;
            DataWriter.updateWeek(this.id, this);
        }
    }

    /**
     * Gets the starting date of this week
     * 
     * @return LocalDate containing starting date of week
     */
    public LocalDate getStartOfWeek() {
        return this.startOfWeek;
    }

    /**
     * Passes through a Group ArrayList and sets it to groups
     * 
     * @param groups A list of groups for the week
     */
    public void setGroups(ArrayList<Group> groups) {
        if (groups != null) {
            this.groups = groups;
            DataWriter.updateWeek(this.id, this);
        }
    }

    /**
     * Returns a Group ArrayList of the current groups
     * 
     * @return Group ArrayList of current groups
     */
    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    /**
     * Sets the current campLocation
     * 
     * @param campLocation The camp location for the week
     */
    public void setCampLocation(CampLocation campLocation) {
        this.campLocation = campLocation;
        DataWriter.updateWeek(this.id, this);
    }

    /**
     * Gets the specified campLocation
     * 
     * @return The specified campLocation
     */
    public CampLocation getCampLocation() {
        return this.campLocation;
    }

    /**
     * Passes through a camper and makes sure they are in the correct age range for
     * the camp
     * 
     * @param camper The camper being checked for registration
     * @return True if the camper is in the correct age range, false if not
     */
    public boolean canRegisterCamper(Camper camper) {
        for (Group group : this.groups) {
            if (camper.getAge(this) <= this.ageRange[groups.indexOf(group)]
                    && camper.getAge(this) >= this.ageRange[groups.indexOf(group)] - 1 && group.canRegisterCamper()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Passes through a camper and makes sure they are in the correct age range, if
     * so, they are added to their specific group
     * 
     * @param camper The camper being registered for the week
     */
    public void registerCamper(Camper camper) {
        boolean registered = false;
        for (Group group : this.groups) {
            if (camper.getAge(this) <= this.ageRange[groups.indexOf(group)] && group.canRegisterCamper()
                    && !registered) {
                camper.incrementPastEnrollments();
                group.addCamper(camper);
                registered = true;
                DataWriter.updateWeek(this.id, this);
            }
        }
    }

    /**
     * Tests if a this week is the current week
     * 
     * @return True if current week, false if not
     */
    public boolean currentWeek() {
        return startOfWeek.isEqual(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)));
    }

    /**
     * Tests if this week is a future week
     * 
     * @return True if future week, false if not
     */
    public boolean futureWeek() {
        return startOfWeek.isAfter(LocalDate.now());
    }

    /**
     * Tests if this week is a past week
     * 
     * @return True if past week, false if not
     */
    public boolean pastWeek() {
        return startOfWeek.isBefore(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)));
    }

    /**
     * Creates an ArrayList of groups and assigns a counselor and day schedule for
     * each group
     * 
     * @return ArrayList of all of the groups for the week
     */
    public void setUpGroups() {
        ArrayList<Group> groups = new ArrayList<Group>();
        for (int i = 0; i < NUM_GROUPS; i++) {
            groups.add(new Group(new ArrayList<Camper>(), Group.MAX_CAMPERS, new ArrayList<DaySchedule>(), null));
            setUniqueCounselor(groups.get(i), groups);
            groups.get(i).getRandomSchedule(this);
            DataWriter.createGroup(groups.get(i));
        }
        this.groups = groups;
    }

    private void setUniqueCounselor(Group group, ArrayList<Group> groups) {
        Random rand = new Random();
        Counselor random;
        while (true) {
            random = UserList.getCounselors().get(rand.nextInt(UserList.getCounselors().size()));
            boolean unique = true;
            for (Group i : groups) {
                if (i.getCounselor() != null && i.getCounselor().getId().equals(random.getId())) {
                    unique = false;
                }
            }
            if (unique) {
                break;
            }
        }
        group.setCounselor(random, this);
    }

    /**
     * Creates a string that represents the start and end of the week
     * 
     * @return String that displays the start and end of the week
     */
    public String toString() {
        return "\tStart of Week: " + this.startOfWeek.format(DateTimeFormatter.ofPattern("E, LLL d, uuuu"))
                + "\n\tEnd of Week: "
                + this.startOfWeek.plusDays(6).format(DateTimeFormatter.ofPattern("E, LLL d, uuuu"))
                + "\n\tTheme of Week: " + this.theme;
    }

    /**
     * Creates a string representing the names of all counselors for the week
     * 
     * @return String showing the first and last names of all counselors for the
     *         week
     */
    public String[] counselorsToString() {
        String[] rtn = new String[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            rtn[i] = groups.get(i).getCounselor().getFirstName() + " " + groups.get(i).getCounselor().getLastName();
        }
        return rtn;
    }
}