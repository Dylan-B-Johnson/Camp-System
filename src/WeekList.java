// Copyright 2022 Row 3

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekList {

    public static boolean addWeek(Week week) {
        for(Week weekFound : getWeeksAvailableForRegistration()){
            if(weekFound.getId().equals(week.getId())){
                return false;
            }
        }
        DataWriter.createWeek(week);
        return true;
    }
    
    public static Week getCurrentWeek() {
        for(Week week : DataReader.getWeeks().values()){
            if(week.getStartOfWeek().until(LocalDate.now()).getDays()<7){
                return week;
            }
        }
        return null;
    }

    public static ArrayList<Week> getWeeksAvailableForRegistration() {
        ArrayList<Week> weeks = new ArrayList<Week>();
        for(Week week : DataReader.getWeeks().values()){
            if(week.getMaxCampers()-week.getCurrentCampers()>0){
                weeks.add(week);
            }
        }
        return weeks;
    }

    public static DaySchedule getDaySchedule(LocalDate date, User counselor){
        for (Week i: DataReader.getWeeks().values()){
            for (Group j: i.getGroups()){
                if(((Counselor)counselor).getGroup().equals(j)){
                    for(DaySchedule schedule : j.getSchedule()){
                        if(schedule.getDay().equals(date)){
                            return schedule;
                        }
                    }
                }
            }
        }
        return null;
    }

}
