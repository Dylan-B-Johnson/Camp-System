import java.util.UUID;
import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDate;
import java.util.Random;

/**
 * A Day Schedule
 * 
 * @author Row 3
 */

public class DaySchedule {
    private UUID id;
    private ArrayList<Activity> currentActivities;
    private Week week;
    private LocalDate day;
    public static final int MAX_ACTIVITY = 6;

    /**
     * Creates a Day Schedule given the following parameters
     * 
     * @param currentActivities Activities for the current day
     * @param week              Week that the Day Schedule falls under
     * @param day               Day of the schedule
     */
    public DaySchedule(ArrayList<Activity> currentActivities, Week week, LocalDate day) {
        this.id = UUID.randomUUID();
        this.currentActivities = currentActivities;
        this.week = week;
        this.day = day;
    }

    /**
     * Creates a Day Schedule given the following parameters, including ID
     * 
     * @param id                DaySchedules UUID
     * @param currentActivities Activities for the current day
     * @param week              Week that the day schedule falls under
     * @param day               Day of the schedule
     */
    public DaySchedule(UUID id, ArrayList<Activity> currentActivities, Week week, LocalDate day) {
        this.id = id;
        this.currentActivities = currentActivities;
        this.week = week;
        this.day = day;
    }

    /**
     * Sets the activities for a day schedule randomly, then returns it
     * 
     * @param week Week that the day schedule falls under
     * @param day  Day of the schedule
     * @return A Day Schedule with random activities
     */
    public DaySchedule getRandomDaySchedule(Week week, LocalDate day) {
        ArrayList<Activity> activities = ActivitiesList.getActivities();
        ArrayList<Activity> curActivities = new ArrayList<Activity>();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            Activity nextActivity = activities.get(random.nextInt(activities.size()));
            while (!verifyActivityAvailablility(nextActivity.getName(), i) || curActivities.contains(nextActivity)) {
                nextActivity = activities.get(random.nextInt(activities.size()));
            }
            curActivities.set(i, nextActivity);
        }
        return new DaySchedule(curActivities, week, day);
    }

    /**
     * Gets the UUID of a day schedule
     * 
     * @return Day Schedules UUID
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets a list of the activities for the day schedule
     * 
     * @return Activities for day schedule
     */
    public ArrayList<Activity> getCurrentAcitivities() {
        return this.currentActivities;
    }

    /**
     * Gets the day of a day schedule
     * 
     * @return Day of day schedule
     */
    public LocalDate getDay() {
        return this.day;
    }

    /**
     * Gets the week the day schedule falls under
     * 
     * @return Week of day schedule
     */
    public Week getWeek() {
        return this.week;
    }

    /**
     * Adds an activity to a day schedule
     * 
     * @param nameOfActivity Activity being added to the day schedule
     * @return True if activity is successfully added, false if not
     */
    public boolean addToSchedule(String nameOfActivity) {
        if (currentActivities.size() <= MAX_ACTIVITY) {
            ArrayList<Activity> activities = ActivitiesList.getActivities();
            for (Activity activity : activities) {
                if (activity.getName().equals(nameOfActivity)
                        && verifyActivityAvailablility(nameOfActivity, currentActivities.size())) {
                    currentActivities.add(activity);
                    DataWriter.updateDaySchedule(this.id, this);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Replaces an old activity with a new activity
     * 
     * @param timeSLot          Time of the activity that wants replaced
     * @param nameOfNewActivity The new activity replacing the old one
     * @return True if successful, false if not
     */
    public boolean replaceActivity(int timeSLot, String nameOfNewActivity) {
        ArrayList<Activity> activities = ActivitiesList.getActivities();
        for (Activity activity : activities) {
            if (activity.getName().equals(nameOfNewActivity)
                    && verifyActivityAvailablility(nameOfNewActivity, timeSLot)) {
                currentActivities.set(timeSLot, activity);
                DataWriter.updateDaySchedule(this.id, this);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies whether an activity is currently available or not
     * 
     * @param nameOfActivity Name of the activity being checked
     * @param timeSlot       The time that the activity is checked
     * @return True if the activity is available, false if not
     */
    private boolean verifyActivityAvailablility(String nameOfActivity, int timeSlot) {
        Activity foundActivity = new Activity(null, null, null);
        ArrayList<Activity> activities = ActivitiesList.getActivities();
        for (Activity activity : activities) {
            if (activity.getName().equals(nameOfActivity)) {
                foundActivity = activity;
                break;
            }
        }
        Collection<Group> groups = DataReader.getGroups().values();
        for (Group group : groups) {
            for (DaySchedule schedule : group.getSchedule()) {
                if (schedule.getCurrentAcitivities().get(timeSlot).equals(foundActivity)
                        && schedule.getDay().equals(this.day)
                        && schedule.getWeek().equals(this.week)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets a list of activities for a new day schedule
     */
    public void setActivities(ArrayList<Activity> activities) {
        if (activities != null) {
            this.currentActivities = activities;
            DataWriter.updateDaySchedule(this.id, this);
        }
    }

    /**
     * Gets a list of the activity on a day schedule
     * 
     * @return Day Schedules activities
     */
    public ArrayList<Activity> getActivities() {
        return this.currentActivities;
    }

    /**
     * Creates a String representation of a Day schedule, including all meal times
     * and activities
     */
    public String toString() {
        return "Breakfast (6:00-6:30)" +
                "\nActivity 1 (6:45-8:15): \n" + activitiesSafeGet(0) +
                "\nActivity 2 (8:30-10:00): \n" + activitiesSafeGet(1) +
                "\nActivity 3 (10:15-11:45): \n" + activitiesSafeGet(2) +
                "\nLunch (12:00-12:30)" +
                "\nActivity 4 (12:45-2:15): \n" + activitiesSafeGet(3) +
                "\nActivity 5 (2:30-4:00): \n" + activitiesSafeGet(4) +
                "\nActivity 6 (4:15-5:45): \n" + activitiesSafeGet(5) +
                "\nDinner (6:00-6:30)";
    }

    /**
     * Checks to see if an activity is set
     * 
     * @param i Index of activity
     * @return Name of activity if scheduled, None Scheduled if not
     */
    public String activitiesSafeGet(int i) {
        String rtn;
        try {
            rtn = "" + currentActivities.get(i);
        } catch (Exception e) {
            rtn = "None Scheduled";
        }
        return rtn;
    }
}