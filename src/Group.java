import java.util.UUID;
import java.util.ArrayList;

/**
 * A group which holds a counselor, eight campers, and their schedule for each
 * day
 * 
 * @author Row 3
 */
public class Group {
    private UUID id;
    private ArrayList<Camper> campers;
    private int groupSize;
    private ArrayList<DaySchedule> schedule;
    private Counselor counselor;
    public static final int MAX_CAMPERS = 8;
    public static final int WEEK_LENGTH = 6;

    /**
     * Creates an instance of Group, setting it's id to the supplied parameter
     * 
     * @param id        The id of the new group
     * @param campers   The campers enrolled in that group
     * @param groupSize The current number of campers in the group
     * @param schedule  The list of schedules for the group
     * @param counselor The counselor assigned to the group
     */
    public Group(UUID id, ArrayList<Camper> campers, int groupSize,
            ArrayList<DaySchedule> schedule, Counselor counselor) {
        this.id = id;
        this.campers = campers;
        this.groupSize = groupSize;
        this.schedule = schedule;
        this.counselor=counselor;
    }

    /**
     * Creates an instance of Group, setting it's id to a random UUID
     * 
     * @param campers   The campers enrolled in that group
     * @param groupSize The current number of campers in the group
     * @param schedule  The list of schedules for the group
     * @param counselor The counselor assigned to the group
     */
    public Group(ArrayList<Camper> campers, int groupSize,
            ArrayList<DaySchedule> schedule, Counselor counselor) {
        this.id = UUID.randomUUID();
        this.campers = campers;
        this.groupSize = groupSize;
        this.schedule = schedule;
        this.counselor=counselor;
    }

    /**
     * Gets the UUID of the group
     * 
     * @return The id of the group
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets the list of schedules for the group
     * 
     * @return The list of schedules for the group
     */
    public ArrayList<DaySchedule> getSchedule() {
        return this.schedule;
    }

    /**
     * Gets the counselor assigned to the group
     * 
     * @return The counselor assigned to the group
     */
    public Counselor getCounselor() {
        return this.counselor;
    }

    /**
     * Sets the Counselor for this group to the supplied counselor as long as they
     * are not already in a group
     * 
     * @param counselor The counselor that is to be assigned to the group
     * @param week      The week the group is located in
     * @return True if the operation was succesfully performed False if the
     *         operation failed
     */
    public boolean setCounselor(Counselor counselor, Week week) {
        for (Group group : week.getGroups()) {
            if (group.getCounselor().id.equals(counselor.getId())) {
                return false;
            }
        }
        this.counselor = counselor;
        DataWriter.updateGroup(this.id, this);
        return true;
    }

    /**
     * Sets the schedule to the supplied list of day schedules
     * 
     * @param schedule The schedule that is being assigned to the group
     */
    public void setSchedule(ArrayList<DaySchedule> schedule) {
        this.schedule = schedule;
        DataWriter.updateGroup(this.id, this);
    }

    /**
     * Gets the size of the group
     * 
     * @return The size of the group
     */
    public int getGroupSize() {
        return this.groupSize;
    }

    /**
     * Sets the size of the group
     * 
     * @param groupSize The size of the group
     */
    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
        DataWriter.updateGroup(this.id, this);
    }

    /**
     * Gets the campers currently in this group
     * 
     * @return The list of campers in the group
     */
    public ArrayList<Camper> getCampers() {
        return this.campers;
    }

    /**
     * Sets the campers currently in the group
     * 
     * @param campers The list of campers in the group
     */
    public void setCampers(ArrayList<Camper> campers) {
        this.campers = campers;
        DataWriter.updateGroup(this.id, this);
    }

    /**
     * Sets groups schedule to a random valid schedule for the week
     * 
     * @param week The week the group is in
     */
    public void getRandomSchedule(Week week) {
        for (int i = 0; i < WEEK_LENGTH; i++) {
            DaySchedule schedule = new DaySchedule(null, null, null);
            this.schedule.add(schedule.getRandomDaySchedule(week, week.getStartOfWeek().plusDays(i)));
            DataWriter.createDaySchedule(this.schedule.get(i));
        }
    }

    /**
     * Verifies that you can register a camper
     * 
     * @return True if you can register a camper False if you cannot
     */
    public boolean canRegisterCamper() {
        if (groupSize < MAX_CAMPERS) {
            return true;
        }
        return false;
    }

    /**
     * Adds a camper to the group as long as there is space
     * 
     * @param camper The camper that is to be added
     */
    public void addCamper(Camper camper) {
        if (groupSize < MAX_CAMPERS) {
            campers.add(camper);
            groupSize++;
            DataWriter.updateGroup(this.id, this);
        }
    }

    /**
     * Removes a camper from the group
     * 
     * @param camper The camper that is to be removed
     * @return True if the camper was succesfully removed False otherwise
     */
    public boolean removeCamper(Camper camper) {
        for (Camper foundCamper : this.campers) {
            if (foundCamper.getId().equals(camper.getId())) {
                this.campers.remove(foundCamper);
                DataWriter.updateGroup(this.id, this);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a string representation of the group
     * 
     * @return The string representation of the group
     */
    public String toString() {
        return "Counselor: " + counselor.getFirstName() + " " + counselor.getLastName() +
                "\nCampers: " + this.campers.toString() +
                "\nGroup size: " + groupSize +
                "\nSchedule: " + this.schedule.toString();
    }

}