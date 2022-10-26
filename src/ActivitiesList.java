//Copyright Row 3

import java.util.ArrayList;

public class ActivitiesList {

    private ActivitiesList() {

    }

    public static ActivitiesList getInstance() {
        return new ActivitiesList();
    }

    public boolean addActivity(Activity activity) {
        return DataWriter.createActivity(activity);
    }

    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values());
    }
}
