// Copyright 2022 Row 3

import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

public class DaySchedule {
    private UUID id;
    private ArrayList<Activity> currentActivities;
    private Week week;
    private LocalDate day;
    private final int MAX_ACTIVITY = 6;

    public DaySchedule(UUID id, ArrayList<Activity> currentActivities, Week week, LocalDate day) {
        this.id = id;
        this.currentActivities = currentActivities;
        this.week = week;
        this.day = day;
    }

    public UUID getId() {
        return this.id;
    }

    public ArrayList<Activity> getCurrentAcitivities(){
        return this.currentActivities;
    }

    public LocalDate getDay(){
        return this.day;
    }

    public Week getWeek(){
        return this.week;
    }

    public boolean addToSchedule(String nameOfActivity) {
        if(currentActivities.size()<=MAX_ACTIVITY){
            HashMap<UUID, Activity> activities = DataReader.getActivities();
            for(Activity activity : activities.values()){
                if(activity.getName().equals(nameOfActivity) && verifyActivityAvailablility(nameOfActivity, currentActivities.size())){
                    currentActivities.add(activity);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean replaceActivity(int timeSLot, String nameOfNewActivity) {
        HashMap<UUID, Activity> activities = DataReader.getActivities();
        for(Activity activity : activities.values()){
            if(activity.getName().equals(nameOfNewActivity) && verifyActivityAvailablility(nameOfNewActivity, timeSLot)){
                currentActivities.set(timeSLot, activity);
                return true;
            }
        }
        return false;
    }

    private boolean verifyActivityAvailablility(String nameOfActivity, int timeSlot) {
        Activity foundActivity = new Activity(null, null, null);
        HashMap<UUID, Activity> activities = DataReader.getActivities();
        for(Activity activity : activities.values()){
            if(activity.getName().equals(nameOfActivity)){
                foundActivity = activity;
                break;
            }
        }
        ArrayList<Group> groups = DataReader.getGroups();
        for(Group group : groups){
            for(DaySchedule schedule : group.getSchedule()){
                if(schedule.getCurrentAcitivities().get(timeSlot).equals(foundActivity) 
                && schedule.getDay().equals(this.day)
                && schedule.getWeek().equals(this.week)){
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        return null;
    }
}