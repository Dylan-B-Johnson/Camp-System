//Copyright Row 3

import java.util.ArrayList;

/**
 * Constains methods related to modifying and accessing the current activities list
 */
public class ActivitiesList {

    /**
     * Adds an activity to the lsit of activities
     * 
     * @param activity The activity that is to be added
     * @return True if the operation was performed successfully False if it was not
     */
    public static boolean addActivity(Activity activity) {
        return DataWriter.createActivity(activity);
    }

    /**
     * Gets the current list of activities
     * 
     * @return The list of activities
     */
    public static ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values());
    }
}
