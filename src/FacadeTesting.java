
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FacadeTesting {
    private static Scanner reader;
    private static String activitiesBackup;
    private static String campersBackup;
    private static String campLocationBackup;
    private static String counselorsBackup;
    private static String customersBackup;
    private static String daySchedulesBackup;
    private static String directorBackup;
    private static String groupsBackup;
    private static String weeksBackup;
    private Contact bobContact;
    private Contact pcpContact;
    private Contact emergencyContact1, emergencyContact2;
    private Facade f = new Facade();

    public static void main(String args[]) {
        customersBackup = readJSON("customers");
        emptyJSON("customers");
        restoreJSON("customers");
    }

    private static String readJSON(String name) {
        try {
            reader = new Scanner(new File("data/" + name + ".json"));
            String json = "";
            while (reader.hasNextLine()) {
                json += reader.nextLine();
            }
            return json;
        } catch (FileNotFoundException e) {
            return null;
        }

    }

    private static void emptyJSON(String name) {
        try {
            PrintWriter pw = new PrintWriter(new File("data/" + name + ".json"));
            pw.println("");
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void restoreJSON(String name) {
        try {
            PrintWriter pw = new PrintWriter(new File("data/" + name + ".json"));
            if (name.equals("activities")) {
                pw.println(activitiesBackup);
            }
            if (name.equals("campers")) {
                pw.println(campersBackup);
            }
            if (name.equals("campLocation")) {
                pw.println(campLocationBackup);
            }
            if (name.equals("counselors")) {
                pw.println(counselorsBackup);
            }
            if (name.equals("customers")) {
                pw.println(customersBackup);
            }
            if (name.equals("daySchedules")) {
                pw.println(daySchedulesBackup);
            }
            if (name.equals("director")) {
                pw.println(directorBackup);
            }
            if (name.equals("groups")) {
                pw.println(groupsBackup);
            }
            if (name.equals("weeks")) {
                pw.println(weeksBackup);
            }
            pw.close();

        } catch (Exception e) {
        }
    }

    @BeforeEach
    public void setup() {
        // Read in all used JSONs to String
        activitiesBackup = readJSON("activities");
        campersBackup = readJSON("campers");
        campLocationBackup = readJSON("campLocation");
        counselorsBackup = readJSON("counselors");
        customersBackup = readJSON("customers");
        daySchedulesBackup = readJSON("daySchedules");
        directorBackup = readJSON("director");
        groupsBackup = readJSON("groups");
        weeksBackup = readJSON("weeks");

        // Empty all used JSONs
        emptyJSON("activities");
        emptyJSON("campers");
        emptyJSON("campLocation");
        emptyJSON("counselors");
        emptyJSON("customers");
        emptyJSON("daySchedules");
        emptyJSON("director");
        emptyJSON("groups");
        emptyJSON("weeks");

        // Contact motherContact = new Contact("Sue", "Brogden", "sbrog@ornl.gov",
        // "987-312-2333", "Mother",
        // "853 Knottingham Rd");
        bobContact = new Contact("Bob", "Johnson", "bob@hotmail.com", "102-526-8754", "Self",
                "542 Cunningham Rd");
        emergencyContact1 = new Contact("Ryan", "Harp", "harr@gmail.com", "657-234-2231", "Friend",
                "55 Hoback St.");
        emergencyContact2 = new Contact("Thomas", "Mandrus", "mandrusT@gmail.com", "456-586-2231", "Friend",
                "76 Hoback St.");
        pcpContact = new Contact("Ron", "Rhymer", "ron@pak.org", "811-234-2333", "PCP",
                "Pediatric Association of Knoxbville, 23 College Drive");
        // Add in testing data
        UserList.addUser(new Customer("bob@hotmail.com", "Bob", "Johnson", "password", CampLocation.getCampLocation(),
                new ArrayList<Camper>(), bobContact));
        UserList.addUser(
                new Director("james@hotmail.com", "James", "Jansen", "password2", CampLocation.getCampLocation()));
        UserList.addUser(new Counselor(new ArrayList<String>(), LocalDate.of(2002, 5, 23), "timmy22@gmail.com", "Timmy",
                "Zitzman", "password3", CampLocation.getCampLocation(), emergencyContact1, emergencyContact2,
                pcpContact));
        UserList.addUser(
                new Counselor(new ArrayList<String>(), LocalDate.of(2001, 5, 23), "mniels23@gmail.com", "Niels",
                        "Mandrus", "password5", CampLocation.getCampLocation(), emergencyContact1, emergencyContact2,
                        pcpContact));
        UserList.addUser(
                new Counselor(new ArrayList<String>(), LocalDate.of(1999, 5, 23), "nigelRyans22@gmail.com", "Nigel",
                        "Ryans", "password32", CampLocation.getCampLocation(), emergencyContact1, emergencyContact2,
                        pcpContact));
        UserList.addUser(
                new Counselor(new ArrayList<String>(), LocalDate.of(2000, 5, 23), "hillwenry23@gmail.com", "Will",
                        "Barton", "passwor2d3", CampLocation.getCampLocation(), emergencyContact1, emergencyContact2,
                        pcpContact));
        UserList.addUser(
                new Counselor(new ArrayList<String>(), LocalDate.of(1998, 5, 23), "connorBrunson23@gmail.com", "Connor",
                        "Brunson", "password32", CampLocation.getCampLocation(), emergencyContact1, emergencyContact2,
                        pcpContact));
        UserList.addUser(
                new Counselor(new ArrayList<String>(), LocalDate.of(2002, 7, 23), "phillip23@gmail.com", "Phillip",
                        "Edwards", "password23", CampLocation.getCampLocation(), emergencyContact1, emergencyContact2,
                        pcpContact));
        // DataWriter.createCamper(new Camper("Tommy", "Eggleston", new
        // ArrayList<String>(), LocalDate.of(2010, 12, 23),
        // emergencyContact1, motherContact, pcpContact, 0, "Not tested", "son"));
    }

    @AfterEach
    public void tearDown() {
        // Save all used JSONs to String as they were
        restoreJSON("activities");
        restoreJSON("campers");
        restoreJSON("campLocation");
        restoreJSON("counselors");
        restoreJSON("customers");
        restoreJSON("daySchedules");
        restoreJSON("director");
        restoreJSON("groups");
        restoreJSON("weeks");
        // emptyJSON("activities");
        // emptyJSON("campers");
        // emptyJSON("campLocation");
        // emptyJSON("counselors");
        // emptyJSON("customers");
        // emptyJSON("daySchedules");
        // emptyJSON("director");
        // emptyJSON("groups");
        // emptyJSON("weeks");
    }

    @Test
    public void customerCanLogin() {
        Customer bob = (Customer) f.login("bob@hotmail.com", "password");
        boolean gotBob = false;
        try {
            gotBob = bob.getFirstName().equals("Bob") && bob.getLastName().equals("Johnson");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidPasswordCustomerLogin() {
        Customer bob = (Customer) f.login("bob@hotmail.com", "passWord");
        assertNull(bob);
    }

    @Test
    public void invalidEmailCustomerLogin() {
        Customer bob = (Customer) f.login("bob23@hotmail.com", "password");
        assertNull(bob);
    }

    @Test
    public void couneslorCanLogin() {
        Counselor bob = (Counselor) f.login("mniels23@gmail.com", "password5");
        boolean gotBob = false;
        try {
            gotBob = bob.getFirstName().equals("Niels") && bob.getLastName().equals("Mandrus");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidPasswordCounselorLogin() {
        Counselor bob = (Counselor) f.login("mniels23@gmail.com", "passWord5");
        assertNull(bob);
    }

    @Test
    public void invalidEmailCounselorLogin() {
        Counselor bob = (Counselor) f.login("mNiels23@gmail.com", "password5");
        assertNull(bob);
    }

    @Test
    public void directorCanLogin() {
        Director bob = (Director) f.login("james@hotmail.com", "password2");
        boolean gotBob = false;
        try {
            gotBob = bob.getFirstName().equals("James") && bob.getLastName().equals("Jansen");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidPasswordDirectorLogin() {
        Director bob = (Director) f.login("james@hotmail.com", "password");
        assertNull(bob);
    }

    @Test
    public void invalidEmailDirectorLogin() {
        Director bob = (Director) f.login("jameshotmail.com", "password2");
        assertNull(bob);
    }

    @Test
    public void emptyUsernameLogin() {
        User bob = f.login("", "password2");
        assertNull(bob);
    }

    @Test
    public void emptyPasswordLogin() {
        User bob = f.login("james@hotmail.com", "");
        assertNull(bob);
    }

    @Test
    public void nullPasswordLogin() {
        User bob = f.login("james@hotmail.com", null);
        assertNull(bob);
    }

    @Test
    public void nullUsernameLogin() {
        User bob = f.login(null, "password2");
        assertNull(bob);
    }

    @Test
    public void nullPasswordAndUsernameLogin() {
        User bob = f.login(null, null);
        assertNull(bob);
    }

    @Test
    public void emptyPasswordAndUsernameLogin() {
        User bob = f.login("", "");
        assertNull(bob);
    }

    @Test
    public void customerCanSignUp() {
        Customer bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "egg@kcs.org", "myPassword23", bobContact);
        boolean gotBob = false;
        try {
            Customer tmp = (Customer) UserList.getUser(bob.getId());
            gotBob = tmp.getFirstName().equals("Tommy") && tmp.getLastName().equals("Eggleston");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidEmailCustomerSignUp() {
        int size = UserList.getUsers().size();
        Customer bob = new Customer("eggkcs.org", "Tommy", "Eggleston", "myPassword23", f.getCampLocation(),
                new ArrayList<Camper>(),
                bobContact);
        try {
            bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "eggkcs.org", "myPassword23", bobContact);
        } catch (Exception e) {
        }
        boolean passed = false;
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullFirstnameCustomerSignUp() {
        int size = UserList.getUsers().size();
        Customer bob = new Customer("eggkcs.org", "Tommy", "Eggleston", "myPassword23", f.getCampLocation(),
                new ArrayList<Camper>(),
                bobContact);
        boolean passed = false;
        try {
            bob = (Customer) f.signUpCustomer(null, "Eggleston", "eggkcs.org", "myPassword23", bobContact);
        } catch (Exception e) {
        }
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullEmailCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = new Customer("eggkcs.org", "Tommy", "Eggleston", "myPassword23", f.getCampLocation(),
                new ArrayList<Camper>(),
                bobContact);
        try {
            bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", null, "myPassword23", bobContact);
        } catch (Exception e) {
        }
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullPasswordCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = new Customer("eggkcs.org", "Tommy", "Eggleston", "myPassword23", f.getCampLocation(),
                new ArrayList<Camper>(),
                bobContact);
        try {
            bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "eggkcs.org", null, bobContact);
        } catch (Exception e) {
        }
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullContactCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = new Customer("eggkcs.org", "Tommy", "Eggleston", "myPassword23", f.getCampLocation(),
                new ArrayList<Camper>(),
                bobContact);
        try {
            bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "eggkcs.org", "myPassword23", null);
        } catch (Exception e) {
        }
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void duplicateEmailCustomerSignUp() {
        Customer bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "bob@hotmail.com", "myPassword23", bobContact);
        assertNull(bob);
    }

    @Test
    public void counselorCanSignUp() {
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", "josh1223@webbschool.org", "myPassword223",
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        boolean gotBob = false;
        try {
            Counselor tmp = (Counselor) UserList.getUser(bob.getId());
            gotBob = tmp.getFirstName().equals("Josh") && tmp.getLastName().equals("Chapman");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidEmailCounselorSignUp() {
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", "josh1223webbschoolorg", "myPassword223",
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        assertNull(bob);
    }

    @Test
    public void duplicateEmailCounselorSignUp() {
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", "bob@hotmail.com", "myPassword223",
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        assertNull(bob);
    }
}
