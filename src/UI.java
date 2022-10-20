import java.util.LinkedList;
import java.util.Scanner;

public class UI {

    public static void main(String args[]) {
        UI.welcomeScreen();
    }

    public static void welcomeScreen() {
        while (true){
            Facade f = new Facade();
            print("***** Welcome to " + f.getCampLocation().getName() + " *****\n" +
                    "Located at " + f.getCampLocation().getLocation() + ", and managed by " + f.getDirector().getFirstName()
                    + " " + f.getDirector().getLastName() + ".\n" + f.getCampLocation().getName()
                    + " offers many fun activities:");
            for (Activity i : f.getActivities()) {
                print(i.getName());
            }
            print("");
            String answer = input("Please select one of the following options by entering the coresponding number:\n"
            + "(1) Login\n(2) Create Account");
            Scanner scan = new Scanner(answer);
            if (answer.equalsIgnoreCase("login")){

            }
            if (answer.equalsIgnoreCase("create account")){

            }
            try{
                if (scan.nextInt()==1){

                }
                if (scan.nextInt()==2){
                    
                }
            }
            catch (Exception e){
                basicError(answer);
            }
            scan.close();
           
        }

        
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

    public static void basicError(String answer){
        cls();
        print("***** ERROR *****\n\""+answer+"\" is not a valid option, please try again.\n(Press enter to continue).");
        input("");
        cls();
    }

}