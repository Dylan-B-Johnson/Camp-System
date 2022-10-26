// Copyright 2022 Row 3

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekList {

    private ArrayList<Week> weeks;
    private static WeekList weekList;

    private WeekList() {

    }

    public static WeekList getInstance() {
        return null;
    }

    public boolean addWeek(Week week) {
        return false;
    }
    
    public Week getCurrentWeek() {
        return null;
    }

    public ArrayList<Week> getWeeksAvailableForRegistration() {
        return null;
    }

    public DaySchedule getDaySchedule(LocalDate date, User counselor){
        for (Week i: weeks){
            for (Group j: i.getGroups()){
            }
        }
        return null;
    }

}
