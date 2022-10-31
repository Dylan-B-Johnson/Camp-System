import java.util.ArrayList;
import java.util.UUID;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Facade {

    private User user;

    public Facade() {

    }

    public static void main(String[] args) {
        Facade f = new Facade();
        f.signUpCustomer("test", "test", "test@gmail.com", "test",
                new Contact("null", "null", "null", "null", "null", "null"));
    }

    public User getDirector() {
        return DataReader.getDirector();
    }

    public CampLocation getCampLocation() {
        return DataReader.getCampLocation();
    }

    public User login(String email, String password) {
        for (User user : UserList.getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                setUser(user);
                return user;
            }
        }
        return null;
    }

    public User signUpCustomer(String firstName, String lastName, String email, String password, Contact self) {
        if (!UserList.emailAvailable(email))
            return null;
        User user = new Customer(email, firstName, lastName, password, getCampLocation(), new ArrayList<Camper>(),
                self);
        UserList.addUser(user);
        setUser(user);
        return user;
    }

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

    public Week getCurrentWeek() {
        return WeekList.getCurrentWeek();
    }

    public ArrayList<Week> getWeeksAvailableForRegistration() {
        return WeekList.getWeeksAvailableForRegistration();
    }

    public String[] getStringWeeksAvailableForRegistration() {
        ArrayList<Week> weeks = getWeeksAvailableForRegistration();
        String[] rtn = new String[weeks.size()];
        for (int i = 0; i < weeks.size(); i++) {
            rtn[i] = (weeks.get(i).toString());
        }
        return rtn;
    }

    public String[] getCamperStrings(Week weekOfRegistration) {
        ArrayList<Camper> campers = getCampersElligableForRegistration(weekOfRegistration);
        String[] rtn = new String[campers.size()];
        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = campers.get(i).getFirstName() + " " + campers.get(i).getLastName();
        }
        return rtn;
    }

    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(DataReader.getActivities().values());
    }

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

    public Week getWeek(Group group){
        for(Week week : WeekList.getFutureOrCurrentWeeks()){
            if(week.getGroups().contains(group)){
                return week;
            }
        }
        return null;
    }

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
            // TODO: handle exception
        }
    }

    public ArrayList<Camper> getCamper(String firstName) {
        ArrayList<Camper> campers = new ArrayList<Camper>();
        for (Camper camper : UserList.getCampers()) {
            if (camper.getFirstName().equals(firstName)) {
                campers.add(camper);
            }
        }
        return campers;
    }

    public ArrayList<User> getUser(String firstName) {
        ArrayList<User> users = new ArrayList<User>();
        for (User user : UserList.getUsers()) {
            if (user.getFirstName().equals(firstName)) {
                users.add(user);
            }
        }
        return users;
    }

    public ArrayList<Customer> getCustomers() {
        return new ArrayList<Customer>(DataReader.getCustomers().values());
    }

    public ArrayList<Camper> getCampers() {
        return new ArrayList<Camper>(DataReader.getCampers().values());
    }

    public ArrayList<Camper> getCampersElligableForRegistration(Week weekOfRegistration) {
        ArrayList<Camper> rtn = new ArrayList<Camper>();
        for (Camper i : ((Customer) user).getCampers()) {
            if (i.getAge(weekOfRegistration) >= getCampLocation().getMaxCamperAge()
                    && i.getAge(weekOfRegistration) <= getCampLocation().getMinCamperAge()) {
                rtn.add(i);
            }
        }
        return rtn;
    }

    public User getUser() {
        return user;
    }

    public boolean activityExists(String name) {
        for (Activity i : ActivitiesList.getActivities()) {
            if (i.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getCounselors() {
        return new ArrayList<User>(DataReader.getCounselors().values());
    }

    public ArrayList<User> getCounselor(String firstName) {
        ArrayList<User> users = new ArrayList<User>();
        for (User user : UserList.getUsers()) {
            if (user.getFirstName().equals(firstName)) {
                users.add(user);
            }
        }
        return users;
    }

    public Contact makeContact(String firstName, String lastName, String email,
            String phoneNum, String relationship, String address) {
        return null;
    }

    public boolean registerCamper(UUID id, Week week) {
        Camper foundCamper = null;
        for (Camper camper : UserList.getCampers()) {
            if (camper.getId().equals(id)) {
                foundCamper = camper;
            }
        }
        if (week.canRegisterCamper(foundCamper)) {
            week.registerCamper(foundCamper);
            return true;
        }
        return false;
    }

    public boolean addCamperToUser(String firstName, String lastName, ArrayList<String> allergies,
            LocalDate birthday, String relationToCustomer, Contact primaryEmergencyContact,
            Contact secondaryEmergencyContact, Contact primaryCarePhysician) {
        Camper camper = new Camper(firstName, lastName, allergies, birthday, primaryEmergencyContact,
                secondaryEmergencyContact, primaryCarePhysician, 0, lastName, relationToCustomer);
        ((Customer) getUser()).addCamper(camper);
        return true;
    }

    public boolean addActivity(String name, String location, String description) {
        return ActivitiesList.addActivity(new Activity(name, location, description));
    }

    public DaySchedule getDaySchedule(int daysFromNow, User counselor) {
        return WeekList.getDaySchedule(LocalDate.now().plusDays(daysFromNow), counselor);
    }

    public String[] nextWeek() {
        String[] rtn = new String[7];
        LocalDate date = LocalDate.now();
        for (int i = 0; i < rtn.length; i++) {
            LocalDate plusDays = date.plusDays(i);
            rtn[i] = (plusDays.format(DateTimeFormatter.ofPattern("E, LLL d, uuuu")));
        }
        return rtn;
    }

    public String[] weekDays(Week week) {
        String[] rtn = new String[7];
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

    public boolean addRandomizedWeek(LocalDate start, String theme) {
        Week week = new Week(0, 0, start, getCampLocation(), theme);
        week.setGroups(week.setUpGroups());
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
        LocalDate prevSunday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        for (Week week : WeekList.getFutureOrCurrentWeeks()) {
            if (week.getStartOfWeek().equals(prevSunday)) {
                return week;
            }
            
        }
        return null;
    }

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

    public void clearSchedules(Week week, int day) {
        // reinitializes all schedules for the specified day and week with an empty
        // DaySchedule arrayList
        // ensures that everything is saved properly
        for (Group group : week.getGroups()) {
            ArrayList<DaySchedule> updatedSchedule = group.getSchedule();
            updatedSchedule.set(day, null);
            group.setSchedule(updatedSchedule); // new ArrayList<DaySchedule>());
            DataWriter.updateGroup(group.getId(), group);
        }
    }

    public boolean exportSchedule(Group group, String filename) {
        // filename param has no extension
        // saves the group's week schedule as a well-formatted text file with the
        // specified name
        // "showing a grid of what they will be doing at each day and time. For each
        // activity it also indicates where it is located."
        // we need to discuss where to save the file
        try {
            FileWriter fileWriter = new FileWriter(filename);
            int day = 1;
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
            System.out.println(exception);
        }
        return false;
    }

    public boolean exportVitalInfo(Group group, String filename) {
        // filename param has no extension
        // saves the group's vital info as a well-formatted text file with the specified
        // name
        // " list of next weeks vital information, this will generate a beautifully
        // formatted report of all his campers allergies, emergency contacts, and
        // medical information. Make sure this is a good sized list."
        // we need to discuss where to save the file
        return false;
    }

    public void saveAndQuit() {
        // Save all objects to their appropriate JSONs
        System.exit(0);
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
     * @param day      The day in question (0-6) from the start of the week
     * @param activity The timeslot to get the legal sctivities for (0-5)
     * @return A list of legal activities for day number day, group group, activity
     *         number activity, and currently scheduled.
     */
    public ArrayList<Activity> getAvailableActivities(Group group, DaySchedule current, int day, int activity) {
        ArrayList<Activity> availableActivities = new ArrayList<Activity>();
        for(Activity potActivity : ActivitiesList.getActivities()){
            boolean available = true;
            for(Group othGroup : getWeek(group).getGroups()){
                if(othGroup.getSchedule().get(day).getActivities().get(activity).getId().equals(potActivity.getId())){
                    available = false;
                }
            }
            if(available){
                availableActivities.add(potActivity);
            }
        }
        return availableActivities;
    }

    public ArrayList<Week> getNextScheduledWeek() {
        // returns the next week that the user is scheduled for
        // returns only the next scheduled week if user is not scheduled for the current
        // week
        // returns both the current week, followed by the next scheduled week if the
        // returns an empty ArrayList<Week> if the counselor is not scheduled for a
        // future or current week
        // (does not return null)
        // counseler is scheduled for the current week
        // (only called if user is a Counselor)
        return null;
    }

}