import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A list of weeks
 * 
 * @author Row 3
 */
public class WeekList {

    /**
     * Adds a week if it doesn't already exist
     * 
     * @param week The week being added to week list
     * @return False if it failed to add, true otherwise
     */
    public static boolean addWeek(Week week) {
        DataWriter.createWeek(week);
        return true;
    }

    public static ArrayList<Week> getWeeks() {
        return new ArrayList<Week>(DataReader.getWeeks().values());
    }

    /**
     * Gets the current camp week session if there is one
     * 
     * @return The current week if camp is in, null if it is not
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
     * @param date      The date of the schedule being found
     * @param counselor The counselor whos schedule is being found
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

    /**
     * Gets all the future or current weeks
     * 
     * @return A list of all future or current weeks
     */
    public static ArrayList<Week> getFutureOrCurrentWeeks() {
        ArrayList<Week> futureOrCurrentWeeks = WeekList.getFutureWeeks();
        Week current = getCurrentWeek();
        if (current != null) {
            futureOrCurrentWeeks.add(current);
        }
        return futureOrCurrentWeeks;
    }

}
