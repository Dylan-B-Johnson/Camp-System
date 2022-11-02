import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * The entry point of the camp system, which starts up the user-interface
 */
public class UI {
    private static Facade f = new Facade();
    private static final String PCP = "primary care physician";
    private static Scanner scan = new Scanner(System.in);
    private static int getCurrentOrFutureWeeksLength;

    /**
     * Application entry point
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String args[]) {
        run();
        scan.close();
    }

    /**
     * The method containing the complete flow of UI screens for the program
     */
    private static void run() {
        getCurrentOrFutureWeeksLength = f.getFutureOrCurrentWeeks().size();
        while (true) {
            if (f.getUser() == null) {
                switch (welcomeScreen()) {
                    case 1:
                        login();
                        break;
                    case 2:
                        createCustomerAccount();
                        break;
                    case 3:
                        createCounselorAccount();
                        break;
                    case 4:
                        f.saveAndQuit();
                }
            }
            if (f.getUser() != null) {
                title("Welcome " + f.getUser().getFirstName() + "!");
                switch (f.getUser().getTypeOfUser()) {
                    case COUNSELOR:
                        counselor();
                        break;
                    case CUSTOMER:
                        customer();
                        break;
                    case DIRECTOR:
                        director();
                }
            }
        }
    }

    /**
     * Counselor Screen logic
     */
    private static void counselor() {
        switch (options(new String[] { "View Schedule", "View Group", "Export Schedule",
                "Export Week Vital Info", "Export Group Roster", "Quit" })) {
            case 1:
                viewSchedule();
                break;
            case 2:
                viewGroup();
                break;
            case 3:
                exportSchedule(false);
                break;
            case 4:
                exportSchedule(true);
                break;
            case 5:
                exportRoster();
                break;
            case 6:
                f.saveAndQuit();
        }
    }

    /**
     * Customer screen logic
     */
    private static void customer() {
        String[] options;
        if (((Customer) f.getUser()).getCampers().size() < 1) {
            options = new String[] { "View Your Campers", "Add a Camper", "Quit" };
            switch (options(options)) {
                case 1:
                    viewYourCampers();
                    break;
                case 2:
                    addACamper();
                    break;
                case 3:
                    f.saveAndQuit();
            }
        } else {
            options = new String[] { "View Your Campers", "Add a Camper",
                    "Register a Camper", "Quit" };
            switch (options(options)) {
                case 1:
                    viewYourCampers();
                    break;
                case 2:
                    addACamper();
                    break;
                case 3:
                    registerACamper();
                    break;
                case 4:
                    f.saveAndQuit();
            }
        }
    }

    /**
     * Director screen logic
     */
    private static void director() {
        String[] options;
        if (getCurrentOrFutureWeeksLength > 0) {
            options = new String[] { "Search Campers", "Search Counselors", "Add Activity",
                    "Add Camp Session Week", "Edit Schedule", "View Schedule", "Export Schedule",
                    "Quit" };
            switch (options(options)) {
                case 1:
                    searchCampers();
                    break;
                case 2:
                    searchCounselors();
                    break;
                case 3:
                    addActivity();
                    break;
                case 4:
                    addWeek();
                    break;
                case 5:
                    editSchedule();
                    break;
                case 6:
                    viewScheduleDirector(false);
                    break;
                case 7:
                    viewScheduleDirector(true);
                    break;
                case 8:
                    f.saveAndQuit();
            }
        } else {
            options = new String[] { "Search Campers", "Search Counselors", "Add Activity",
                    "Add Camp Session Week", "Quit" };
            switch (options(options)) {
                case 1:
                    searchCampers();
                    break;
                case 2:
                    searchCounselors();
                    break;
                case 3:
                    addActivity();
                    break;
                case 4:
                    addWeek();
                    break;
                case 5:
                    f.saveAndQuit();
            }
        }
    }

    /**
     * The screen that allows a counselor to export their groups's roster to a file
     */
    private static void exportRoster() {
        ArrayList<Week> nextWeeks = f.getCurrentOrFutureScheduledWeeks();
        if (nextWeeks.size() == 0) {
            title("ERROR");
            print("You are not scheduled for any future weeks.");
            enterToExit();
            return;
        }
        Week selectedWeek = getExportWeek(nextWeeks);
        if (selectedWeek == null) {
            // if this occurs then there is a bug (probably in f.getNextScheduledWeek())
            actionFailed();
            return;
        }
        title("Export Your Group's Roster");
        String filename = input(
                "Please enter the name of the file to export your week's roster as (do not include a file extension):");
        Group group = f.getAssociatedGroup(selectedWeek);
        if (group == null || !(f.exportRoster(group, filename, selectedWeek))) {
            actionFailed();
            return;
        } else {
            print("Sucessfully exported your group's roster.");
            enterToExit();
        }
    }

    /**
     * The screen that allows a counselor (not logged in) to create their account
     */
    private static void createCounselorAccount() {
        title("Create Your Account");
        String firstname = input("Please enter your first name: ");
        String lastname = input("Please enter your last name: ");
        ArrayList<String> allergies = getAllergiesCounselor();
        LocalDate bday = getBirthdayCounselor();
        title("Create Your Account");
        print("Please enter your first name: ");
        print(firstname);
        print("Please enter your last name: ");
        print(lastname);
        Contact pec = getEmergencyContactCounselor("primary emergency contact");
        Contact sec = getEmergencyContactCounselor("secondary emergency contact");
        Contact pcp = getEmergencyContactCounselor(PCP);
        f.setUser(f.signUpCounselor(firstname, lastname, input("Please enter your email address:"),
                input("Please enter a password for your account:"), allergies, bday, pec, sec, pcp));
        if (f.getUser() == null) {
            title("ERROR");
            input("Your email or password were invalid. Please try again.\n(Press enter to continue).");
        }
    }

    /**
     * The screen that allows the director to search for a camper
     */
    private static void searchCampers() {
        title("Search for a Camper");
        ArrayList<Camper> results = f.getCamper(input("Please enter the first name of the camper:"));
        if (results.size() == 0) {
            title("ERROR");
            print("No campers were found with that firstname. Please try again.");
            enterToExit();
            return;
        }
        title("Results");
        int i = 1;
        for (Camper camper : results) {
            print("\nCamper " + i + ":");
            print(camper.toString());
            i++;
        }
        enterToExit();
        return;
    }

    /**
     * The screen that allows the director to search for a counselor
     */
    private static void searchCounselors() {
        title("Search for a Counselor");
        ArrayList<User> results = f.getCounselor(input("Please enter the first name of the counselor:"));
        if (results.size() == 0) {
            title("ERROR");
            print("No counselors were found with that firstname. Please try again.");
            enterToExit();
            return;
        }
        title("Results");
        int i = 1;
        for (User counselor : results) {
            print("\nCounselor " + i + ":");
            print(counselor.toString());
            i++;
        }
        enterToExit();
    }

    /**
     * The screen that allows the director to add a new activity
     */
    private static void addActivity() {
        title("Adding an Activity");
        String name = input("Please enter the name of the activity to add:");
        if (f.activityExists(name)) {
            title("ERROR");
            print("An activity with that name already exists.");
            enterToExit();
        } else if (f.addActivity(name,
                input("Please enter the location that the activity is performed:"),
                input("Please enter a description for the activity:"))) {
            print("Activity sucessfully added.");
            enterToExit();
        } else {
            actionFailed();
        }

    }

    /**
     * The screen that allows the director to edit add a camp week session
     */
    private static void addWeek() {
        LocalDate date = getStartDate();
        if (f.getCounselors().size() < Week.NUM_GROUPS) {
            title("ERROR");
            print("There are less than " + Week.NUM_GROUPS
                    + " (the number of groups per week) counslesors in the system.\nPlease have" +
                    " more counselors register their accounts before creating a new week.");
            enterToExit();
            return;
        }
        title("Add a Week");
        if (f.addRandomizedWeek(date, input("Please enter the theme of the camp session week:"))) {
            print("Camp session week sucessfully added.");
            getCurrentOrFutureWeeksLength++;
            enterToExit();
        } else {
            actionFailed();
        }

    }

    /**
     * Gives the director the option of clearing the schedule for a specific day
     * 
     * @param week      The week to clear the schedules for
     * @param answerDay The day of the week to offer schedule-clearing for
     */
    private static void allowScheduleClearing(Week week, int answerDay) {
        int answer;
        while (true) {
            title("Edit Schedule");
            print("You cannot schedule an activity for more than one group at the same time.\n" +
                    "You also cannot schedule the same activity twice in one day.\n" +
                    "If you would like to not face these restrictions, you can clear the schedules for this day now.");
            print("(If you do, remember to go back and fill in the other group's schedules).");
            answer = options(new String[] { "Delete all schedules for this day", "Delete no schedules" });
            if (answer != -1) {
                break;
            }
        }
        if (answer == 1) {
            f.clearSchedules(week, answerDay - 1);
        }
    }

    /**
     * Gets the director to select a day to edit the schedule for
     * 
     * @param week The week the director will edit the schedules for
     * @return The index of the day of the week that the director will edit the
     *         schedule for
     */
    private static int selectDayToEdit(Week week) {
        int answerDay;
        while (true) {
            title("Select Day To Edit");
            answerDay = options(f.weekDays(week));
            if (answerDay != -1) {
                break;
            }
        }
        return answerDay;
    }

    /**
     * Gets the director to select a group to edit their schedule
     * 
     * @param week The week the director would like to select a group from
     * @return The index of the group the director would like to edit the schedule
     *         for
     */
    private static int selectGroupToEdit(Week week) {
        int answerCounselor;
        while (true) {
            title("Select Group to Edit");
            answerCounselor = options(week.counselorsToString());
            if (answerCounselor != -1) {
                break;
            }
        }
        return answerCounselor;
    }

    /**
     * The loop that gets the director to add in all 6 activities to the selected
     * day schedule
     * 
     * @param week      The week what the group belongs to
     * @param group     The group whose schedule is being edited
     * @param answerDay The day of the week that the director is editing
     */
    private static void addActivities(Week week, Group group, int answerDay) {
        DaySchedule oldSchedule = f.getDaySchedule(answerDay - 1,
                group.getCounselor(), week);
        DaySchedule current = new DaySchedule(new ArrayList<Activity>(), week, oldSchedule.getDay());
        int activity = 1;
        while (activity <= DaySchedule.MAX_ACTIVITY) {
            title("Select the " + f.ordinal(activity) + " Activity");
            ArrayList<Activity> legal = f.getAvailableActivities(group, current, answerDay - 1, activity - 1, week);
            if (legal.size() == 0) {
                title("ERROR");
                print("There are no activities that are valid to be added as the " + f.ordinal(activity)
                        + " activity.");
                print("Try again, try clearing this day's schedule, or try adding more activities.");
                print("The schedule will be kept as it was.");
                enterToExit();
                return;
            }
            String[] legalString = new String[legal.size()];
            for (int i = 0; i < legal.size(); i++) {
                legalString[i] = legal.get(i).getName();
            }
            int activityAnswer = options(legalString);
            if (activityAnswer == -1) {
                continue;
            }
            ArrayList<Activity> tmp = current.getActivities();
            tmp.add(legal.get(activityAnswer - 1));
            current.setActivities(tmp);
            activity++;
        }
        if (f.replaceDaySchedule(current, group, answerDay - 1)) {
            title("Sucessfully Created New Schedule");
            print(current.toString());
            enterToExit();
        } else {
            actionFailed();
        }
    }

    /**
     * The screen that lets the director edit a specific schedule
     */
    private static void editSchedule() {
        Week week = f.getAssociatedWeek(getScheduleDate());
        if (week != null) {
            int answerCounselor = selectGroupToEdit(week);
            int answerDay = selectDayToEdit(week);
            allowScheduleClearing(week, answerDay);
            Group group = week.getGroups().get(answerCounselor - 1);
            addActivities(week, group, answerDay);
        } else {
            actionFailed();
        }
    }

    /**
     * Gets the director to choose a week to view/export the schedule for
     * 
     * @param weeks         All future or current week
     * @param exportInstead Whether or not to export the schedule instead of viewing
     *                      it
     * @return The week to view/export the schedule for
     */
    private static Week getWeekToViewDirector(ArrayList<Week> weeks, boolean exportInstead) {
        String[] options = new String[weeks.size()];
        for (int i = 0; i < weeks.size(); i++) {
            options[i] = weeks.get(i).toString();
        }
        int answerWeek;
        while (true) {
            if (exportInstead) {
                title("Select Week to Export");
            } else {
                title("Select Week to View");
            }
            answerWeek = options(options);
            if (answerWeek != -1) {
                break;
            }
        }
        return weeks.get(answerWeek - 1);
    }

    /**
     * Gets the director to select a group to view/export the schedule for from a
     * specific week
     * 
     * @param week          The week to pick a group from
     * @param exportInstead Whether or not to export the schedule instead of viewing
     *                      it
     * @return The index of the group to export/view the schedule for
     */
    private static int getGroupToView(Week week, boolean exportInstead) {
        int answerCounselor;
        while (true) {
            if (exportInstead) {
                title("Select Group to Export");
            } else {
                title("Select Group to View");
            }
            answerCounselor = options(week.counselorsToString());
            if (answerCounselor != -1) {
                break;
            }
        }
        return answerCounselor;
    }

    /**
     * Gets the director to pick a day of the week to view the schedule for
     * 
     * @param week The week to view a schedule from,
     * @return The number of day of the week the director will view the schedule for
     */
    private static int getViewScheduleDayDirector(Week week) {
        int answerDay;
        while (true) {
            title("Select Day to View");
            answerDay = options(f.weekDays(week));
            if (answerDay != -1) {
                break;
            }
        }
        return answerDay - 1;
    }

    /**
     * The screen that lets the director view or export a schedule
     * 
     * @param exportInstead Whether or not to export the schedule instead of viewing
     *                      it
     */
    private static void viewScheduleDirector(boolean exportInstead) {
        ArrayList<Week> weeks = f.getFutureOrCurrentWeeks();
        if (weeks.size() == 0) {
            title("ERROR");
            if (exportInstead) {
                print("There are no future or current camp week sessions to export the schedule for.");
            } else {
                print("There are no future or current camp week sessions to view the schedules for.");
            }
            enterToExit();
            return;
        }
        Week week = getWeekToViewDirector(weeks, exportInstead);
        int answerCounselor = getGroupToView(week, exportInstead);
        if (exportInstead) {
            title("Export Group Schedule");
            String filename = input(
                    "Please enter the name of the file to export the schedule as (do not include a file extension):");
            if (f.exportSchedule(week.getGroups().get(answerCounselor - 1), filename, week)) {
                print("Schedule sucessfully exported.");
            } else {
                title("ERROR");
                print("An error occured. You may have used an invalid character in your filename.");
            }
        } else {
            int answerDay = getViewScheduleDayDirector(week);
            title("View Schedule");
            print(week.getGroups().get(answerCounselor - 1).getSchedule().get(answerDay - 1).toString());
        }
        enterToExit();
    }

    /**
     * Gets the counselor to select an upcomming scheduled week for them to export
     * the schedule or vital info for
     * 
     * @param scheduledWeeks An arraylist of the weeks the counselor is scheduled
     *                       for
     * @return The week that the counselor choose
     */
    private static Week getExportWeek(ArrayList<Week> scheduledWeeks) {
        int answer = -1;
        while (answer == -1) {
            title("Select Week to Export");
            answer = options(f.representWeeks(scheduledWeeks));
        }
        return scheduledWeeks.get(answer);
    }

    /**
     * Gets a counselor to enter in the filename for the schedule or week vital info
     * they would like to export
     * 
     * @param vitalInfo
     * @return The filename the couneslor entered
     */
    private static String getCounselorExportFilename(boolean vitalInfo) {
        String filename;
        if (vitalInfo) {
            filename = input(
                    "Please enter the name of the file to export your week's vital info as (do not include a file extension):");
        } else {
            filename = input(
                    "Please enter the name of the file to export your schedule as (do not include a file extension):");
        }
        return filename;
    }

    /**
     * The screen that allows a counselor to export a specific week's schedule they
     * are scheduled for or to export the vital info for that week
     * 
     * @param vitalInfo Whether or not to export the week's schedule, or the week's
     *                  vital info
     */
    private static void exportSchedule(boolean vitalInfo) {
        ArrayList<Week> nextWeeks = f.getCurrentOrFutureScheduledWeeks();
        if (nextWeeks.size() == 0) {
            title("ERROR");
            print("You are not scheduled for any future weeks.");
            enterToExit();
            return;
        }
        Week selectedWeek = getExportWeek(nextWeeks);
        if (selectedWeek == null) {
            // if this occurs then there is a bug (probably in f.getNextScheduledWeek())
            actionFailed();
            return;
        }
        if (vitalInfo) {
            title("Export Week Vital Info");
        } else {
            title("Export Schedule");
        }
        String filename = getCounselorExportFilename(vitalInfo);
        Group group = f.getAssociatedGroup(selectedWeek);
        if (group == null || !(f.exportSchedule(group, filename, selectedWeek))) {
            actionFailed();
            return;
        }
        if (vitalInfo) {
            print("Sucessfully exported the week's vital info.");
        } else {
            print("Sucessfully exported the schedule.");
        }
        enterToExit();
    }

    /**
     * Informs the user that there was an error and their action failed
     */
    private static void actionFailed() {
        title("ERROR");
        print("Something went wrong. Please try again.");
        enterToExit();
    }

    /**
     * Gets the customer to pick a week to register for
     * 
     * @param weeks The string representation of the weeks available for
     *              registration
     * @return The index of the week to register for (-2 if there are no weeks to
     *         register for)
     */
    private static int pickRegistrationWeek(String[] weeks) {
        int answerWeek = -1;
        if (weeks.length == 0) {
            title("ERROR");
            print("There are no open weeks for registration. Please try again later or contact the camp.");
            enterToExit();
            return -2;
        }
        while (answerWeek != -1) {
            title("Select the Week to Register For");
            answerWeek = options(weeks);
        }
        return answerWeek;
    }

    /**
     * Gets the customer to finish the registration process
     * 
     * @param camperOptions A string prepresentation of the list of campers that the
     *                      customer can register
     * @param weeks         The string representation of the weeks available for
     *                      registration
     * @param answerWeek    The index of the week that the customer choose to
     *                      register for
     */
    private static void pickCamperToRegister(String[] camperOptions, String[] weeks, int answerWeek) {
        while (true) {
            title("Select the Camper to Register");
            int answerCamper = options(camperOptions);
            if (answerCamper != -1) {
                Camper camper = f.getCampersElligableForRegistration(
                        f.getWeeksAvailableForRegistration().get(answerWeek - 1)).get(answerCamper - 1);
                if (f.registerCamper(camper.getId(), f.getWeeksAvailableForRegistration().get(answerWeek - 1))) {
                    title("Registration Complete");
                    System.out.printf("Registering "
                            + camper.getFirstName()
                            + "\nFor the week:\n" + weeks[answerWeek - 1] + "\nWill cost $%2f",

                            f.getCostOfRegistration());
                    double discount = f.getDiscoutOnRegistration();
                    if (discount != 0) {
                        System.out.printf("(Having applied a discount of $%2f).", discount);
                    }
                    enterToExit();
                    return;
                } else {
                    title("ERROR");
                    print("We could not register " + camper.getFirstName() + " for the week " + weeks[answerWeek - 1]
                            + " because their age group is at capacity.\n");
                    enterToExit();
                    return;
                }
            }
        }
    }

    /**
     * The screen that allows a customer to register one of their campers
     */
    private static void registerACamper() {
        String[] weeks = f.getStringWeeksAvailableForRegistration();
        int answerWeek = pickRegistrationWeek(weeks);
        if (answerWeek == -2) {
            return;
        }
        String[] camperOptions = f.getCamperStrings(f.getWeeksAvailableForRegistration().get(answerWeek - 1));
        if (camperOptions.length == 0) {
            title("ERROR");
            print("You have not added any campers that will be in the appropriate age range for the selected week.\n"
                    + "(Between " + f.getCampLocation().getMinCamperAge() + " and "
                    + f.getCampLocation().getMaxCamperAge() + " years old).");
            enterToExit();
            return;
        } else if (camperOptions.length == 1) {
            Camper camper = f
                    .getCampersElligableForRegistration(f.getWeeksAvailableForRegistration().get(answerWeek - 1))
                    .get(0);
            if (f.registerCamper(camper.getId(), f.getWeeksAvailableForRegistration().get(answerWeek - 1))) {
                title("Registration Complete");
                print(camper.getFirstName() + " " + camper.getLastName()
                        + " is your only camper in the appropriate age range for the selected week.\n"
                        + "(Between " + f.getCampLocation().getMinCamperAge() + " and "
                        + f.getCampLocation().getMaxCamperAge() + " years old).");
                System.out.printf("Registering "
                        + camper.getFirstName()
                        + "\nFor the week:\n" + weeks[answerWeek - 1] + "\nWill cost $%2f",
                        f.getCostOfRegistration());
                double discount = f.getDiscoutOnRegistration();
                if (discount != 0) {
                    System.out.printf("(Having applied a discount of $%2f).", discount);
                }
                enterToExit();
                return;
            } else {
                title("ERROR");
                print("We could not register " + camper.getFirstName() + " for the week " + weeks[answerWeek - 1]
                        + " because their age group is at capacity.\n(" + camper.getFirstName()
                        + " is your only camper elligable for registration).");
                enterToExit();
                return;
            }
        }
        pickCamperToRegister(camperOptions, weeks, answerWeek);
    }

    /**
     * The screen that allows a customer to view their campers
     */
    private static void viewYourCampers() {
        title("View Your Campers");
        int i = 1;
        for (Camper camper : ((Customer) (f.getUser())).getCampers()) {
            print("\nCamper " + i + ":");
            print(camper.toString());
            i++;
        }
        enterToExit();
    }

    /**
     * Gets the customer to enter in the allergies of their camper
     * 
     * @return The list of allergies the customer entered
     */
    private static ArrayList<String> getAllergies() {
        ArrayList<String> rtn = new ArrayList<String>();
        int i = 1;
        while (true) {
            title("Camper Allergies");
            String answer = input("Please enter the " + f.ordinal(i)
                    + " allergy of your camper:\n(To quit adding allergies enter \"q\").");
            if (answer.equals("q") || answer.equals("quit")) {
                return rtn;
            }
            rtn.add(answer);
            i++;
        }
    }

    /**
     * Gets the counselor to enter in their allergies
     * 
     * @return The list of allergies the counselor entered
     */
    private static ArrayList<String> getAllergiesCounselor() {
        ArrayList<String> rtn = new ArrayList<String>();
        int i = 1;
        while (true) {
            title("Allergies");
            String answer = input("Please enter your " + f.ordinal(i)
                    + " allergy:\n(To quit adding allergies enter \"q\").");
            if (answer.equals("q") || answer.equals("quit")) {
                return rtn;
            }
            rtn.add(answer);
            i++;
        }
    }

    /**
     * 
     * @return
     */
    private static LocalDate getBirthdayCounselor() {
        while (true) {
            title("Birthday");
            int day, month, year;
            try {
                month = Integer.parseInt(input("Please enter the month of your birth:"));
                if (month < 1 || month > 12) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-12).");
                    enterToExit();
                    continue;
                }
                day = Integer.parseInt(input("Please enter day of the month of your birth:"));
                if (day < 1 || day > 31) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-31).");
                    enterToExit();
                    continue;
                }
                year = Integer.parseInt(input("Please enter the number of the month of your birth:"));
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                title("ERROR");
                print("You did not enter an integer.");
                input("Enter anything to continue.");
                continue;
            }
        }
    }

    /**
     * Gets the date for a customer's camper's birthday
     * 
     * @return The date a customer's camper was born
     */
    private static LocalDate getBirthday() {
        while (true) {
            title("Camper Birthday");
            int day, month, year;
            try {
                month = Integer.parseInt(input("Please enter number of the month of your camper's birth:"));
                if (month < 1 || month > 12) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-12).");
                    enterToExit();
                    continue;
                }
                day = Integer.parseInt(input("Please enter day of the month of your camper's birth:"));
                if (day < 1 || day > 31) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-31).");
                    enterToExit();
                    continue;
                }
                year = Integer.parseInt(input("Please enter the year of your camper's birth:"));
                if (year < Calendar.getInstance().get(Calendar.YEAR) - 19
                        || year >= Calendar.getInstance().get(Calendar.YEAR)) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range for a minor.");
                    enterToExit();
                    continue;
                }
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                title("ERROR");
                print("You did not enter an integer.");
                input("Enter anything to continue.");
                continue;
            }
        }
    }

    /**
     * Gets the date for the schedule the director will edit
     * 
     * @return The date the director will edit the schedule for
     */
    private static LocalDate getScheduleDate() {
        while (true) {
            title("Day Schedule to Edit");
            int day, month, year;
            try {
                month = Integer.parseInt(
                        input("Please enter number of the month of the day schedule to edit:"));
                if (month < 1 || month > 12) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-12).");
                    enterToExit();
                    continue;
                }
                day = Integer.parseInt(
                        input("Please enter the day of the month of the day schedule to edit:"));
                if (day < 1 || day > 31) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-31).");
                    enterToExit();
                    continue;
                }
                year = Integer.parseInt(input("Please enter the year of the day schedule to edit:"));
                if (!f.isFutureOrCurrentDate(LocalDate.of(year, month, day))) {
                    title("ERROR");
                    print("You entered a date in the past.");
                    enterToExit();
                    continue;
                }
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                title("ERROR");
                print("You did not enter an integer.");
                input("Enter anything to continue.");
                continue;
            }
        }
    }

    /**
     * Selects an alternative week start day that is valid if the provided one is
     * invalid
     * 
     * @param startDay The startDay that the director would like to add a camp
     *                 session week for
     * @return A valid start date for a new week
     */
    private static LocalDate makeStartDateValid(LocalDate startDay) {
        LocalDate rtn = startDay;
        if (startDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
            title("ERROR");
            print("You did not enter a Sunday.");
            LocalDate prevSunday = startDay.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            if (f.isFutureOrCurrentDate(prevSunday)) {
                print("The previous Sunday has been selected as the start of this week instead.");
                rtn = prevSunday;
            } else {
                print("The previous Sunday has passed, as such, the next Sunday has been selected as the start of this week instead.");
                rtn = startDay.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            }
            enterToExit();
        }
        return rtn;
    }

    /**
     * Gets the director to pick a date for a new camp session week to add
     * 
     * @return The start date of adding a new camp session week
     */
    private static LocalDate getStartDate() {
        while (true) {
            title("Start of Camp Session Week");
            int day, month, year;
            try {
                month = Integer.parseInt(
                        input("Please enter number of the month of the start of the camp session week to add:"));
                if (month < 1 || month > 12) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-12).");
                    enterToExit();
                    continue;
                }
                day = Integer.parseInt(
                        input("Please enter the day of the month of the start of the camp session week to add:"));
                if (day < 1 || day > 31) {
                    title("ERROR");
                    print("You did not enter an integer in the appropriate range (1-31).");
                    enterToExit();
                    continue;
                }
                year = Integer.parseInt(input("Please enter the year of the camp session week to add:"));
                if (!f.isFutureOrCurrentDate(LocalDate.of(year, month, day))) {
                    title("ERROR");
                    print("You entered a date in the past.");
                    enterToExit();
                    continue;
                }
                return makeStartDateValid(LocalDate.of(year, month, day));
            } catch (Exception e) {
                title("ERROR");
                print("You did not enter an integer.");
                input("Enter anything to continue.");
                continue;
            }
        }
    }

    /**
     * Gets the customer to enter in their contact info
     * 
     * @return The Contact object representing the customer
     */
    private static Contact getCustomerConctact(String firstname, String lastname, String email) {
        String phoneNumber = input("Please enter your phone number:");
        String relationship = "Self";
        String address = input("Please enter your address:");
        return f.makeContact(firstname, lastname, email, phoneNumber,
                relationship, address);
    }

    /**
     * Get the counselor to enter in the contact info for one of their emergency
     * contacts
     * 
     * @param typeOfContact The type of contact that the emergency contact is
     * @return The Contact object representing the counselor's emergency contact
     */
    private static Contact getEmergencyContactCounselor(String typeOfContact) {
        String firstname = input("Please enter the first name of your " + typeOfContact + ":");
        String lastname = input("Please enter the last name of your " + typeOfContact + ":");
        String email = input("Please enter the email of your " + typeOfContact + ":");
        String phoneNumber = input("Please enter the phone number of your " + typeOfContact + ":");
        String relationship;
        if (!typeOfContact.equals(PCP)) {
            relationship = input("Please enter the relationship between you and your " + typeOfContact + ":");
        } else {
            relationship = "Primary Care Physician";
        }
        String address = input("Please enter the address of your " + typeOfContact + ":");
        return f.makeContact(firstname, lastname, email, phoneNumber, relationship, address);
    }

    /**
     * Get the customer to enter in the contact info for one of their camper's
     * emergency contacts
     * 
     * @param typeOfContact The type of contact that the emergency contact is
     * @return The Contact object representing the camper's emergency contact
     */
    private static Contact getEmergencyContact(String typeOfContact) {
        String firstname = input("Please enter the first name of your camper's " + typeOfContact + ":");
        String lastname = input("Please enter the last name of your camper's " + typeOfContact + ":");
        String email = input("Please enter the email of your camper's " + typeOfContact + ":");
        String phoneNumber = input("Please enter the phone number of your camper's " + typeOfContact + ":");
        String relationship;
        if (!typeOfContact.equals(PCP)) {
            relationship = input("Please enter the relationship between your camper and their " + typeOfContact + ":");
        } else {
            relationship = "Primary Care Physician";
        }
        String address = input("Please enter the address of your camper's " + typeOfContact + ":");
        return f.makeContact(firstname, lastname, email, phoneNumber, relationship, address);
    }

    /**
     * The screen allowing a customer to add a camper to the system
     */
    private static void addACamper() {
        title("Add a Camper");
        String firstname = input("Please enter your camper's first name: ");
        String lastname = input("Please enter your camper's last name: ");
        ArrayList<String> allergies = getAllergies();
        LocalDate bday = getBirthday();
        title("Add a Camper");
        print("Please enter your camper's first name: ");
        print(firstname);
        print("Please enter your camper's last name: ");
        print(lastname);
        String relationship = input(
                "What is your relationship to your camper?\n(I.E. enter \"mother\" if you are the camper's mother).");
        if (f.addCamperToUser(firstname, lastname, allergies, bday, relationship,
                getEmergencyContact("primary emergency contact"),
                getEmergencyContact("secondary emergency contact"), getEmergencyContact(PCP))) {
            print("Your camper has sucessfully been added!");
            enterToExit();
        } else {
            actionFailed();
        }
    }

    /**
     * Gets the counselor to select a week to view their schedule for
     * 
     * @return The week the counselor will view their schedule for
     */
    private static Week getSelectedWeekViewSchedule() {
        ArrayList<Week> scheduledWeeks = f.getCurrentOrFutureScheduledWeeks();
        if (scheduledWeeks.size() == 0) {
            title("ERROR");
            print("You are not scheduled for any future weeks.");
            enterToExit();
            return null;
        }
        int answer = -1;
        while (answer == -1) {
            title("Select Week to View");
            answer = options(f.representWeeks(scheduledWeeks));
        }
        return scheduledWeeks.get(answer);
    }

    /**
     * Gets the counselor to select a week to view their group for
     * 
     * @return The week the counselor will view their group for
     */
    private static Week getSelectedWeekViewGroup() {
        ArrayList<Week> scheduledWeeks = f.getCurrentOrFutureScheduledWeeks();
        if (scheduledWeeks.size() == 0) {
            title("ERROR");
            print("You are not scheduled for any future weeks.");
            enterToExit();
            return null;
        }
        int answer = -1;
        while (answer == -1) {
            title("Select Week to View Group for");
            answer = options(f.representWeeks(scheduledWeeks));
        }
        return scheduledWeeks.get(answer);
    }

    /**
     * The screen that allows a counselor to view their schedule on a specific day
     */
    private static void viewSchedule() {
        Week selectedWeek = getSelectedWeekViewSchedule();
        if (selectedWeek == null) {
            return;
        }
        int answer = options(f.weekDays(selectedWeek));
        Group group = null;
        for (Group i : selectedWeek.getGroups()) {
            if (i.getCounselor().getId().equals(f.getUser().getId())) {
                group = i;
            }
        }
        if (group == null) {
            actionFailed();
            return;
        }
        if (answer != -1) {
            title("View Schedule");
            print(group.getSchedule().get(answer - 1).toString());
            enterToExit();
        }
    }

    /**
     * The screen that allow a counselor to view their group
     */
    private static void viewGroup() {
        Week selectedWeek = getSelectedWeekViewGroup();
        if (selectedWeek == null) {
            return;
        }
        Group group = null;
        for (Group i : selectedWeek.getGroups()) {
            if (i.getCounselor().getId().equals(f.getUser().getId())) {
                group = i;
            }
        }
        if (group == null) {
            actionFailed();
            return;
        }
        title("View Group");
        int i = 1;
        for (Camper camper : group.getCampers()) {
            print("\nCamper " + i + ":");
            print(camper.toString());
            i++;
        }
        enterToExit();
    }

    /**
     * The landing screen that allows any user to choose whether to login or create
     * an account
     * 
     * @return 1 if the user would like to login, 2 if the user would like to
     *         create a customer account, and 3 if the user would like to create a
     *         counselor account
     */
    private static int welcomeScreen() {
        while (true) {
            title("Welcome to " + f.getCampLocation().getName());
            print("Located at " + f.getCampLocation().getLocation() + ", and managed by "
                    + f.getDirector().getFirstName()
                    + " " + f.getDirector().getLastName() + ".\n" + f.getCampLocation().getName()
                    + " offers many fun activities:");
            for (Activity i : f.getActivities()) {
                print("- " + i.getName());
            }
            print("");
            return options(new String[] { "Login", "Create Customer Account", "Create Counselor Account", "Quit" });
        }

    }

    /**
     * The screen that allows a user to create a customer account
     */
    private static void createCustomerAccount() {
        title("Create Your Account");
        String firstname = input("Please enter your first name:");
        String lastname = input("Please enter your last name:");
        String email = input("Please enter your email:");
        String password = input("Please enter your password:");
        Contact self = getCustomerConctact(firstname, lastname, email);
        f.setUser(f.signUpCustomer(firstname, lastname, email, password, self));
        if (f.getUser() == null) {
            title("ERROR");
            input("Your email or password were invalid. Please try again.\n(Press enter to continue).");
        }
    }

    /**
     * The screen that allows a user to login
     */
    private static void login() {
        title("Login");
        f.setUser(f.login(input("Please enter your email address:"),
                input("Please enter the password for your account:")));
        if (f.getUser() == null) {
            title("ERROR");
            input("Your email or password were invalid. Please try again.\n(Press enter to continue).");
        }
    }

    /**
     * A shorthand for printing to standard out
     * 
     * @param string The string to print
     */
    private static void print(String string) {
        System.out.println(string);
    }

    /**
     * A shorthand for prompting user input to standard in
     * 
     * @param prompt The string to print to standard out to prompt the suer
     * @return The next line from standard in after the output of the prompt
     */
    private static String input(String prompt) {
        print(prompt);
        scan = new Scanner(System.in);
        String answer = scan.nextLine();
        return answer;
    }

    /**
     * A method to clear the console
     * 
     * @author Code snipped derived from:
     *         https://www.javatpoint.com/how-to-clear-screen-in-java#Platform-Specific-Command
     *         Row 3 Claims no copywrite or authorship over this method
     */
    private static void cls() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

    }

    /**
     * Informs the user that their response was not a vlaid option
     * 
     * @param answer The response the user gave
     */
    private static void basicError(String answer) {
        title("ERROR");
        input("\"" + answer + "\" is not a valid option, please try again.\n(Press enter to continue).");
        cls();
    }

    /**
     * Titles a screen
     * 
     * @param title The title to give the screen
     */
    private static void title(String title) {
        cls();
        print("***** " + title + " *****");
    }

    /**
     * A method that gets a user to choose one of a list of options
     * 
     * @param options The list of options the user can choose from
     * @return -1 if the user did not provide a valid response. Otherwise, the index
     *         + 1 of the option the user choose
     */
    private static int options(String[] options) {
        String optionsString = "";
        for (int i = 0; i < options.length; i++) {
            if (i < options.length - 1) {
                optionsString += "(" + (i + 1) + ") " + options[i] + "\n";
            } else {
                optionsString += "(" + (i + 1) + ") " + options[i];
            }
        }
        String answer = input(
                "Please select one of the following options by entering the coresponding number:\n" + optionsString);
        for (int i = 0; i < options.length; i++) {
            if (answer.equalsIgnoreCase(options[i])) {
                return i + 1;
            }
        }
        scan = new Scanner(answer);
        int optionNum;
        try {
            optionNum = scan.nextInt();
        } catch (Exception e) {
            ;
            basicError(answer);
            return -1;
        }
        if (optionNum < 1 || optionNum > options.length) {
            ;
            basicError(answer);
            return -1;
        }
        ;
        return optionNum;
    }

    /**
     * A short hand to stall the program until the user enters something
     */
    public static void enterToExit() {
        input("Press enter to exit.");
    }
}