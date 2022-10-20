// Copyright 2022 Row 3

import java.util.UUID;
import java.util.ArrayList;
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

    public boolean addToSchedule(String nameOfActivity) {
        return false;
    }

    public boolean replaceActivity(int timeSLot, String nameOfNewActivity) {
        return false;
    }

    private boolean verifyActivityAvailablility(String nameOfActivity) {
        return false;
    }

    public String toString() {
        return null;
    }
}