import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DataReaderTest {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Camper> campers = new ArrayList<>();
    private ArrayList<Counselor> counselors = new ArrayList<>();
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<DaySchedule> daySchedules = new ArrayList<>();
    private ArrayList<Week> weeks = new ArrayList<>();
    private CampLocation campLocation = new CampLocation("name", "location", 12);
    private Director director = new Director("test@test.test", "firstname", "lastname", "password",
            DataReader.getCampLocation());;

    private Contact getTestContact() {
        return new Contact("firstName", "lastName", "test@test.test", "8034772355", "mom", "101 test dr");
    }

    @BeforeEach
    public void setup() {
        DataWriter.saveCustomers(new HashMap<>());
        DataWriter.saveCampers(new HashMap<>());
        DataWriter.saveCounselors(new HashMap<>());
        DataWriter.saveActivities(new HashMap<>());
        DataWriter.saveGroups(new HashMap<>());
        DataWriter.saveWeeks(new HashMap<>());
        DataWriter.saveDaySchedules(new HashMap<>());
        DataWriter.saveCampLocation(null);
        DataWriter.saveDirector(null);
        campers.add(new Camper("firstName", "lastName", new ArrayList<String>(), LocalDate.of(2010, 1, 1),
                getTestContact(), getTestContact(), getTestContact(), 0,
                "good swimmer", "daughter"));
        customers.add(new Customer("test@test.test", "firstName", "lastName", "password",
                campLocation, campers,
                new Contact("firstName", "lastName", "test@test.test", "8034772355", "mom", "101 test dr")));
        counselors.add(new Counselor(new ArrayList<>(), LocalDate.of(2002, 1, 1), "test@test.test", "counselor",
                "lastName", "password", campLocation, getTestContact(), getTestContact(),
                getTestContact()));
        groups.add(new Group(campers, 1, daySchedules, counselors.get(0)));
        weeks.add(new Week(15, 1, LocalDate.of(2023, 1, 1), campLocation, "theme"));
        daySchedules.add(new DaySchedule(activities, weeks.get(0), LocalDate.of(2023, 1, 2)));
        activities.add(new Activity("activityName", "activityLocation", "activityDescription"));
        for (Counselor counselor : counselors) {
            DataWriter.createCounselor(counselor);
        }
        for (Customer customer : customers) {
            DataWriter.createCustomer(customer);
        }
        for (Camper camper : campers) {
            DataWriter.createCamper(camper);
        }
        for (Activity activity : activities) {
            DataWriter.createActivity(activity);
        }
        for (Week week : weeks) {
            DataWriter.createWeek(week);
        }
        for (Group group : groups) {
            DataWriter.createGroup(group);
        }
        for (DaySchedule daySchedule : daySchedules) {
            DataWriter.createDaySchedule(daySchedule);
        }
        DataWriter.saveDirector(director);
        DataWriter.saveCampLocation(campLocation);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void testGetCustomerSize() {
        assertEquals(customers.size(), DataReader.getCustomers().values().size());
    }

    @Test
    void testGetCustomer() {
        assertEquals(customers.get(0).getFirstName(),
                DataReader.getCustomers().values().iterator().next().getFirstName());
        assertEquals(customers.get(0).getLastName(),
                DataReader.getCustomers().values().iterator().next().getLastName());
        assertEquals(customers.get(0).getEmail(),
                DataReader.getCustomers().values().iterator().next().getEmail());
        assertEquals(customers.get(0).getCampers().get(0).getFirstName(),
                DataReader.getCustomers().values().iterator().next().getCampers().get(0).getFirstName());
        assertEquals(customers.get(0).getFirstName(),
                DataReader.getCustomers().values().iterator().next().getFirstName());
        assertEquals(customers.get(0).getPassword(),
                DataReader.getCustomers().values().iterator().next().getPassword());
        assertEquals(customers.get(0).getCampLocation().getName(),
                DataReader.getCustomers().values().iterator().next().getCampLocation().getName());
        assertEquals(customers.get(0).getContactInfo().getPhoneNum(),
                DataReader.getCustomers().values().iterator().next().getContactInfo().getPhoneNum());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = DataReader.getCustomer(customers.get(0).getId());
        assertEquals(customer.getFirstName(),
                customers.get(0).getFirstName());
        assertEquals(customer.getLastName(),
                customers.get(0).getLastName());
        assertEquals(customer.getEmail(),
                customers.get(0).getEmail());
        assertEquals(customer.getCampers().get(0).getFirstName(),
                customers.get(0).getCampers().get(0).getFirstName());
        assertEquals(customer.getFirstName(),
                customers.get(0).getFirstName());
        assertEquals(customer.getPassword(),
                customers.get(0).getPassword());
        assertEquals(customer.getCampLocation().getName(),
                customers.get(0).getCampLocation().getName());
        assertEquals(customer.getContactInfo().getPhoneNum(),
                customers.get(0).getContactInfo().getPhoneNum());
    }

    @Test
    void testGetCamperSize() {
        assertEquals(campers.size(), DataReader.getCampers().values().size());
    }

    @Test
    void testGetCamper() {
        assertEquals(campers.get(0).getFirstName(), DataReader.getCampers().values().iterator().next().getFirstName());
        assertEquals(campers.get(0).getLastName(), DataReader.getCampers().values().iterator().next().getLastName());
        assertEquals(campers.get(0).getAllergies().size(),
                DataReader.getCampers().values().iterator().next().getAllergies().size());
        assertEquals(campers.get(0).getBirthday(), DataReader.getCampers().values().iterator().next().getBirthday());
        assertEquals(campers.get(0).getPrimaryEmergencyContact().getFirstName(),
                DataReader.getCampers().values().iterator().next().getPrimaryEmergencyContact().getFirstName());
        assertEquals(campers.get(0).getSecondaryEmergencyContact().getFirstName(),
                DataReader.getCampers().values().iterator().next().getSecondaryEmergencyContact().getFirstName());
        assertEquals(campers.get(0).getPrimaryCarePhysician().getFirstName(),
                DataReader.getCampers().values().iterator().next().getPrimaryCarePhysician().getFirstName());
        assertEquals(campers.get(0).getPastEnrollment(),
                DataReader.getCampers().values().iterator().next().getPastEnrollment());
        assertEquals(campers.get(0).getSwimTestResult(),
                DataReader.getCampers().values().iterator().next().getSwimTestResult());
        assertEquals(campers.get(0).getRelationshipToCustomer(),
                DataReader.getCampers().values().iterator().next().getRelationshipToCustomer());
    }

    @Test
    void testGetCamperById() {
        Camper camper = DataReader.getCamper(campers.get(0).getId());
        assertEquals(campers.get(0).getFirstName(), camper.getFirstName());
        assertEquals(campers.get(0).getLastName(), camper.getLastName());
        assertEquals(campers.get(0).getAllergies().size(),
                camper.getAllergies().size());
        assertEquals(campers.get(0).getBirthday(), camper.getBirthday());
        assertEquals(campers.get(0).getPrimaryEmergencyContact().getFirstName(),
                camper.getPrimaryEmergencyContact().getFirstName());
        assertEquals(campers.get(0).getSecondaryEmergencyContact().getFirstName(),
                camper.getSecondaryEmergencyContact().getFirstName());
        assertEquals(campers.get(0).getPrimaryCarePhysician().getFirstName(),
                camper.getPrimaryCarePhysician().getFirstName());
        assertEquals(campers.get(0).getPastEnrollment(),
                camper.getPastEnrollment());
        assertEquals(campers.get(0).getSwimTestResult(),
                camper.getSwimTestResult());
        assertEquals(campers.get(0).getRelationshipToCustomer(),
                camper.getRelationshipToCustomer());
    }

    @Test
    void testGetCounselorSize() {
        assertEquals(counselors.size(), DataReader.getCampers().values().size());
    }

    @Test
    void testGetCounselor() {
        assertEquals(counselors.get(0).getAllergies().size(),
                DataReader.getCounselors().values().iterator().next().getAllergies().size());
        assertEquals(counselors.get(0).getBirthday(),
                DataReader.getCounselors().values().iterator().next().getBirthday());
        assertEquals(counselors.get(0).getEmail(),
                DataReader.getCounselors().values().iterator().next().getEmail());
        assertEquals(counselors.get(0).getFirstName(),
                DataReader.getCounselors().values().iterator().next().getFirstName());
        assertEquals(counselors.get(0).getLastName(),
                DataReader.getCounselors().values().iterator().next().getLastName());
        assertEquals(counselors.get(0).getPassword(),
                DataReader.getCounselors().values().iterator().next().getPassword());
        assertEquals(counselors.get(0).getCampLocation().getName(),
                DataReader.getCounselors().values().iterator().next().getCampLocation().getName());
        assertEquals(counselors.get(0).getPrimaryEmergencyContact().getFirstName(),
                DataReader.getCounselors().values().iterator().next().getPrimaryEmergencyContact().getFirstName());
        assertEquals(counselors.get(0).getSecondaryEmergencyContact().getFirstName(),
                DataReader.getCounselors().values().iterator().next().getSecondaryEmergencyContact().getFirstName());
        assertEquals(counselors.get(0).getPrimaryCarePhysician().getFirstName(),
                DataReader.getCounselors().values().iterator().next().getPrimaryCarePhysician().getFirstName());
    }

    @Test
    void testGetCounselorById() {
        Counselor counselor = DataReader.getCounselor(counselors.get(0).getId());
        assertEquals(counselors.get(0).getAllergies().size(),
                counselor.getAllergies().size());
        assertEquals(counselors.get(0).getBirthday(),
                counselor.getBirthday());
        assertEquals(counselors.get(0).getEmail(),
                counselor.getEmail());
        assertEquals(counselors.get(0).getFirstName(),
                counselor.getFirstName());
        assertEquals(counselors.get(0).getLastName(),
                counselor.getLastName());
        assertEquals(counselors.get(0).getPassword(),
                counselor.getPassword());
        assertEquals(counselors.get(0).getCampLocation().getName(),
                counselor.getCampLocation().getName());
        assertEquals(counselors.get(0).getPrimaryEmergencyContact().getFirstName(),
                counselor.getPrimaryEmergencyContact().getFirstName());
        assertEquals(counselors.get(0).getSecondaryEmergencyContact().getFirstName(),
                counselor.getSecondaryEmergencyContact().getFirstName());
        assertEquals(counselors.get(0).getPrimaryCarePhysician().getFirstName(),
                counselor.getPrimaryCarePhysician().getFirstName());
    }

    @Test
    void testGetActivitySize() {
        assertEquals(activities.size(), DataReader.getActivities().size());
    }

    @Test
    void testGetActivity() {
        assertEquals(activities.get(0).getName(), DataReader.getActivities().values().iterator().next().getName());
        assertEquals(activities.get(0).getLocation(),
                DataReader.getActivities().values().iterator().next().getLocation());
        assertEquals(activities.get(0).getDescription(),
                DataReader.getActivities().values().iterator().next().getDescription());
    }

    @Test
    void testGetActivityById() {
        Activity activity = DataReader.getActivity(activities.get(0).getId());
        assertEquals(activities.get(0).getName(), activity.getName());
        assertEquals(activities.get(0).getLocation(),
                activity.getLocation());
        assertEquals(activities.get(0).getDescription(),
                activity.getDescription());
    }

    @Test
    void testGetGroupSize() {
        assertEquals(groups.size(), DataReader.getGroups().size());
    }

    @Test
    void testGetGroup() {
        assertEquals(groups.get(0).getCampers().get(0).getFirstName(),
                DataReader.getGroups().values().iterator().next().getCampers().get(0).getFirstName());
        assertEquals(groups.get(0).getGroupSize(),
                DataReader.getGroups().values().iterator().next().getGroupSize());
        assertEquals(groups.get(0).getSchedule().get(0).getDay(),
                DataReader.getGroups().values().iterator().next().getSchedule().get(0).getDay());
        assertEquals(groups.get(0).getCounselor().getFirstName(),
                DataReader.getGroups().values().iterator().next().getCounselor().getFirstName());
    }

    @Test
    void testGetGroupById() {
        Group group = DataReader.getGroup(groups.get(0).getId());
        assertEquals(groups.get(0).getCampers().get(0).getFirstName(),
                group.getCampers().get(0).getFirstName());
        assertEquals(groups.get(0).getGroupSize(),
                group.getGroupSize());
        assertEquals(groups.get(0).getSchedule().get(0).getDay(),
                group.getSchedule().get(0).getDay());
        assertEquals(groups.get(0).getCounselor().getFirstName(),
                group.getCounselor().getFirstName());
    }

    @Test
    void testGetWeekSize() {
        assertEquals(weeks.size(), DataReader.getWeeks().size());
    }

    @Test
    void testGetWeek() {
        assertEquals(weeks.get(0).getMaxCampers(), DataReader.getWeeks().values().iterator().next().getMaxCampers());
        assertEquals(weeks.get(0).getCurrentCampers(),
                DataReader.getWeeks().values().iterator().next().getCurrentCampers());
        assertEquals(weeks.get(0).getStartOfWeek(), DataReader.getWeeks().values().iterator().next().getStartOfWeek());
        assertEquals(weeks.get(0).getGroups().get(0).getGroupSize(),
                DataReader.getWeeks().values().iterator().next().getGroups().get(0).getGroupSize());
        assertEquals(weeks.get(0).getCampLocation().getName(),
                DataReader.getWeeks().values().iterator().next().getCampLocation().getName());
        assertEquals(weeks.get(0).getTheme(), DataReader.getWeeks().values().iterator().next().getTheme());
    }

    @Test
    void testGetWeekById() {
        Week week = DataReader.getWeek(weeks.get(0).getId());
        assertEquals(weeks.get(0).getMaxCampers(), week.getMaxCampers());
        assertEquals(weeks.get(0).getCurrentCampers(),
                week.getCurrentCampers());
        assertEquals(weeks.get(0).getStartOfWeek(), week.getStartOfWeek());
        assertEquals(weeks.get(0).getGroups().get(0).getGroupSize(),
                week.getGroups().get(0).getGroupSize());
        assertEquals(weeks.get(0).getCampLocation().getName(),
                week.getCampLocation().getName());
        assertEquals(weeks.get(0).getTheme(), week.getTheme());
    }

    @Test
    void testGetDayScheduleSize() {
        assertEquals(daySchedules.size(), DataReader.getDaySchedules().size());
    }

    @Test
    void testGetDaySchedule() {
        assertEquals(daySchedules.get(0).getCurrentAcitivities().get(0).getDescription(), DataReader.getDaySchedules()
                .values().iterator().next().getCurrentAcitivities().get(0).getDescription());
        assertEquals(daySchedules.get(0).getWeek().getTheme(), DataReader.getDaySchedules()
                .values().iterator().next().getWeek().getTheme());
        assertEquals(daySchedules.get(0).getDay(), DataReader.getDaySchedules()
                .values().iterator().next().getDay());
    }

    @Test
    void testGetDayScheduleById() {
        DaySchedule daySchedule = DataReader.getDaySchedule(daySchedules.get(0).getId());
        assertEquals(daySchedules.get(0).getCurrentAcitivities().get(0).getDescription(),
                daySchedule.getCurrentAcitivities().get(0).getDescription());
        assertEquals(daySchedules.get(0).getWeek().getTheme(), daySchedule.getWeek().getTheme());
        assertEquals(daySchedules.get(0).getDay(), daySchedule.getDay());
    }

    @Test
    void testGetCampLocation() {
        assertEquals(campLocation.getName(), DataReader.getCampLocation().getName());
        assertEquals(campLocation.getLocation(), DataReader.getCampLocation().getLocation());
        assertEquals(campLocation.getPricePerCamper(), DataReader.getCampLocation().getPricePerCamper());
    }

    @Test
    void testGetDirector() {
        assertEquals(director.getEmail(), DataReader.getDirector().getEmail());
        assertEquals(director.getFirstName(), DataReader.getDirector().getFirstName());
        assertEquals(director.getLastName(), DataReader.getDirector().getLastName());
        assertEquals(director.getPassword(), DataReader.getDirector().getPassword());
        assertEquals(director.getCampLocation().getName(), DataReader.getDirector().getCampLocation().getName());
    }
}
