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
    private static final String SECONDARYEMERGENCYCONTACT = "secondaryEmergencyContact";
    private static final String PRIMARYCAREPHYSICIAN = "primaryCarePhysician";
    private static final String PASTENROLLMENT = "pastEnrollment";
    private static final String SWIMTESTRESULT = "swimTestResult";
    private static final String ADDRESS = "address";
    private static final String RELATIONSHIP = "relationship";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String RELATIONTOCUSTOMER = "relationToCustomer";
    private static final String GROUPSIZE = "groupSize";
    private static final String COUNSELOR = "counselor";
    private static final String PRICEPERCAMPER = "pricePerCamper";
    private static final String MAXCAMPERS = "maxCampers";
    private static final String CURRENTCAMPERS = "currentCampers";
    private static final String STARTOFWEEK = "startOfWeek";
    private static final String GROUPS = "groups";

    private static DirtyFlags dirtyFlags = new DirtyFlags();

    public static final int CUSTOMERSDIRTY = 0;
    public static final int ACTIVITIESDIRTY = 1;
    public static final int CAMPERSDIRTY = 2;
    public static final int GROUPSDIRTY = 3;
    public static final int COUNSELORDIRTY = 4;
    public static final int DIRECTORDIRTY = 5;
    public static final int CAMPLOCATIONDIRTY = 6;
    public static final int WEEKSDIRTY = 7;

    private static ArrayList<Customer> customerCache = null;
    private static ArrayList<Activity> activityCache = null;
    private static ArrayList<Camper> camperCache = null;
    private static ArrayList<Group> groupCache = null;
    private static ArrayList<Counselor> counselorCache = null;
    private static Director directorCache = null;
    private static CampLocation campLocationCache = null;
    private static ArrayList<Week> weeksCache = null;

    public static void main(String[] args) {
        // temp for tests
        getWeeks();
        System.out.println(getCustomer(UUID.fromString("de162b09-ab98-4ec5-9bf2-1a5fdc86c7e7")));
        System.out.println(getCustomer(UUID.fromString("de162b09-ab98-4ec5-9bf2-1a5fdc86c7e7")));
        setDirtyFlag(CUSTOMERSDIRTY);
        System.out.println(getCustomer(UUID.fromString("de162b09-ab98-4ec5-9bf2-1a5fdc86c7e7")));

    }

    public static void setDirtyFlag(int flagToSet) {
        switch (flagToSet) {
            case 0:
                dirtyFlags.customersDirty = true;
                return;
            case 1:
                dirtyFlags.activitiesDirty = true;
                return;
            case 2:
                dirtyFlags.campersDirty = true;
                return;
            case 3:
                dirtyFlags.groupsDirty = true;
                return;
            case 4:
                dirtyFlags.counselorDirty = true;
                return;
            case 5:
                dirtyFlags.directorDirty = true;
                return;
            case 6:
                dirtyFlags.campLocationDirty = true;
                return;
            case 7:
                dirtyFlags.weeksDirty = true;
                return;
        }
    }

    public static Customer getCustomer(UUID id) {
        for (Customer customer : getCustomers()) {
            if (customer.getId().compareTo(id) == 0)
                return customer;
        }
        return null;
    }

    public static ArrayList<Customer> getCustomers() {
        if (customerCache != null && !dirtyFlags.customersDirty) {
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
        if (activityCache != null && !dirtyFlags.activitiesDirty) {
            return activityCache;
        }
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
        activityCache = activities;
        return activities;
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
        if (camperCache != null && !dirtyFlags.campersDirty) {
            return camperCache;
        }
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
                JSONObject sec = (JSONObject) camper.get(SECONDARYEMERGENCYCONTACT);
                Contact secondaryEmergencyContact = new Contact((String) sec.get(FIRSTNAME),
                        (String) sec.get(LASTNAME), (String) sec.get(EMAIL), (String) sec.get(PHONENUMBER),
                        (String) sec.get(RELATIONSHIP), (String) sec.get(ADDRESS));
                JSONObject pcp = (JSONObject) camper.get(PRIMARYCAREPHYSICIAN);
                Contact primaryCarePhysician = new Contact((String) pcp.get(FIRSTNAME),
                        (String) pcp.get(LASTNAME), (String) pcp.get(EMAIL), (String) pcp.get(PHONENUMBER),
                        (String) pcp.get(RELATIONSHIP), (String) pcp.get(ADDRESS));
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
        camperCache = camperList;
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
        if (groupCache != null && !dirtyFlags.groupsDirty) {
            return groupCache;
        }
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
        groupCache = groupList;
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
        if (counselorCache != null && !dirtyFlags.counselorDirty) {
            return counselorCache;
        }
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
                CampLocation campLocation = getCampLocation();
                counselorList.add(new Counselor(id, email, firstName, lastName, password, campLocation));
            }
        } catch (Exception exception) {

        }
        counselorCache = counselorList;
        return counselorList;
    }

    public static Director getDirector() {
        if (directorCache != null && !dirtyFlags.directorDirty) {
            return directorCache;
        }
        JSONParser parser = new JSONParser();
        try {
            JSONObject director = (JSONObject) parser.parse(new FileReader("data/director.json"));
            UUID id = UUID.fromString((String) director.get(ID));
            String email = (String) director.get(EMAIL);
            String firstName = (String) director.get(FIRSTNAME);
            String lastName = (String) director.get(LASTNAME);
            String password = (String) director.get(PASSWORD);
            CampLocation campLocation = getCampLocation();
            Director directorObject = new Director(id, email, firstName, lastName, password, campLocation);
            directorCache = directorObject;
            return directorObject;
        } catch (Exception exception) {

        }
        return null;
    }

    public static CampLocation getCampLocation() {
        if (campLocationCache != null && !dirtyFlags.campLocationDirty) {
            return campLocationCache;
        }
        JSONParser parser = new JSONParser();
        try {
            JSONObject campLocation = (JSONObject) parser.parse(new FileReader("data/campLocation.json"));
            UUID id = UUID.fromString((String) campLocation.get(ID));
            String name = (String) campLocation.get(NAME);
            String location = (String) campLocation.get(LOCATION);
            double pricePerCamper = (double) campLocation.get(PRICEPERCAMPER);
            CampLocation campLocationObject = new CampLocation(id, name, location, pricePerCamper);
            campLocationCache = campLocationObject;
            return campLocationObject;
        } catch (Exception exception) {

        }
        return null;
    }

    public static Week getWeek(UUID id) {
        for (Week week : getWeeks()) {
            if (week.getId().compareTo(id) == 0) {
                return week;
            }
        }
        return null;
    }

    public static ArrayList<Week> getWeeks() {
        if (weeksCache != null && !dirtyFlags.weeksDirty) {
            return weeksCache;
        }
        JSONParser parser = new JSONParser();
        ArrayList<Week> weeksList = new ArrayList<>();
        try {
            JSONArray weeksArray = (JSONArray) parser.parse(new FileReader("data/weeks.json"));
            for (Object weekObject : weeksArray) {
                JSONObject weekJsonObject = (JSONObject) weekObject;
                UUID id = UUID.fromString((String) weekJsonObject.get(ID));
                int maxCampers = ((Long) weekJsonObject.get(MAXCAMPERS)).intValue();
                int currentCampers = ((Long) weekJsonObject.get(CURRENTCAMPERS)).intValue();
                LocalDate startOfWeek = LocalDate.parse((String) weekJsonObject.get(STARTOFWEEK));
                ArrayList<Group> groups = new ArrayList<>();
                JSONArray groupArray = (JSONArray) weekJsonObject.get(GROUPS);
                for (Object groupObject : groupArray) {
                    groups.add(getGroup(UUID.fromString((String) groupObject)));
                }
                CampLocation campLocation = getCampLocation();
                weeksList.add(new Week(id, maxCampers, currentCampers, startOfWeek, groups, campLocation));
            }
        } catch (Exception exception) {

        }
        weeksCache = weeksList;
        return weeksList;
    }
}
