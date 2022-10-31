import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A list of weeks
 * 
 * @author Row 3
 */
public class WeekList {

    /**
     * Passes through a week and checks if it is available for registration, then
     * creates the week
     * 
     * @param week
     * @return False if the week is not available for registration, true if it is
     */
    public static boolean addWeek(Week week) {
        for (Week weekFound : getWeeksAvailableForRegistration()) {
            if (weekFound.getId().equals(week.getId())) {
                return false;
            }
        }
        DataWriter.createWeek(week);
        return true;
    }

    /**
     * Gets the current week by making sure the start of the week is less than 7
     * 
     * @return The current week if it is the correct date, nothing if it is not
     */
    public static Week getCurrentWeek() {
        for (Week week : DataReader.getWeeks().values()) {
            if (week.getStartOfWeek().until(LocalDate.now()).getDays() < 7
                    && week.getStartOfWeek().until(LocalDate.now()).getDays() > 0) {
                return week;
            }
        }
        return null;
    }

    /**
     * Gets the weeks that are available for registration
     * 
     * @return A week ArrayList of the weeks that are currently available for
     *         registration
     */
    public static ArrayList<Week> getWeeksAvailableForRegistration() {
        ArrayList<Week> weeks = new ArrayList<Week>();
        ArrayList<Week> futureOrCurrentWeeks = getFutureWeeks();
        Week current = getCurrentWeek();
        if (current != null) {
            futureOrCurrentWeeks.add(current);
        }
        for (Week week : futureOrCurrentWeeks) {
            if (week.getMaxCampers() - week.getCurrentCampers() > 0) {
                weeks.add(week);
            }
        }
        return weeks;
    }

    /**
     * Gets every future week thst currently exists
     * 
     * @return A week ArrayList of all future weeks
     */
    public static ArrayList<Week> getFutureWeeks() {
        ArrayList<Week> rtn = new ArrayList<Week>();
        for (Week i : DataReader.getWeeks().values()) {
            if (i.futureWeek()) {
                rtn.add(i);
            }
        }
        return rtn;
    }

    /**
     * Passes through a date and counselor, and uses that to find their day schedule
     * 
     * @param date
     * @param counselor
     * @return The day schedule for the counselor if their ID matches, nothing if
     *         not
     */
    public static DaySchedule getDaySchedule(LocalDate date, User counselor) {
        for (Week i : DataReader.getWeeks().values()) {
            for (Group j : i.getGroups()) {
                if (j.getCounselor().getId().equals(counselor.getId())) {
                    for (DaySchedule schedule : j.getSchedule()) {
                        if (schedule.getDay().equals(date)) {
                            return schedule;
                        }
                    }
                }
            }
        }
        return null;
    }

}
