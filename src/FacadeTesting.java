//Author of FacadeTesting: Dylan Johnson


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.HashMap;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import javax.xml.crypto.Data;

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

    private static void replaceJSON(String name) {
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

    private static String readJSON(String name) {
        try {
            reader = new Scanner(new File("facadeTestingData/" + name + ".json"));
            String json = "";
            while (reader.hasNextLine()) {
                json += reader.nextLine();
            }
            return json;
        } catch (FileNotFoundException e) {
            return null;
        }

    }

    public static void main(String[] args) {
        FacadeTesting testing = new FacadeTesting();
        testing.setup();
    }

    @BeforeEach
    public void setup() {
        activitiesBackup = readJSON("activities");
        campersBackup = readJSON("campers");
        campLocationBackup = readJSON("campLocation");
        counselorsBackup = readJSON("counselors");
        customersBackup = readJSON("customers");
        daySchedulesBackup = readJSON("daySchedules");
        directorBackup = readJSON("director");
        groupsBackup = readJSON("groups");
        weeksBackup = readJSON("weeks");
        replaceJSON("activities");
        replaceJSON("campers");
        replaceJSON("campLocation");
        replaceJSON("counselors");
        replaceJSON("customers");
        replaceJSON("daySchedules");
        replaceJSON("director");
        replaceJSON("groups");
        replaceJSON("weeks");
        DataReader.reload();
        Contact motherContact = new Contact("Sue", "Brogden", "sbrog@ornl.gov",
                "987-312-2333", "Mother",
                "853 Knottingham Rd");
        bobContact = new Contact("Bob", "Johnson", "bob@hotmail.com", "102-526-8754",
                "Self",
                "542 Cunningham Rd");
        emergencyContact1 = new Contact("Ryan", "Harp", "harr@gmail.com",
                "657-234-2231", "Friend",
                "55 Hoback St.");
        emergencyContact2 = new Contact("Thomas", "Mandrus", "mandrusT@gmail.com",
                "456-586-2231", "Friend",
                "76 Hoback St.");
        pcpContact = new Contact("Ron", "Rhymer", "ron@pak.org", "811-234-2333",
                "PCP",
                "Pediatric Association of Knoxbville, 23 College Drive");
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void customerCanLogin() {
        Customer bob = (Customer) f.login("bob@hotmail.com", "passworten1234");
        boolean gotBob = false;
        try {
            gotBob = bob.getFirstName().equals("Bob") && bob.getLastName().equals("Damien");
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
        Customer bob = (Customer) f.login("bob23@hotmail.com", "passworten1234");
        assertNull(bob);
    }

    @Test
    public void couneslorCanLogin() {
        Counselor bob = (Counselor) f.login("jamesh1997@gmail.com", "secure");
        boolean gotBob = false;
        try {
            gotBob = bob.getFirstName().equals("James") && bob.getLastName().equals("Henderson");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidPasswordCounselorLogin() {
        Counselor bob = (Counselor) f.login("jamesh1997@gmail.com", "passWord5");
        assertNull(bob);
    }

    @Test
    public void invalidEmailCounselorLogin() {
        Counselor bob = (Counselor) f.login("mNiels23@gmail.com", "secure");
        assertNull(bob);
    }

    @Test
    public void directorCanLogin() {
        Director bob = (Director) f.login("sergy@hotmail.com", "dinasoursAreKool");
        boolean gotBob = false;
        try {
            gotBob = bob.getFirstName().equals("Sergy") && bob.getLastName().equals("Ivanovich");
        } catch (Exception e) {

        }
        assertTrue(gotBob);
    }

    @Test
    public void invalidPasswordDirectorLogin() {
        Director bob = (Director) f.login("sergy@hotmail.com", "dinaasd2soursAreKool");
        assertNull(bob);
    }

    @Test
    public void invalidEmailDirectorLogin() {
        Director bob = (Director) f.login("ser23gy@hotmail.com", "dinasoursAreKool");
        assertNull(bob);
    }

    @Test
    public void emptyUsernameLogin() {
        User bob = f.login("", "passworten1234");
        assertNull(bob);
    }

    @Test
    public void emptyPasswordLogin() {
        User bob = f.login("bob@hotmail.com", "");
        assertNull(bob);
    }

    @Test
    public void nullPasswordLogin() {
        User bob = f.login("bob@hotmail.com", null);
        assertNull(bob);
    }

    @Test
    public void nullUsernameLogin() {
        User bob = f.login(null, "passworten1234");
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
        Customer bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "eggkcs.org", "myPassword23", bobContact);
        boolean passed = false;
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullFirstnameCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = (Customer) f.signUpCustomer(null, "Eggleston", "eggkcs.org", "myPassword23", bobContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullEmailCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", null, "myPassword23", bobContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullPasswordCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "eggkcs.org", null, bobContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullContactCustomerSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Customer bob = (Customer) f.signUpCustomer("Tommy", "Eggleston", "eggkcs.org", "myPassword23", null);
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

    @Test
    public void nullFirstnameCounselorSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Counselor bob = (Counselor) f.signUpCounselor(null, "Chapman", "josh1223@webbschool.org", "myPassword223",
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullEmailCounselorSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", null, "myPassword223",
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullPasswordCounselorSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", "josh1223@webbschool.org", null,
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullContactsCounselorSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", "josh1223@webbschool.org", "myPassword223",
                new ArrayList<String>(), LocalDate.of(2002, 7, 21), null, null, null);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void nullAllergiesCounselorSignUp() {
        int size = UserList.getUsers().size();
        boolean passed = false;
        Counselor bob = (Counselor) f.signUpCounselor("Josh", "Chapman", "josh1223@webbschool.org", "myPassword223",
                null, LocalDate.of(2002, 7, 21), emergencyContact1, emergencyContact2, pcpContact);
        if (bob == null && UserList.getUsers().size() == size) {
            passed = true;
        }
        assertTrue(passed);
    }

    @Test
    public void canRepresentWeeks() {
        String[] weeks = f.representWeeks(WeekList.getWeeks());
        boolean worked = true;
        for (int i = 0; i < weeks.length; i++) {
            if (weeks[i] == null || !(weeks[i].toString().equals(WeekList.getWeeks().get(i).toString()))) {
                worked = false;
            }
        }
        assertTrue(worked);
    }

    @Test
    public void nullWeekListRepresentWeeks() {
        String[] weeks = new String[1];
        weeks[0] = WeekList.getWeeks().get(0).toString();
        try {
            weeks = f.representWeeks(null);
        } catch (Exception e) {
        }
        assertNull(weeks);
    }

    public static String readTestTxt(String name) {
        try {
            reader = new Scanner(new File("facadeTestingData/" + name + ".txt"));
            String json = "";
            while (reader.hasNextLine()) {
                json += reader.nextLine();
            }
            return json;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String readTxt(String name) {
        try {
            reader = new Scanner(new File(name + ".txt"));
            String json = "";
            while (reader.hasNextLine()) {
                json += reader.nextLine();
            }
            return json;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void canExportSchedule() {
        Group group = DataReader.getGroup(UUID.fromString("134f1b69-8a52-4041-8fef-e53f27a18d48"));
        Week week = DataReader.getWeek(UUID.fromString("e7bcdb50-e04e-4140-a192-893c605b0017"));
        f.exportSchedule(group, "exportedSchedule", week);
        String produced = readTxt("exportedSchedule");
        assertTrue(readTestTxt("exportedSchedule").equals(produced));
    }

    @Test
    public void doesntExportScheduleWhenNullGroup() {
        Group group = null;
        Week week = DataReader.getWeek(UUID.fromString("e7bcdb50-e04e-4140-a192-893c605b0017"));
        assertFalse(f.exportSchedule(group, "exportedSchedule", week));
    }

    @Test
    public void doesntExportScheduleWhenNullWeek() {
        Group group = DataReader.getGroup(UUID.fromString("134f1b69-8a52-4041-8fef-e53f27a18d48"));
        Week week = null;
        assertFalse(f.exportSchedule(group, "exportedSchedule", week));
    }

    @Test
    public void doesntExportScheduleWhenNullFilename() {
        Group group = DataReader.getGroup(UUID.fromString("134f1b69-8a52-4041-8fef-e53f27a18d48"));
        Week week = DataReader.getWeek(UUID.fromString("e7bcdb50-e04e-4140-a192-893c605b0017"));
        assertFalse(f.exportSchedule(group, null, week));
    }

    @Test
    public void canExportRoster() {
        Group group = DataReader.getGroup(UUID.fromString("7fb2e499-80bb-48a5-ad6d-c151de323e60"));
        Week week = DataReader.getWeek(UUID.fromString("a9440a27-89df-4d24-b1e8-60ec6cc305d6"));
        f.exportRoster(group, "roster", week);
        String produced = readTxt("roster");
        assertTrue(readTestTxt("roster").equals(produced));
    }

    @Test
    public void doesntExportRosterWhenNullWeek() {
        Group group = DataReader.getGroup(UUID.fromString("7fb2e499-80bb-48a5-ad6d-c151de323e60"));
        Week week = null;
        assertFalse(f.exportRoster(group, "roster", week));
    }

    @Test
    public void doesntExportRosterWhenNullGroup() {
        Group group = null;
        Week week = DataReader.getWeek(UUID.fromString("e7bcdb50-e04e-4140-a192-893c605b0017"));
        assertFalse(f.exportRoster(group, "roster", week));
    }

    @Test
    public void doesntExportRosterWhenNullFilename() {
        Group group = DataReader.getGroup(UUID.fromString("7fb2e499-80bb-48a5-ad6d-c151de323e60"));
        Week week = DataReader.getWeek(UUID.fromString("a9440a27-89df-4d24-b1e8-60ec6cc305d6"));
        assertFalse(f.exportRoster(group, null, week));
    }

    @Test
    public void canExportVitalInfo() {
        Group group = DataReader.getGroup(UUID.fromString("7fb2e499-80bb-48a5-ad6d-c151de323e60"));
        Week week = DataReader.getWeek(UUID.fromString("a9440a27-89df-4d24-b1e8-60ec6cc305d6"));
        f.exportVitalInfo(group, "vitalInfo", week);
        String produced = readTxt("vitalInfo");
        assertTrue(readTestTxt("vitalInfo").equals(produced));
    }

    @Test
    public void doesntExportVitalInfoWhenNullWeek() {
        Group group = DataReader.getGroup(UUID.fromString("7fb2e499-80bb-48a5-ad6d-c151de323e60"));
        Week week = null;
        assertFalse(f.exportVitalInfo(group, "vitalInfo", week));
    }

    @Test
    public void doesntExportVitalInfoWhenNullGroup() {
        Group group = null;
        Week week = DataReader.getWeek(UUID.fromString("e7bcdb50-e04e-4140-a192-893c605b0017"));
        assertFalse(f.exportVitalInfo(group, "vitalInfo", week));
    }

    @Test
    public void doesntExportVitalInfoWhenNullFilename() {
        Group group = DataReader.getGroup(UUID.fromString("7fb2e499-80bb-48a5-ad6d-c151de323e60"));
        Week week = DataReader.getWeek(UUID.fromString("a9440a27-89df-4d24-b1e8-60ec6cc305d6"));
        assertFalse(f.exportVitalInfo(group, null, week));
    }

    @Test
    public void canRegisterCamper() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        UUID id = UUID.fromString("7e7b8f07-e89f-4e26-a0fb-3ba36f8ba1b0");
        boolean worked = false;
        f.registerCamper(id, week);
        for (Group i : week.getGroups()) {
            for (Camper j : i.getCampers()) {
                if (j.getId().equals(id)) {
                    worked = true;
                }
            }
        }
        assertTrue(worked);
    }

    @Test
    public void doesntRegisterCamperWhenWeekNull() {
        Week week = null;
        UUID id = UUID.fromString("7e7b8f07-e89f-4e26-a0fb-3ba36f8ba1b0");
        assertFalse(f.registerCamper(id, week));
    }

    @Test
    public void doesntRegisterCamperWhenIDNull() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        UUID id = null;
        assertFalse(f.registerCamper(id, week));
    }

    @Test
    public void doesntRegisterCamperWhenIDNonExistant() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        UUID id = UUID.fromString("77777777-e89f-4e26-a0fb-3ba36f8ba1b0");
        assertFalse(f.registerCamper(id, week));
    }

    @Test
    public void canAddCamperToUser() {
        Customer customer = DataReader.getCustomer(UUID.fromString("1106b091-4e98-4c4f-8d97-271f552af724"));
        f.setUser(customer);
        f.addCamperToUser("Fredrick", "Mayfield", new ArrayList<String>(), LocalDate.of(2010, 5, 23), "Son",
                emergencyContact1, emergencyContact2, pcpContact);
        boolean worked = false;
        if (customer.getCampers().size() == 1) {
            worked = true;
        }
        if (DataReader.getCustomer(UUID.fromString("1106b091-4e98-4c4f-8d97-271f552af724")).getCampers()
                .get(0) == null) {
            worked = false;
        }
        assertTrue(worked);
    }

    @Test
    public void doesntAddCamperToNullUser() {
        f.setUser(null);
        assertFalse(f.addCamperToUser("Fredrick", "Mayfield", new ArrayList<String>(), LocalDate.of(2010, 5, 23), "Son",
                emergencyContact1, emergencyContact2, pcpContact));
    }

    @Test
    public void doesntAddCamperWithNullName() {
        Customer customer = DataReader.getCustomer(UUID.fromString("1106b091-4e98-4c4f-8d97-271f552af724"));
        f.setUser(customer);
        assertFalse(f.addCamperToUser(null, "Mayfield", new ArrayList<String>(), LocalDate.of(2010, 5, 23), "Son",
                emergencyContact1, emergencyContact2, pcpContact));
    }

    @Test
    public void doesntAddCamperWithNullAllergies() {
        Customer customer = DataReader.getCustomer(UUID.fromString("1106b091-4e98-4c4f-8d97-271f552af724"));
        f.setUser(customer);
        assertFalse(f.addCamperToUser("Fredrick", "Mayfield", null, LocalDate.of(2010, 5, 23), "Son",
                emergencyContact1, emergencyContact2, pcpContact));
    }

    @Test
    public void doesntAddCamperWithNullBirthday() {
        Customer customer = DataReader.getCustomer(UUID.fromString("1106b091-4e98-4c4f-8d97-271f552af724"));
        f.setUser(customer);
        assertFalse(f.addCamperToUser("Fredrick", "Mayfield", new ArrayList<String>(), null, "Son",
                emergencyContact1, emergencyContact2, pcpContact));
    }

    @Test
    public void doesntAddCamperWithNullContact() {
        Customer customer = DataReader.getCustomer(UUID.fromString("1106b091-4e98-4c4f-8d97-271f552af724"));
        f.setUser(customer);
        assertFalse(f.addCamperToUser("Fredrick", "Mayfield", new ArrayList<String>(), null, "Son",
                emergencyContact1, null, pcpContact));
    }

    @Test
    public void canRepresentWeekDays() {
        String[] correct = new String[] { "Sun, Jun 18, 2023", "Mon, Jun 19, 2023", "Tue, Jun 20, 2023",
                "Wed, Jun 21, 2023", "Thu, Jun 22, 2023", "Fri, Jun 23, 2023", "Sat, Jun 24, 2023" };
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        String[] got = f.weekDays(week);
        boolean worked = true;
        if (got.length != correct.length) {
            worked = false;
        }
        for (int i = 0; i < correct.length; i++) {
            if (!got[i].equals(correct[i])) {
                worked = false;
            }
        }
        assertTrue(worked);
    }

    @Test
    public void weekDaysReturnsNullWhenNullWeek() {
        assertNull(f.weekDays(null));
    }

    @Test
    public void canReplaceDaySchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("d78db184-dcfd-43af-9d20-e0c0f442629a")));
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        activities.add(DataReader.getActivity(UUID.fromString("49fa2e16-dcbb-4094-881e-be536104122b")));
        activities.add(DataReader.getActivity(UUID.fromString("fb610fc1-b92a-409c-9d1a-0712d7b96733")));
        activities.add(DataReader.getActivity(UUID.fromString("6f145e7d-e657-4112-ba7c-756b2264da3b")));
        activities.add(DataReader.getActivity(UUID.fromString("e3c449cc-8f8d-46c5-9cb2-7376b9f92769")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        f.replaceDaySchedule(schedule, group, 1);
        boolean worked = true;
        for (int i = 0; i < activities.size(); i++) {
            if (!activities.get(i).getId().equals(group.getSchedule().get(1).getActivities().get(i).getId())) {
                worked = false;
            }
        }
        assertTrue(worked);
    }

    @Test
    public void doesntReplaceDayScheduleWhenNullSchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        assertFalse(f.replaceDaySchedule(null, group, 1));
    }

    @Test
    public void doesntReplaceDayScheduleWhenNullGroup() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("d78db184-dcfd-43af-9d20-e0c0f442629a")));
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        activities.add(DataReader.getActivity(UUID.fromString("49fa2e16-dcbb-4094-881e-be536104122b")));
        activities.add(DataReader.getActivity(UUID.fromString("fb610fc1-b92a-409c-9d1a-0712d7b96733")));
        activities.add(DataReader.getActivity(UUID.fromString("6f145e7d-e657-4112-ba7c-756b2264da3b")));
        activities.add(DataReader.getActivity(UUID.fromString("e3c449cc-8f8d-46c5-9cb2-7376b9f92769")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        assertFalse(f.replaceDaySchedule(schedule, null, 1));
    }

    @Test
    public void negativeDayReplaceDaySchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("d78db184-dcfd-43af-9d20-e0c0f442629a")));
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        activities.add(DataReader.getActivity(UUID.fromString("49fa2e16-dcbb-4094-881e-be536104122b")));
        activities.add(DataReader.getActivity(UUID.fromString("fb610fc1-b92a-409c-9d1a-0712d7b96733")));
        activities.add(DataReader.getActivity(UUID.fromString("6f145e7d-e657-4112-ba7c-756b2264da3b")));
        activities.add(DataReader.getActivity(UUID.fromString("e3c449cc-8f8d-46c5-9cb2-7376b9f92769")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        assertFalse(f.replaceDaySchedule(schedule, group, -1));
    }

    @Test
    public void outofboundsDayReplaceDaySchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("d78db184-dcfd-43af-9d20-e0c0f442629a")));
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        activities.add(DataReader.getActivity(UUID.fromString("49fa2e16-dcbb-4094-881e-be536104122b")));
        activities.add(DataReader.getActivity(UUID.fromString("fb610fc1-b92a-409c-9d1a-0712d7b96733")));
        activities.add(DataReader.getActivity(UUID.fromString("6f145e7d-e657-4112-ba7c-756b2264da3b")));
        activities.add(DataReader.getActivity(UUID.fromString("e3c449cc-8f8d-46c5-9cb2-7376b9f92769")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        assertFalse(f.replaceDaySchedule(schedule, group, 7));
    }

    @Test
    public void canClearSchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        f.clearSchedules(week, 1);
        boolean worked = true;
        for (Group i : week.getGroups()) {
            if (i.getSchedule().get(1).getActivities().size() != 0) {
                worked = false;
            }
        }
        assertTrue(worked);
    }

    @Test
    public void nullWeekClearSchedule() {
        f.clearSchedules(null, 1);
    }

    @Test
    public void negativeDayWeekClearSchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        f.clearSchedules(week, -1);
        boolean unchanged = true;
        for (Group i : week.getGroups()) {
            if (i.getSchedule().get(1).getActivities().size() != 6) {
                unchanged = false;
            }
        }
        assertTrue(unchanged);
    }

    @Test
    public void outOfBoundsDayWeekClearSchedule() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        f.clearSchedules(week, 7);
        boolean unchanged = true;
        for (Group i : week.getGroups()) {
            if (i.getSchedule().get(1).getActivities().size() != 6) {
                unchanged = false;
            }
        }
        assertTrue(unchanged);
    }

    private static boolean equals(ArrayList<Activity> a, ArrayList<Activity> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).getId().toString().equals(b.get(i).getId().toString())) {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<Activity> compliment(ArrayList<Activity> a) {
        ArrayList<Activity> rtn = new ArrayList<Activity>();
        for (Activity i : ActivitiesList.getActivities()) {
            boolean notInA = true;
            for (Activity j : a) {
                if (i.getId().equals(j.getId())) {
                    notInA = false;
                }
            }
            if (notInA) {
                rtn.add(i);
            }
        }
        return rtn;
    }


    private static boolean in(Activity activity, ArrayList<Activity> a){
        for (Activity i : a){
            if (activity.getId().equals(i.getId())){
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Activity> union(ArrayList<Activity> a, ArrayList<Activity> b) {
        ArrayList<Activity> rtn = new ArrayList<Activity>();
        for (Activity i : ActivitiesList.getActivities()) {
            if (in(i, a) || in(i,b)){
                rtn.add(i);
            }
        }
        return rtn;
    }

    @Test
    public void canGetAvailableActivitiesWhenAllClear() {
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }

        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        ArrayList<Activity> availableActivities = compliment(activities);
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        boolean worked = equals(f.getAvailableActivities(group, schedule, 1, 0, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("49fa2e16-dcbb-4094-881e-be536104122b")));
        availableActivities = compliment(activities);
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 1, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("fb610fc1-b92a-409c-9d1a-0712d7b96733")));
        availableActivities = compliment(activities);
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 2, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("6f145e7d-e657-4112-ba7c-756b2264da3b")));
        availableActivities = compliment(activities);
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 3, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("e3c449cc-8f8d-46c5-9cb2-7376b9f92769")));
        availableActivities = compliment(activities);
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 4, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("d78db184-dcfd-43af-9d20-e0c0f442629a")));
        availableActivities = compliment(activities);
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 4, week), availableActivities);
    }

    @Test
    public void canGetAvailableActivitiesWhenNotAllClear(){
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }

        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("d3b5b646-fc00-4f95-830c-35657c46deaa")));
        activities.add(DataReader.getActivity(UUID.fromString("f1be7fb3-ec83-4d9d-9590-1854f01e406a")));
        Group tmp = DataReader.getGroup(UUID.fromString("d5f1a288-0422-49f0-ab5d-5827c8eb793c"));
        tmp.getSchedule().get(1).setActivities(activities);
        DataWriter.updateGroup(UUID.fromString("d5f1a288-0422-49f0-ab5d-5827c8eb793c"),tmp);

        activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        ArrayList<Activity> availableActivities = compliment(union(activities,tmp.getSchedule().get(1).getActivities()));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        boolean worked = equals(f.getAvailableActivities(group, schedule, 1, 0, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("49fa2e16-dcbb-4094-881e-be536104122b")));
        availableActivities = compliment(union(activities,tmp.getSchedule().get(1).getActivities()));
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 1, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("fb610fc1-b92a-409c-9d1a-0712d7b96733")));
        availableActivities = compliment(union(activities,tmp.getSchedule().get(1).getActivities()));
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 2, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("6f145e7d-e657-4112-ba7c-756b2264da3b")));
        availableActivities = compliment(union(activities,tmp.getSchedule().get(1).getActivities()));
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 3, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("e3c449cc-8f8d-46c5-9cb2-7376b9f92769")));
        availableActivities = compliment(union(activities,tmp.getSchedule().get(1).getActivities()));
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 4, week), availableActivities);

        activities.add(DataReader.getActivity(UUID.fromString("d78db184-dcfd-43af-9d20-e0c0f442629a")));
        availableActivities = compliment(union(activities,tmp.getSchedule().get(1).getActivities()));
        schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        worked = worked && equals(f.getAvailableActivities(group, schedule, 1, 4, week), availableActivities);
    }

    @Test
    public void getAvailableActivitiesReturnsEmptyArrayWhenNullGroup(){
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        assertTrue(f.getAvailableActivities(null, schedule, 1, 0, week).size()==0);
    }

    @Test
    public void getAvailableActivitiesReturnsEmptyArrayWhenNullWeekAndSchedule(){
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        assertTrue(f.getAvailableActivities(group, null, 1, 0, null).size()==0);
    }

    @Test
    public void getAvailableActivitiesOutOfBoundsDay(){
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        assertTrue(f.getAvailableActivities(group, schedule, 7, 0, week).size()==0);
    }

    @Test
    public void getAvailableActivitiesNegativeDay(){
        Week week = DataReader.getWeek(UUID.fromString("b69d87c8-964b-4606-b0b8-fb94f401eac7"));
        Group group = DataReader.getGroup(UUID.fromString("85cebb34-df07-499e-979b-d712d5941c58"));
        for (Group i : week.getGroups()) {
            i.getSchedule().get(1).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(i.getId(), i);
        }
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(DataReader.getActivity(UUID.fromString("7332323b-0592-4da7-806c-f3673e49597c")));
        DaySchedule schedule = new DaySchedule(activities, week, LocalDate.of(2023, 6, 19));
        assertTrue(f.getAvailableActivities(group, schedule, -1, 0, week).size()==0);
    }
}
