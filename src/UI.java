import java.util.Scanner;

public class UI {

    public static void main(String args[]) {
        UI.welcomeScreen();
    }

    public static void welcomeScreen() {
        while (true) {
            Facade f = new Facade();
            title("Welcome to " + f.getCampLocation().getName());
            print("Located at " + f.getCampLocation().getLocation() + ", and managed by "
                    + f.getDirector().getFirstName()
                    + " " + f.getDirector().getLastName() + ".\n" + f.getCampLocation().getName()
                    + " offers many fun activities:");
            for (Activity i : f.getActivities()) {
                print(i.getName());
            }
            print("");
            String answer = input("Please select one of the following options by entering the coresponding number:\n"
                    + "(1) Login\n(2) Create Account");
            Scanner scan = new Scanner(answer);
            if (answer.equalsIgnoreCase("login")) {
                login();
            }
            if (answer.equalsIgnoreCase("create account")) {
                createAccount();
            }
            try {
                if (scan.nextInt() == 1) {
                    login();
                }
                if (scan.nextInt() == 2) {
                    createAccount();
                }
            } catch (Exception e) {
                basicError(answer);
            }
            scan.close();

        }

    }

    public static void createAccount() {
        Facade f = new Facade();
        title("Create Your Account");
        while (true){
            
        }
    }

    public static void login() {

    }

    public static void print(String string) {
        System.out.println(string);
    }

    public static String input(String prompt) {
        print(prompt);
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        scan.close();
        return answer;
    }

    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void basicError(String answer) {
        title("ERROR");
        input(answer+ "\" is not a valid option, please try again.\n(Press enter to continue).");
        cls();
    }

    public static void title(String title) {
        cls();
        print("***** " + title + " *****");
    }

}