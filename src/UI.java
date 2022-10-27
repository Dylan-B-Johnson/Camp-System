import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class UI {
    private static Facade f = new Facade();
    private static final String PCP = "primary care physician";
    private static Scanner scan = new Scanner(System.in);

    public static void main(String args[]) {
        run();
        scan.close();
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
                title("Welcome " + f.getUser().getFirstName() + "!");
                switch (f.getUser().getTypeOfUser()) {
                    case COUNSELOR:
                        switch (options(new String[] { "View Schedule", "View Group", "Export Schedule" })) {
                            case 1:
                                viewSchedule();
                                break;
                            case 2:
                                viewGroup();
                                break;
                            case 3:
                                exportSchedule();
                        }
                        break;
                    case CUSTOMER:
                        String[] options;
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
                        switch (options(new String[] { "Search Campers", "Search Counselors", "Add Activity",
                                "Edit Schedule", "View Schedule", "Export Schedule", "Add Week" })) {
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:

                        }

                }
            }

        }

    }

    private static void exportSchedule() {

    }


    private static void registerACamper() {
        while (true) {
            title("Select the Week to Register For");
            String[] weeks = f.getStringWeeksAvailableForRegistration();
            int answerWeek = options(weeks);
            if (answerWeek != -1) {
                String[] camperOptions = f.getCamperStrings(f.getWeeksAvailableForRegistration().get(answerWeek));
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
                            .getCampersElligableForRegistration(f.getWeeksAvailableForRegistration().get(answerWeek))
                            .get(0);
                    if (f.registerCamper(camper.getId(), f.getWeeksAvailableForRegistration().get(answerWeek))) {
                        title("Registration Complete");
                        System.out.printf("Registering "
                                + camper.getFirstName()
                                + "\nFor the week:\n" + weeks[answerWeek] + "\nWill cost $%2f",

                                f.getCostOfRegistration());
                        double discount = f.getDiscoutOnRegistration();
                        if (discount != 0) {
                            System.out.printf("(Having applied a discount of $%2f).", discount);
                        }
                        enterToExit();
                        return;
                    } else {
                        title("ERROR");
                        print("We could not register " + camper.getFirstName() + " for the week " + weeks[answerWeek]
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
                                f.getWeeksAvailableForRegistration().get(answerWeek)).get(answerCamper - 1);
                        if (f.registerCamper(camper.getId(), f.getWeeksAvailableForRegistration().get(answerWeek))) {
                            title("Registration Complete");
                            System.out.printf("Registering "
                                    + camper.getFirstName()
                                    + "\nFor the week:\n" + weeks[answerWeek] + "\nWill cost $%2f",

                                    f.getCostOfRegistration());
                            double discount = f.getDiscoutOnRegistration();
                            if (discount != 0) {
                                System.out.printf("(Having applied a discount of $%2f).", discount);
                            }
                            enterToExit();
                            return;
                        } else {
                            title("ERROR");
                            print("Something went wrong. Please try again.");
                            enterToExit();
                            return;
                        }
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
        while (true) {
            title("View Schedule");
            int answer = options(f.nextWeek());
            if (answer != -1) {
                String schedule = f.getDaySchedule(answer, f.getUser()).toString();
                if (schedule != null) {
                    print(schedule);
                } else {
                    title("ERROR");
                    print("You are not scheduled for this week.");
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

    private static boolean welcomeScreen() {
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