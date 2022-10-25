import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private static Facade f = new Facade();

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
                        // select appropriate user screen

                        break;
                    // select appropriate user screen

                    case DIRECTOR:
                        // select appropriate user screen

                }
            }

        }
    }

    private static void viewSchedule() {
        final int MEAL_LENGTH=30;
        final int TRANSITION_TIME=15;
        final int ACTIVITY_TIME=150;
        while (true) {
            title("View Schedule");
            int answer = options(f.nextWeek());
            if (answer != -1) {
                title(f.nextWeek()[answer]);
                ArrayList<Activity> activities = f.getDaySchedule(answer, f.getUser()).getCurrentAcitivities();
                int[] start = new int[] {6,0};
                int[] end = new int[] {6,30};
                for (int i = 0; i < activities.size() + 3; i++) {
                    if(i==0){
                        print(f.displayTime(start)+"-"+f.displayTime(end)+": Breaksfast");
                    }
                    else if(i==4){
                        start = f.addTime(end[0], end[1], TRANSITION_TIME);
                        end = f.addTime(start[0], start[1], MEAL_LENGTH);
                        print(f.displayTime(start)+"-"+f.displayTime(end)+": Lunch");
                    }
                    else if (i==8){
                        start = f.addTime(end[0], end[1], TRANSITION_TIME);
                        end = f.addTime(start[0], start[1], MEAL_LENGTH);
                        print(f.displayTime(start)+"-"+f.displayTime(end)+": Dinner");
                    }
                    else {
                        int activitiesIndex;
                        if (i<4){
                            activitiesIndex=i-1;
                        }
                        else{
                            activitiesIndex=i-2;
                        }
                        start = f.addTime(end[0], end[1], TRANSITION_TIME);
                        end = f.addTime(start[0], start[1], ACTIVITY_TIME+TRANSITION_TIME);
                        print(f.displayTime(start)+"-"+f.displayTime(end)+": "+activities.get(activitiesIndex));
                    }
                }
                input("Press enter to exit.");
                break;
            }
        }
    }

    private static void viewGroup() {

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
}