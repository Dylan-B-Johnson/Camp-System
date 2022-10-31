// Copyright 2022 Row 3

import java.util.UUID;
import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDate;
import java.util.Random;

public class DaySchedule {
    private UUID id;
    private ArrayList<Activity> currentActivities;
    private Week week;
    private LocalDate day;
    public static final int MAX_ACTIVITY = 6;

    public DaySchedule(ArrayList<Activity> currentActivities, Week week, LocalDate day) {
        this.id = UUID.randomUUID();
        this.currentActivities = currentActivities;
        this.week = week;
        this.day = day;
    }

    public DaySchedule(UUID id, ArrayList<Activity> currentActivities, Week week, LocalDate day) {
        this.id = id;
        this.currentActivities = currentActivities;
        this.week = week;
        this.day = day;
    }

    public DaySchedule getRandomDaySchedule(Week week, LocalDate day) {
        ArrayList<Activity> activities = ActivitiesList.getInstance().getActivities();
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

    public UUID getId() {
        return this.id;
    }

    public ArrayList<Activity> getCurrentAcitivities() {
        return this.currentActivities;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public Week getWeek() {
        return this.week;
    }

    public boolean addToSchedule(String nameOfActivity) {
        if (currentActivities.size() <= MAX_ACTIVITY) {
            ArrayList<Activity> activities = ActivitiesList.getInstance().getActivities();
            for (Activity activity : activities) {
                if (activity.getName().equals(nameOfActivity)
                        && verifyActivityAvailablility(nameOfActivity, currentActivities.size())) {
                    currentActivities.add(activity);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean replaceActivity(int timeSLot, String nameOfNewActivity) {
        ArrayList<Activity> activities = ActivitiesList.getInstance().getActivities();
        for (Activity activity : activities) {
            if (activity.getName().equals(nameOfNewActivity)
                    && verifyActivityAvailablility(nameOfNewActivity, timeSLot)) {
                currentActivities.set(timeSLot, activity);
                return true;
            }
        }
        return false;
    }

    private boolean verifyActivityAvailablility(String nameOfActivity, int timeSlot) {
        Activity foundActivity = new Activity(null, null, null);
        ArrayList<Activity> activities = ActivitiesList.getInstance().getActivities();
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

    public void setActivities(ArrayList<Activity> activities){
        if (activities!=null){
            this.currentActivities=activities;
        }
    }

    public ArrayList<Activity> getActivities(){
        return this.currentActivities;
    }

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