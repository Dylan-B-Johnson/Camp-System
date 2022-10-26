import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class UI {
    private static Facade f = new Facade();
    private static final String PCP = "primary care physician";

    public static void main(String args[]) {
        run();
    }

    private static void run() {
        while (true) {
            if (f.getUser() == null) {
                if (welcomeScreen()) {
                    login();
                } else {
                    createAccount();
                }
            }
            if (f.getUser() != null) {
                switch (f.getUser().getTypeOfUser()) {
                    case COUNSELOR:
                        if (counselorScreen()) {
                            viewSchedule();
                        } else {
                            viewGroup();
                        }
                        break;
                    case CUSTOMER:
                        switch (options(new String[] { "View Your Campers", "Add a Camper", "Register a Camper" })) {
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
                    // select appropriate user screen

                    case DIRECTOR:
                        // select appropriate user screen

                }
            }

        }
    }

    private static void registerACamper() {

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

    /**
     * A method which gets the ith number properly formatted with the
     * gramatically-correct suffix.
     * 
     * @author Bohemian
     *         (https://stackoverflow.com/users/256196/bohemian)
     *         (https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name)
     *         Row 3 claims no ownership or copywrite over this method.
     * @param i The number to get the proper suffix for the ith number for
     * @return A string for the properly formatted ith number
     */
    private static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }

    private static ArrayList<String> getAllergies() {
        ArrayList<String> rtn = new ArrayList<String>();
        int i = 1;
        while (true) {
            title("Camper Allergies");
            String answer = input("Please enter the " + ordinal(i)
                    + " allergy of your camper:\n(To quit adding allergies enter \"q\").");
            if (answer.equals("q") || answer.equals("quit")) {
                return rtn;
            }
            rtn.add(answer);
            i++;
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
                year = Integer.parseInt(input("Please enter number of the month of your camper's birth:"));
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
                enterToExit();
                continue;
            }
        }
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
        } else {
            title("ERROR");
            print("Something went wrong. Please try again.");
        }
        enterToExit();
    }

    private static void viewSchedule() {
        final int MEAL_LENGTH = 30;
        final int TRANSITION_TIME = 15;
        final int ACTIVITY_TIME = 150;
        while (true) {
            title("View Schedule");
            int answer = options(f.nextWeek());
            if (answer != -1) {
                title(f.nextWeek()[answer]);
                ArrayList<Activity> activities = f.getDaySchedule(answer, f.getUser()).getCurrentAcitivities();
                int[] start = new int[] { 6, 0 };
                int[] end = new int[] { 6, 30 };
                for (int i = 0; i < activities.size() + 3; i++) {
                    if (i == 0) {
                        print(f.displayTime(start) + "-" + f.displayTime(end) + ": Breaksfast");
                    } else if (i == 4) {
                        start = f.addTime(end[0], end[1], TRANSITION_TIME);
                        end = f.addTime(start[0], start[1], MEAL_LENGTH);
                        print(f.displayTime(start) + "-" + f.displayTime(end) + ": Lunch");
                    } else if (i == 8) {
                        start = f.addTime(end[0], end[1], TRANSITION_TIME);
                        end = f.addTime(start[0], start[1], MEAL_LENGTH);
                        print(f.displayTime(start) + "-" + f.displayTime(end) + ": Dinner");
                    } else {
                        int activitiesIndex;
                        if (i < 4) {
                            activitiesIndex = i - 1;
                        } else {
                            activitiesIndex = i - 2;
                        }
                        start = f.addTime(end[0], end[1], TRANSITION_TIME);
                        end = f.addTime(start[0], start[1], ACTIVITY_TIME + TRANSITION_TIME);
                        print(f.displayTime(start) + "-" + f.displayTime(end) + ": "
                                + activities.get(activitiesIndex).getName() +
                                "\nLocation: " + activities.get(activitiesIndex).getLocation() + "\nDescription: "
                                + activities.get(activitiesIndex).getDescription());
                    }
                }
                enterToExit();
                break;
            }
        }
    }

    private static void viewGroup() {
        title("View Group");
        int i = 1;
        for (Camper camper : f.getGroup(f.getUser()).getCampers()) {
            print("\nCamper " + i + ":");
            print(camper.toString());
            i++;
        }
        enterToExit();
    }

    private static boolean counselorScreen() {
        while (true) {
            title("Welcome " + f.getUser().getFirstName() + "!");
            switch (options(new String[] { "View Schedule", "View Group" })) {
                case 1:
                    return true;
                case 2:
                    return false;
            }

        }
    }

    private static boolean welcomeScreen() {
        while (true) {
            title("Welcome to " + f.getCampLocation().getName());
            print("Located at " + f.getCampLocation().getLocation() + ", and managed by "
                    + f.getDirector().getFirstName()
                    + " " + f.getDirector().getLastName() + ".\n" + f.getCampLocation().getName()
                    + " offers many fun activities:");
            for (Activity i : f.getActivities()) {
                print(i.getName());
            }
            print("");
            switch (options(new String[] { "Login", "Create Account" })) {
                case 1:
                    return true;
                case 2:
                    return false;
            }

        }

    }

    private static void createAccount() {
        title("Create Your Account");
        f.setUser(f.signUp(input("Please enter your email address:"),
                input("Please enter a password for your account:")));
        if (f.getUser() == null) {
            title("ERROR");
            input("Your email or password were invalid. Please try again.\n(Press enter to continue).");
        }
    }

    private static void login() {
        title("Login");
        f.setUser(f.login(input("Please enter your email address:"),
                input("Please enter a password for your account:")));
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
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        scan.close();
        return answer;
    }

    private static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void basicError(String answer) {
        title("ERROR");
        input(answer + "\" is not a valid option, please try again.\n(Press enter to continue).");
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
        Scanner scan = new Scanner(answer);
        int optionNum;
        try {
            optionNum = scan.nextInt();
        } catch (Exception e) {
            scan.close();
            basicError(answer);
            return -1;
        }
        if (optionNum < 1 || optionNum > options.length) {
            scan.close();
            basicError(answer);
            return -1;
        }
        scan.close();
        return optionNum;
    }

    public static void enterToExit() {
        input("Press enter to exit.");
    }
}