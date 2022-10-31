import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class UI {
    private static Facade f = new Facade();
    private static final String PCP = "primary care physician";
    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<Week> currentOrFutureWeeks = f.getFutureOrCurrentWeeks();

    public static void main(String args[]) {
        run();
        scan.close();
    }

    private static void run() {
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
                }
            }
            if (f.getUser() != null) {
                title("Welcome " + f.getUser().getFirstName() + "!");
                String[] options;
                switch (f.getUser().getTypeOfUser()) {
                    case COUNSELOR:
                        switch (options(new String[] { "View Schedule", "View Group", "Export Schedule",
                                "Export Week Vital Info" })) {
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
                        }
                        break;
                    case CUSTOMER:
                        if (((Customer) f.getUser()).getCampers().size() < 1) {
                            options = new String[] { "View Your Campers", "Add a Camper" };
                        } else {
                            options = new String[] { "View Your Campers", "Add a Camper",
                                    "Register a Camper" };
                        }
                        switch (options(options)) {
                            case 1:
                                viewYourCampers();
                                break;
                            case 2:
                                addACamper();
                                break;
                            case 3:
                                registerACamper();
                        }
                        break;
                    case DIRECTOR:
                        if (currentOrFutureWeeks.size() == 0) {
                            options = new String[] { "Search Campers", "Search Counselors", "Add Activity",
                                    "Add Camp Session Week", "Edit Schedule", "View Schedule", "Export Schedule" };
                        } else {
                            options = new String[] { "Search Campers", "Search Counselors", "Add Activity",
                                    "Add Camp Session Week" };
                        }
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
                                viewScheduleDirector(false);
                        }

                }
            }

        }

    }

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

    private static void addActivity() {
        title("Adding an Activity");
        String name = input("Please enter the name of the activity to add:");
        if (f.activityExists(name)) {
            title("ERROR");
            print("An activity with that name already exists.");
        } else if (f.addActivity(name,
                input("Please enter the location that the activity is performed:"),
                input("Please enter a description for the activity:"))) {
            print("Activity sucessfully added.");
            enterToExit();
        } else {
            enterToExit();
        }

    }

    private static void addWeek() {
        LocalDate date = getStartDate();
        title("Add a Week");
        if (f.addRandomizedWeek(date, input("Please enter the theme of the camp session week:"))) {
            print("Camp session week sucessfully added.");
            enterToExit();
        } else {
            actionFailed();
        }

    }

    private static void editSchedule() {
        Week week = f.getAssociatedWeek(getScheduleDate());
        if (week != null) {
            int answerDay;
            while (true) {
                title("Select Day To Edit");
                answerDay = options(f.weekDays(week));
                if (answerDay != -1) {
                    break;
                }
            }
            int answerCounselor;
            while (true) {
                title("Select Group to Edit");
                answerCounselor = options(week.counselorsToString());
                if (answerCounselor != -1) {
                    break;
                }
            }
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
            Group group = week.getGroups().get(answerCounselor - 1);
            DaySchedule oldSchedule = f.getDaySchedule(answerDay - 1,
                    group.getCounselor());
            DaySchedule current = new DaySchedule(new ArrayList<Activity>(), week, oldSchedule.getDay());
            int activity = 1;
            while (activity < DaySchedule.MAX_ACTIVITY) {
                title("Select the " + activity + f.ordinal(activity) + " Activity");
                ArrayList<Activity> legal = f.getAvailableActivities(group, current, answerDay - 1, activity - 1);
                if (legal.size() == 0) {
                    title("ERROR");
                    print("There are no activities that are valid to be added as the " + activity + f.ordinal(activity)
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
        } else {
            actionFailed();
        }
    }

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
        Week week = weeks.get(answerWeek - 1);
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
        if (exportInstead) {
            title("Export Group Schedule");
            String filename = input(
                    "Please enter the name of the file to export the schedule as (do not include a file extension):");
            if (f.exportSchedule(week.getGroups().get(answerCounselor - 1), filename)) {
                print("Schedule sucessfully exported.");
            } else {
                actionFailed();
            }
        } else {
            int answerDay;
            while (true) {
                title("Select Day to View");
                answerDay = options(f.weekDays(week));
                if (answerDay != -1) {
                    break;
                }
            }
            title("View Schedule");
            print(week.getGroups().get(answerCounselor - 1).getSchedule().get(answerDay).toString());
        }
        enterToExit();
    }

    private static void exportSchedule(boolean vitalInfo) {
        ArrayList<Week> nextWeek = f.getNextScheduledWeek();
        Week selectedWeek = null;
        switch (nextWeek.size()) {
            case 0:
                title("ERROR");
                print("You are not scheduled for any future weeks.");
                enterToExit();
                return;
            case 1:
                selectedWeek = nextWeek.get(0);
                break;
            case 2:
                while (true) {
                    title("Select Week to Export");
                    String[] options = new String[2];
                    options[0] = nextWeek.get(0).toString();
                    options[1] = nextWeek.get(1).toString();
                    int answer = options(options);
                    if (answer != -1) {
                        selectedWeek = nextWeek.get(answer - 1);
                        break;
                    }
                }
        }
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
        String filename;
        if (vitalInfo) {
            filename = input(
                    "Please enter the name of the file to export your week's vital info as (do not include a file extension):");
        } else {
            filename = input(
                    "Please enter the name of the file to export your schedule as (do not include a file extension):");
        }
        Group group = null;
        for (Group i : selectedWeek.getGroups()) {
            if (i.getCounselor().getFirstName().equals(f.getUser().getFirstName()) &&
                    i.getCounselor().getLastName().equals(f.getUser().getLastName())) {
                group = i;
            }
        }
        if (group == null || !(f.exportSchedule(group, filename))) {
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

    private static void actionFailed() {
        title("ERROR");
        print("Something went wrong. Please try again.");
        enterToExit();
    }

    private static void registerACamper() {
        title("Select the Week to Register For");
        String[] weeks = f.getStringWeeksAvailableForRegistration();
        int answerWeek = options(weeks);
        if (answerWeek != -1) {
            String[] camperOptions = f.getCamperStrings(f.getWeeksAvailableForRegistration().get(answerWeek - 1));
            if (camperOptions.length == 0) {
                title("ERROR");
                print("You have not added any campers that will be in the appropriate age range for the selected week.\n"
                        +
                        "(Between " + f.getCampLocation().getMinCamperAge() + " and "
                        + f.getCampLocation().getMaxCamperAge() + " years old).");
                enterToExit();
                return;
            } else if (camperOptions.length == 1) {
                Camper camper = f
                        .getCampersElligableForRegistration(f.getWeeksAvailableForRegistration().get(answerWeek - 1))
                        .get(0);
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
                            + ".\n(" + camper.getFirstName()
                            + " is your only camper elligable for registration).\nPlease try again.");
                    enterToExit();
                    return;
                }
            }
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
                        actionFailed();
                        return;
                    }
                }
            }
        }
    }

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
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                title("ERROR");
                print("You did not enter an integer.");
                input("Enter anything to continue.");
                continue;
            }
        }
    }

    private static Contact getCustomerConctact() {
        String phoneNumber = input("Please enter your phone number:");
        String relationship = "Self";
        String address = input("Please enter your address:");
        return f.makeContact(f.getUser().getFirstName(), f.getUser().getLastName(), f.getUser().getEmail(), phoneNumber,
                relationship, address);
    }

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

    private static void viewSchedule() {
        ArrayList<Week> nextWeek = f.getNextScheduledWeek();
        Week selectedWeek = null;
        switch (nextWeek.size()) {
            case 0:
                title("ERROR");
                print("You are not scheduled for any future weeks.");
                enterToExit();
                return;
            case 1:
                selectedWeek = nextWeek.get(0);
                break;
            case 2:
                while (true) {
                    title("Select Week to View");
                    String[] options = new String[2];
                    options[0] = nextWeek.get(0).toString();
                    options[1] = nextWeek.get(1).toString();
                    int answer = options(options);
                    if (answer != -1) {
                        selectedWeek = nextWeek.get(answer - 1);
                        break;
                    }
                }
        }
        if (selectedWeek == null) {
            // if this occurs then there is a bug (probably in f.getNextScheduledWeek())
            actionFailed();
            return;
        }
        int answer = options(f.weekDays(selectedWeek));
        Group group = null;
        for (Group i : selectedWeek.getGroups()) {
            if (i.getCounselor().getFirstName().equals(f.getUser().getFirstName()) &&
                    i.getCounselor().getLastName().equals(f.getUser().getLastName())) {
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

    private static void viewGroup() {
        title("View Group");
        int i = 1;
        for (Camper camper : f.getFirstGroup(f.getUser()).getCampers()) {
            print("\nCamper " + i + ":");
            print(camper.toString());
            i++;
        }
        enterToExit();
    }

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
            return options(new String[] { "Login", "Create Customer Account", "Create Counselor Account" });
        }

    }

    private static void createCustomerAccount() {
        title("Create Your Account");
        f.setUser(f.signUpCustomer(input("Please enter your first name:"),
                input("Please enter your last name:"),
                input("Please enter your email:"),
                input("Please enter your password:"),
                getCustomerConctact()));
        if (f.getUser() == null) {
            title("ERROR");
            input("Your email or password were invalid. Please try again.\n(Press enter to continue).");
        } else {
            f.getUser().setFirstName(input("Please enter your first name:"));
            f.getUser().setLastName(input("Please enter your last name:"));
            ((Customer) f.getUser()).setContact(getCustomerConctact());
        }
    }

    private static void login() {
        title("Login");
        f.setUser(f.login(input("Please enter your email address:"),
                input("Please enter the password for your account:")));
        if (f.getUser() == null) {
            title("ERROR");
            input("Your email or password were invalid. Please try again.\n(Press enter to continue).");
        }
    }

    private static void print(String string) {
        System.out.println(string);
    }

    private static String input(String prompt) {
        print(prompt);
        scan = new Scanner(System.in);
        String answer = scan.nextLine();
        return answer;
    }

    /**
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

    private static void basicError(String answer) {
        title("ERROR");
        input("\"" + answer + "\" is not a valid option, please try again.\n(Press enter to continue).");
        cls();
    }

    private static void title(String title) {
        cls();
        print("***** " + title + " *****");
    }

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

    public static void enterToExit() {
        input("Press enter to exit.");
    }
}