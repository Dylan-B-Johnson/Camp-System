import java.util.ArrayList;
import java.util.UUID;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * A method that enables the UI to manage the program
 */
public class Facade {

    private User user;

    /**
     * Creates an instance of facade
     */
    public Facade() {

    }

    /**
     * Gets the director of the camp
     * 
     * @return The director of the camp
     */
    public User getDirector() {
        return DataReader.getDirector();
    }

    /**
     * Gets the camp location
     * 
     * @return The camp location instance
     */
    public CampLocation getCampLocation() {
        return DataReader.getCampLocation();
    }

    /**
     * Logs in a user and sets them to the active user
     * 
     * @param email    The email of the user
     * @param password The password of the user
     * @return The user that logged in
     */
    public User login(String email, String password) {
        for (User user : UserList.getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                setUser(user);
                return user;
            }
        }
        return null;
    }

    /**
     * Creates a new customer and sets them to the active user
     * 
     * @param firstName The first name of the customer
     * @param lastName  The last name of the customer
     * @param email     The email of the customer
     * @param password  The password of the customer
     * @param self      The customer's own contact
     * @return The user that created their account
     */
    public User signUpCustomer(String firstName, String lastName, String email, String password, Contact self) {
        if (!UserList.emailAvailable(email))
            return null;
        User user = new Customer(email, firstName, lastName, password, getCampLocation(), new ArrayList<Camper>(),
                self);
        UserList.addUser(user);
        setUser(user);
        return user;
    }

    /**
     * Creates a new counselor and sets them to the active user
     * 
     * @param firstName                 The first name of the counselor
     * @param lastName                  The last name of the counselor
     * @param email                     The email of the counselor
     * @param password                  The password of the counselor
     * @param allergies                 The allergies of the counselor
     * @param birthday                  The birthday of the counselor
     * @param primaryEmergencyContact   The first emergency contact for the
     *                                  counselor
     * @param secondaryEmergencyContact The second emergency contact for the
     *                                  counselor
     * @param primaryCarePhysician      The primary care physician of the counselor
     * @return The counselor that created their account
     */
    public User signUpCounselor(String firstName, String lastName, String email, String password,
            ArrayList<String> allergies, LocalDate birthday, Contact primaryEmergencyContact,
            Contact secondaryEmergencyContact, Contact primaryCarePhysician) {
        if (!UserList.emailAvailable(email))
            return null;
        User user = new Counselor(allergies, birthday, email, firstName, lastName, password, getCampLocation(),
                primaryEmergencyContact,
                secondaryEmergencyContact,
                primaryCarePhysician);
        UserList.addUser(user);
        setUser(user);
        return user;
    }

    /**
     * Gets the current week instance
     * 
     * @return The current week
     */
    public Week getCurrentWeek() {
        return WeekList.getCurrentWeek();
    }

    /**
     * The weeks that are capable of accepting more campers
     * 
     * @return The available weeks
     */
    public ArrayList<Week> getWeeksAvailableForRegistration() {
        return WeekList.getWeeksAvailableForRegistration();
    }

    /**
     * A string array of the weeks that are capable of accepting more campers
     * 
     * @return The available weeks
     */
    public String[] getStringWeeksAvailableForRegistration() {
        ArrayList<Week> weeks = getWeeksAvailableForRegistration();
        String[] rtn = new String[weeks.size()];
        for (int i = 0; i < weeks.size(); i++) {
            rtn[i] = (weeks.get(i).toString());
        }
        return rtn;
    }

    /**
     * Takes an array list of weeks and returns a string representation of them
     * 
     * @param weeks The weeks that will be listed
     * @return The string representation of the list of weeks
     */
    public String[] representWeeks(ArrayList<Week> weeks) {
        String[] rtn = new String[weeks.size()];
        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = weeks.get(i).toString();
        }
        return rtn;
    }

    /**
     * Returns a string representation of the a user's campers that can register for
     * a week
     * 
     * @param weekOfRegistration The week that is being checked for
     * @return The string representation of the campers
     */
    public String[] getCamperStrings(Week weekOfRegistration) {
        ArrayList<Camper> campers = getCampersElligableForRegistration(weekOfRegistration);
        String[] rtn = new String[campers.size()];
        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = campers.get(i).getFirstName() + " " + campers.get(i).getLastName();
        }
        return rtn;
    }

    /**
     * Gets all possible activities
     * 
     * @return Gets possible activities
     */
    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values());
    }

    /**
     * Gets the first chronolgoical group a counselor is assigned to
     * 
     * @param user The counselor whose groups are being looked through
     * @return The first group
     */
    public Group getFirstGroup(User user) {
        for (Week week : WeekList.getFutureOrCurrentWeeks()) {
            for (Group group : week.getGroups()) {
                if (group.getCounselor().getId().equals(user.getId())) {
                    return group;
                }
            }
        }
        return null;
    }

    /**
     * Gets the week that a group is located in
     * 
     * @param group The group whose week is being checked for
     * @return The week that the group is in
     */
    public Week getWeek(Group group) {
        for (Week week : WeekList.getWeeks()) {
            for (Group i : week.getGroups()) {
                if (i.getCounselor().getId().equals(group.getId())) {
                    return week;
                }
            }
        }
        return null;
    }

    /**
     * Exports a formatted schedule to the specified file
     * 
     * @param daySchedule The day that is being printed
     */
    public void exportSchedule(DaySchedule daySchedule) {
        ArrayList<String> output = new ArrayList<>();

        for (Activity activity : daySchedule.getCurrentAcitivities()) {
            output.add(activity.toString());
        }

        try {
            FileWriter file = new FileWriter(String.format("schedule_of_%s.txt", daySchedule.getDay().toString()));
            for (String activityString : output) {
                file.append(activityString);
            }
            file.close();
        } catch (Exception e) {
        }
    }

    /**
     * Gets a list of campers whose name matched the input
     * 
     * @param firstName The name that is being searched for
     * @return The list of campers that match
     */
    public ArrayList<Camper> getCamper(String firstName) {
        ArrayList<Camper> campers = new ArrayList<Camper>();
        for (Camper camper : UserList.getCampers()) {
            if (camper.getFirstName().equals(firstName)) {
                campers.add(camper);
            }
        }
        return campers;
    }

    /**
     * Gets a list of users whose name matched the input
     * 
     * @param firstName The name that is being searched for
     * @return The list of users that match
     */
    public ArrayList<User> getUser(String firstName) {
        ArrayList<User> users = new ArrayList<User>();
        for (User user : UserList.getUsers()) {
            if (user.getFirstName().equals(firstName)) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * Gets a list of all users of type customer
     * 
     * @return The list of customers
     */
    public ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    /**
     * Gets a list of all campers
     * 
     * @return The list of campers
     */
    public ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    /**
     * Gets a list of campers that can register for a week
     * 
     * @param weekOfRegistration The week that is being checked
     * @return The campers that are allowed to register
     */
    public ArrayList<Camper> getCampersElligableForRegistration(Week weekOfRegistration) {
        ArrayList<Camper> rtn = new ArrayList<Camper>();
        for (Camper i : ((Customer) user).getCampers()) {
            if (i.getAge(weekOfRegistration) >= getCampLocation().getMinCamperAge()
                    && i.getAge(weekOfRegistration) <= getCampLocation().getMaxCamperAge()) {
                rtn.add(i);
            }
        }
        return rtn;
    }

    /**
     * Get the user that is associated with the facade
     * 
     * @return The user that is part of the facade
     */
    public User getUser() {
        return user;
    }

    /**
     * Checks to see if an activity exists
     * 
     * @param name The name of the activity
     * @return True if it exists False if it does not
     */
    public boolean activityExists(String name) {
        for (Activity i : ActivitiesList.getActivities()) {
            if (i.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the user associated with the facade
     * 
     * @param user The user that the facade is now assigned to
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets a list of all users of type counselor
     * 
     * @return The list of counselors
     */
    public ArrayList<User> getCounselors() {
        return new ArrayList<User>(DataReader.getCounselors().values());
    }

    /**
     * Gets an array list of counselors whose names match the input
     * 
     * @param firstName The name that is being searched for
     * @return The list of counselors
     */
    public ArrayList<User> getCounselor(String firstName) {
        ArrayList<User> users = new ArrayList<User>();
        for (User user : UserList.getUsers()) {
            if (user.getFirstName().equals(firstName)) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * Makes an instance of type contact with the supplied parameters
     * 
     * @param firstName    The first name of the contact
     * @param lastName     The last name of the contact
     * @param email        The email of the contact
     * @param phoneNum     The phone number of the contact
     * @param relationship The relationship of the contact to the user
     * @param address      The contact's address
     * @return The contact that was created
     */
    public Contact makeContact(String firstName, String lastName, String email,
            String phoneNum, String relationship, String address) {
        return new Contact(firstName, lastName, email,
                phoneNum, relationship, address);
    }

    /**
     * Registers the camper for a week
     * 
     * @param id   The id of the camper
     * @param week The week they are being registered for
     * @return True if the operation was successful False if it was not
     */
    public boolean registerCamper(UUID id, Week week) {
        Camper foundCamper = DataReader.getCamper(id);
        if (foundCamper == null) {
            return false;
        }
        if (week.canRegisterCamper(foundCamper)) {
            week.registerCamper(foundCamper);
            DataWriter.updateWeek(week.getId(), week);
            return true;
        }
        return false;
    }

    /**
     * Adds the specified camper to a user's camper arraylist
     * 
     * @param firstName                 The first name of the camper
     * @param lastName                  The last name of the camper
     * @param allergies                 The allergies of the campers
     * @param birthday                  The camper's birthday
     * @param relationToCustomer        The camper's relationship to the customer
     * @param primaryEmergencyContact   The camper's first emergency contact
     * @param secondaryEmergencyContact The camper's second emergency contact
     * @param primaryCarePhysician      The camper's primary care physician
     * @return True if it was successful False if it was not
     */
    public boolean addCamperToUser(String firstName, String lastName, ArrayList<String> allergies,
            LocalDate birthday, String relationToCustomer, Contact primaryEmergencyContact,
            Contact secondaryEmergencyContact, Contact primaryCarePhysician) {
        Camper camper = new Camper(firstName, lastName, allergies, birthday, primaryEmergencyContact,
                secondaryEmergencyContact, primaryCarePhysician, 0, "Not tested", relationToCustomer);
        DataWriter.createCamper(camper);
        ((Customer) getUser()).addCamper(camper);
        return true;
    }

    /**
     * Adds an activity to the list of activities
     * 
     * @param name        The name of the activity
     * @param location    The location of the activity
     * @param description The description of the activity
     * @return True if it was successful false if it was not
     */
    public boolean addActivity(String name, String location, String description) {
        return ActivitiesList.addActivity(new Activity(name, location, description));
    }

    public DaySchedule getDaySchedule(int dayOfWeek, User counselor, Week week) {
        return WeekList.getDaySchedule(week.getStartOfWeek().plusDays(dayOfWeek), counselor);
    }

    /**
     * Creates a string representation for each day of a week
     * 
     * @param week The week to get the day strings for
     * @return An array of the string representations for each constituent day of
     *         the week
     */
    public String[] weekDays(Week week) {
        String[] rtn = new String[Week.WEEK_LENGTH];
        LocalDate date = week.getStartOfWeek();
        for (int i = 0; i < rtn.length; i++) {
            LocalDate plusDays = date.plusDays(i);
            rtn[i] = (plusDays.format(DateTimeFormatter.ofPattern("E, LLL d, uuuu")));
        }
        return rtn;
    }

    /**
     * Gets the group from a week that the user (a counselor) belongs to
     * 
     * @param selectedWeek The week to search through
     * @return The group the counselor is in
     */
    public Group getAssociatedGroup(Week selectedWeek) {
        Group group = null;
        for (Group i : selectedWeek.getGroups()) {
            if (i.getCounselor().getFirstName().equals(getUser().getFirstName()) &&
                    i.getCounselor().getLastName().equals(getUser().getLastName())) {
                group = i;
            }
        }
        return group;
    }

    /**
     * Gets all the future or current weeks
     * 
     * @return A list of all future or current weeks
     */
    public ArrayList<Week> getFutureOrCurrentWeeks() {
        return WeekList.getFutureOrCurrentWeeks();
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
    public String ordinal(int i) {
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

    /**
     * Gets the total price for registration for the current user (only run when
     * the current user is a Customer)
     * 
     * @return The price of registration for the current user (a customer) in USD
     */
    public double getCostOfRegistration() {
        return getCampLocation().getPricePerCamper() - getDiscoutOnRegistration();
    }

    /**
     * Gets the discount in USD on registration for the current user (only run when
     * the current user is a Customer)
     * 
     * @return The discount on registration for the current user (a customer) in USD
     */
    public double getDiscoutOnRegistration() {
        return ((Customer) getUser()).getDiscount() / 100.0 * getCampLocation().getPricePerCamper();
    }

    /**
     * Creates a new camp week session
     * 
     * @param start The start of the new camp session week to add
     * @param theme The theme for the new camp session week
     * @return Whether or not the creation of the new camp week session was
     *         sucessful
     */
    public boolean addRandomizedWeek(LocalDate start, String theme) {
        Week week = new Week(0, 0, start, getCampLocation(), theme);
        week.setUpGroups();
        return WeekList.addWeek(week);
    }

    /**
     * Checks if a date is in the past or not
     * 
     * @param date The date to determine if it's in the past or not
     * @return True if the passed in date is the current date or a future date,
     *         false otherwise
     */
    public boolean isFutureOrCurrentDate(LocalDate date) {
        return date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now());
    }

    /**
     * Gets the camp week session that contains the provided date
     * 
     * @param dateContainedInWeek The date to find the associated week for
     * @return Null if there is no future or current week containing the provided
     *         date, the week containing the provided date otherwise
     */
    public Week getAssociatedWeek(LocalDate dateContainedInWeek) {
        LocalDate prevSunday = dateContainedInWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        for (Week week : WeekList.getWeeks()) {
            if (week.getStartOfWeek().equals(prevSunday)) {
                return week;
            }
        }
        return null;
    }

    /**
     * Replaces specified group's schedule with the new schedule for the specific
     * day
     * 
     * @param newSchedule The schedule to replace the old schedule with
     * @param group       The group whose schedule you are changing
     * @param day         The index of the day to replace (0-6)
     * @return Whether or not it suceeded
     */
    public boolean replaceDaySchedule(DaySchedule newSchedule, Group group, int day) {
        // replaces the specified group's day-th DaySchedule with newSchedule and
        // ensures it is saved properly
        if (!DataWriter.createDaySchedule(newSchedule))
            return false;
        ArrayList<DaySchedule> schedules = group.getSchedule();
        schedules.set(day, newSchedule);
        group.setSchedule(schedules);
        return DataWriter.updateGroup(group.getId(), group);
    }

    /**
     * Reinitializes all schedules for the specified day and week with an empty
     * DaySchedule arrayList
     * 
     * @param week Week to empty all schedules for
     * @param day  The index of the day to replace (0-6)
     */
    public void clearSchedules(Week week, int day) {
        for (Group group : week.getGroups()) {
            group.getSchedule().get(day).setActivities(new ArrayList<Activity>());
            DataWriter.updateGroup(group.getId(), group);
        }
    }

    /**
     * Exports a week schedule to a text file
     * 
     * @param group    The group whose schedule shall be exported
     * @param filename The name of the exported file
     * @param week     The week the group is in
     * @return Wether or not it was successful
     */
    public boolean exportSchedule(Group group, String filename, Week week) {
        filename += ".txt";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            int day = 1;
            fileWriter.append(week.toString() + "\n");
            for (DaySchedule daySchedule : group.getSchedule()) {
                fileWriter.append(String.format("Day %d\n", day++));
                for (Activity activity : daySchedule.getActivities()) {
                    fileWriter.append(String.format("%s\t|\t%s\t|\t%s\n", activity.getName(), activity.getLocation(),
                            activity.getDescription()));
                }
                fileWriter.append("\n");
            }
            fileWriter.close();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Exports the camper roster for a group
     * 
     * @param group    The group to export the roster for
     * @param filename The name of the exported file
     * @param week     The week the group is in
     * @return Wether or not it was successful
     */
    public boolean exportRoster(Group group, String filename, Week week) {
        filename += ".txt";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.append(week.toString() + "\n");
            for (Camper camper : group.getCampers()) {
                fileWriter.append("Name: " + camper.getFirstName() + " " + camper.getLastName() + " Age: "
                        + camper.getAge(week) + "\n");
            }
            fileWriter.close();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Exports the vital info for a group
     * 
     * @param group    The group to export the vital info for
     * @param filename The name of the exported file
     * @param week     The week the group is in
     * @return Wether or not it was successful
     */
    public boolean exportVitalInfo(Group group, String filename, Week week) {
        filename += ".txt";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.append(week.toString() + "\n");
            for (Camper camper : group.getCampers()) {
                fileWriter.append(camper.toString());
                fileWriter.append("\n*******************************************\n");
            }
            fileWriter.close();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * A method that returns a list of legal activities that could be added to the
     * current DaySchedule at the specified time, ensuring multiple of the same
     * activity will not be assigned to multiple groups at the same time or assigned
     * twice in the current DaySchedule. Ignores group.getSchedule().get(day), as
     * this will be overwritten with current.
     * 
     * @param group    The group whose schedule is being edited.
     * @param current  The DaySchedule object that will be assigned to the group and
     *                 day once it is filled with 6 valid activities. THIS IS NOT
     *                 THE SAME as group.getSchedule().get(day).
     * @param day      The day in question (0-5) from the start of the week
     * @param activity The timeslot to get the legal sctivities for (0-5)
     * @param week     The week that you're editing in
     * @return A list of legal activities for day number day, group group, activity
     *         number activity, and currently scheduled.
     */
    public ArrayList<Activity> getAvailableActivities(Group group, DaySchedule current, int day, int activity,
            Week week) {
        ArrayList<Activity> availableActivities = new ArrayList<Activity>();
        for (Activity potActivity : ActivitiesList.getActivities()) {
            boolean available = true;
            for (Group othGroup : week.getGroups()) {
                if (othGroup.getSchedule().get(day).getActivities().size() != 0 && othGroup.getSchedule().get(day)
                        .getActivities().get(activity).getId().equals(potActivity.getId())) {
                    available = false;
                }
            }
            for (Activity i : current.getActivities()) {
                if (potActivity.getId().equals(i.getId())) {
                    available = false;
                }
            }
            if (available) {
                availableActivities.add(potActivity);
            }
        }
        return availableActivities;
    }

    /**
     * Gets a list of current or future weeks that the user (a counselor) is
     * scheduled for
     * 
     * @return A list of current or future weeks that the user (a counselor) is
     *         scheduled for
     */
    public ArrayList<Week> getCurrentOrFutureScheduledWeeks() {
        ArrayList<Week> rtn = new ArrayList<Week>();
        for (Week week : WeekList.getFutureOrCurrentWeeks()) {
            for (Group group : week.getGroups()) {
                if (group.getCounselor().getId().equals(getUser().getId())) {
                    rtn.add(week);
                    break; // assumes one Counselor isn't assigned to multiple groups in the same week
                }
            }
        }
        return rtn;
    }

}