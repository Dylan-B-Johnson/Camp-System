// Copyright row 3

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataReader {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String PASSWORD = "password";
    private static final String CAMPERS = "campers";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String DESCRIPTION = "description";
    private static final String ALLERGIES = "allergies";
    private static final String PRIMARYEMERGENCYCONTACT = "primaryEmergencyContact";
    private static final String PASTENROLLMENT = "pastEnrollment";
    private static final String SWIMTESTRESULT = "swimTestResult";
    private static final String ADDRESS = "address";
    private static final String RELATIONSHIP = "relationship";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String RELATIONTOCUSTOMER = "relationToCustomer";
    private static final String GROUPSIZE = "groupSize";
    private static final String COUNSELOR = "counselor";
    private static final String PRICEPERCAMPER = "pricePerCamper";

    private static DirtyFlags dirtyFlags = new DirtyFlags();

    private static ArrayList<Customer> customerCache = null;
    private static CampLocation campLocationCache = null;

    public static void main(String[] args) {
        // temp for tests
        System.out.println(getCustomer(UUID.fromString("de162b09-ab98-4ec5-9bf2-1a5fdc86c7e7")));
        System.out.println(getCustomer(UUID.fromString("de162b09-ab98-4ec5-9bf2-1a5fdc86c7e7")));
        setDirtyFlag();
        System.out.println(getCustomer(UUID.fromString("de162b09-ab98-4ec5-9bf2-1a5fdc86c7e7")));

    }

    public static void setDirtyFlag() {
        dirtyFlags.customerDirty = true;
    }

    public static Customer getCustomer(UUID id) {
        for (Customer customer : getCustomers()) {
            if (customer.getId().compareTo(id) == 0)
                return customer;
        }
        return null;
    }

    public static ArrayList<Customer> getCustomers() {
        if (customerCache != null && !dirtyFlags.customerDirty) {
            return customerCache;
        }
        JSONParser parser = new JSONParser();
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        try {
            JSONArray customers = (JSONArray) parser.parse(new FileReader("data/customers.json"));
            for (Object object : customers) {
                JSONObject user = (JSONObject) object;
                String id = (String) user.get(ID);
                String email = (String) user.get(EMAIL);
                String firstName = (String) user.get(FIRSTNAME);
                String lastName = (String) user.get(LASTNAME);
                ArrayList<Camper> campers = new ArrayList<Camper>();
                customerList
                        .add(new Customer(UUID.fromString(id), email, firstName, lastName, lastName, null, campers));
            }
        } catch (Exception exception) {

        }
        customerCache = customerList;
        return customerList;
    }

    public static Activity getActivity(UUID id) {
        for (Activity activity : getActivities()) {
            if (activity.getId().compareTo(id) == 0)
                return activity;
        }
        return null;
    }

    public static ArrayList<Activity> getActivities() {
        JSONParser parser = new JSONParser();
        ArrayList<Activity> activities = new ArrayList<Activity>();

        try {
            JSONArray jActivities = (JSONArray) parser.parse(new FileReader("data/activities.json"));
            for (Object object : jActivities) {
                JSONObject activity = (JSONObject) object;
                String id = (String) activity.get(ID);
                String name = (String) activity.get(NAME);
                String location = (String) activity.get(LOCATION);
                String description = (String) activity.get(DESCRIPTION);
                activities.add(new Activity(UUID.fromString(id), name, location, description));
            }
        } catch (Exception exception) {

        }

        return activities;
    }

    public static ArrayList<Week> getWeeks() {
        return null;
    }

    public static Camper getCamper(UUID id) {
        ArrayList<Camper> allCampers = getCampers();
        for (Camper camper : allCampers) {
            if (camper.getId().compareTo(id) == 0) {
                return camper;
            }
        }
        return null;
    }

    public static ArrayList<Camper> getCampers() {
        JSONParser parser = new JSONParser();
        ArrayList<Camper> camperList = new ArrayList<>();
        try {
            JSONArray campers = (JSONArray) parser.parse(new FileReader("data/campers.json"));
            for (Object object : campers) {
                JSONObject camper = (JSONObject) object;
                UUID id = UUID.fromString((String) camper.get(ID));
                String firstName = (String) camper.get(FIRSTNAME);
                String lastName = (String) camper.get(LASTNAME);
                ArrayList<String> allergies = new ArrayList<String>();
                JSONArray jsonAllergies = (JSONArray) camper.get(ALLERGIES);
                for (Object o : jsonAllergies) {
                    allergies.add((String) o);
                }
                LocalDate birthday = LocalDate.now();
                JSONObject pec = (JSONObject) camper.get(PRIMARYEMERGENCYCONTACT);
                Contact primaryEmergencyContact = new Contact((String) pec.get(FIRSTNAME),
                        (String) pec.get(LASTNAME), (String) pec.get(EMAIL), (String) pec.get(PHONENUMBER),
                        (String) pec.get(RELATIONSHIP), (String) pec.get(ADDRESS));
                Contact secondaryEmergencyContact = null;
                Contact primaryCarePhysician = null;
                int pastEnrollment = ((Long) camper.get(PASTENROLLMENT)).intValue();
                String swimTestResult = (String) camper.get(SWIMTESTRESULT);
                String relationToCustomer = (String) camper.get(RELATIONTOCUSTOMER);
                camperList.add(new Camper(id, firstName, lastName, allergies, birthday, primaryEmergencyContact,
                        secondaryEmergencyContact, primaryCarePhysician, pastEnrollment, swimTestResult,
                        relationToCustomer));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return camperList;
    }

    public static Group getGroup(UUID id) {
        ArrayList<Group> groups = getGroups();
        for (Group group : groups) {
            if (group.getId().compareTo(id) == 0) {
                return group;
            }
        }
        return null;
    }

    public static ArrayList<Group> getGroups() {
        JSONParser parser = new JSONParser();
        ArrayList<Group> groupList = new ArrayList<>();
        try {
            JSONArray groups = (JSONArray) parser.parse(new FileReader("data/groups.json"));
            for (Object object : groups) {
                JSONObject group = (JSONObject) object;
                UUID id = UUID.fromString((String) group.get(ID));
                int groupSize = ((Long) group.get(GROUPSIZE)).intValue();
                ArrayList<Camper> campers = new ArrayList<>();
                JSONArray jCampers = (JSONArray) group.get(CAMPERS);
                for (Object camper : jCampers) {
                    JSONObject cam = (JSONObject) camper;
                    campers.add(getCamper(UUID.fromString((String) cam.get(ID))));
                }
                Counselor counselor = getCounselor(UUID.fromString((String) group.get(COUNSELOR)));
                ArrayList<DaySchedule> schedule = new ArrayList<>();
                groupList.add(new Group(id, campers, counselor, groupSize, schedule));
            }
        } catch (Exception exception) {

        }
        return groupList;
    }

    public static Counselor getCounselor(UUID id) {
        for (Counselor counselor : getCounselors()) {
            if (counselor.getId().compareTo(id) == 0)
                return counselor;
        }
        return null;
    }

    public static ArrayList<Counselor> getCounselors() {
        JSONParser parser = new JSONParser();
        ArrayList<Counselor> counselorList = new ArrayList<>();

        try {
            JSONArray counselors = (JSONArray) parser.parse(new FileReader("data/counselors.json"));
            for (Object object : counselors) {
                JSONObject counselor = (JSONObject) object;
                UUID id = UUID.fromString((String) counselor.get(ID));
                String email = (String) counselor.get(EMAIL);
                String firstName = (String) counselor.get(FIRSTNAME);
                String lastName = (String) counselor.get(LASTNAME);
                String password = (String) counselor.get(PASSWORD);

                counselorList.add(new Counselor(id, email, firstName, lastName, password, null));
            }
        } catch (Exception exception) {

        }

        return counselorList;
    }

    public static Director getDirector() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject director = (JSONObject) parser.parse(new FileReader("data/director.json"));
            UUID id = UUID.fromString((String) director.get(ID));
            String email = (String) director.get(EMAIL);
            String firstName = (String) director.get(FIRSTNAME);
            String lastName = (String) director.get(LASTNAME);
            String password = (String) director.get(PASSWORD);
            return new Director(id, email, firstName, lastName, password, null);
        } catch (Exception exception) {

        }
        return null;
    }

    public static CampLocation getCampLocation() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject campLocation = (JSONObject) parser.parse(new FileReader("data/campLocation.json"));
            UUID id = UUID.fromString((String) campLocation.get(ID));
            String name = (String) campLocation.get(NAME);
            String location = (String) campLocation.get(LOCATION);
            double pricePerCamper = (double) campLocation.get(PRICEPERCAMPER);

            return new CampLocation(id, name, location, pricePerCamper);
        } catch (Exception exception) {

        }
        return null;
    }
}
