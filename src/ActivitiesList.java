//Copyright Row 3

import java.util.ArrayList;

public class ActivitiesList {
    
    private ArrayList<Activity> activities;
    private static ActivitiesList activitiesList;

    private ActivitiesList(){
        
    }

    public static ActivitiesList getInstance(){
        return new ActivitiesList();
    }

    public boolean addActivity(Activity activity){
        return false; 
    }

    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values());
    }
}
