// Copyright row 3

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataReader {

    public static final int CUSTOMERSDIRTY = 0;
    public static final int ACTIVITIESDIRTY = 1;
    public static final int CAMPERSDIRTY = 2;
    public static final int GROUPSDIRTY = 3;
    public static final int COUNSELORDIRTY = 4;
    public static final int DIRECTORDIRTY = 5;
    public static final int CAMPLOCATIONDIRTY = 6;
    public static final int WEEKSDIRTY = 7;
    public static final int DAYSCHEDULESDIRTY = 8;

    private static DirtyFlags dirtyFlags = new DirtyFlags();

    private static HashMap<UUID, Customer> customerCache = null;
    private static HashMap<UUID, Activity> activityCache = null;
    private static HashMap<UUID, Camper> camperCache = null;
    private static HashMap<UUID, Group> groupCache = null;
    private static HashMap<UUID, Counselor> counselorCache = null;
    private static Director directorCache = null;
    private static CampLocation campLocationCache = null;
    private static HashMap<UUID, Week> weeksCache = null;
    private static HashMap<UUID, DaySchedule> daySchedulesCache = null;

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
            case 8:
                dirtyFlags.daySchedulesDirty = true;
                return;
        }
    }

    public static Customer getCustomer(UUID id) {
        HashMap<UUID, Customer> customerHashMap = getCustomers();
        if (customerHashMap.containsKey(id)) {
            return customerHashMap.get(id);
        }
        return null;
    }

    public static HashMap<UUID, Customer> getCustomers() {
        if (customerCache != null && !dirtyFlags.customersDirty) {
            return customerCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, Customer> customerList = new HashMap<>();
        try {
            JSONArray customers = (JSONArray) parser.parse(new FileReader("data/customers.json"));
            for (Object object : customers) {
                JSONObject user = (JSONObject) object;
                String id = (String) user.get(DataConstants.ID);
                String email = (String) user.get(DataConstants.EMAIL);
                String firstName = (String) user.get(DataConstants.FIRSTNAME);
                String lastName = (String) user.get(DataConstants.LASTNAME);
                String password = (String) user.get(DataConstants.PASSWORD);
                ArrayList<Camper> campers = new ArrayList<Camper>();
                JSONArray camperJsonArray = (JSONArray) user.get(DataConstants.CAMPERS);
                for (Object camperObject : camperJsonArray) {
                    String camperId = (String) camperObject;
                    Camper camper = getCamper(UUID.fromString(camperId));
                    campers.add(camper);
                }
                customerList
                        .put(UUID.fromString(id),
                                new Customer(UUID.fromString(id), email, firstName, lastName, password,
                                        getCampLocation(),
                                        campers));
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
        customerCache = customerList;
        return customerList;
    }

    public static Activity getActivity(UUID id) {
        HashMap<UUID, Activity> activityHashMap = getActivities();
        if (activityHashMap.containsKey(id))
            return activityHashMap.get(id);
        return null;
    }

    public static HashMap<UUID, Activity> getActivities() {
        if (activityCache != null && !dirtyFlags.activitiesDirty) {
            return activityCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, Activity> activities = new HashMap<>();

        try {
            JSONArray jActivities = (JSONArray) parser.parse(new FileReader("data/activities.json"));
            for (Object object : jActivities) {
                JSONObject activity = (JSONObject) object;
                String id = (String) activity.get(DataConstants.ID);
                String name = (String) activity.get(DataConstants.NAME);
                String location = (String) activity.get(DataConstants.LOCATION);
                String description = (String) activity.get(DataConstants.DESCRIPTION);
                activities.put(UUID.fromString(id), new Activity(UUID.fromString(id), name, location, description));
            }
        } catch (Exception exception) {

        }
        activityCache = activities;
        return activities;
    }

    public static Camper getCamper(UUID id) {
        HashMap<UUID, Camper> allCampers = getCampers();
        if (allCampers.containsKey(id))
            return allCampers.get(id);
        return null;
    }

    public static HashMap<UUID, Camper> getCampers() {
        if (camperCache != null && !dirtyFlags.campersDirty) {
            return camperCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, Camper> camperList = new HashMap<>();
        try {
            JSONArray campers = (JSONArray) parser.parse(new FileReader("data/campers.json"));
            for (Object object : campers) {
                JSONObject camper = (JSONObject) object;
                UUID id = UUID.fromString((String) camper.get(DataConstants.ID));
                String firstName = (String) camper.get(DataConstants.FIRSTNAME);
                String lastName = (String) camper.get(DataConstants.LASTNAME);
                ArrayList<String> allergies = new ArrayList<String>();
                JSONArray jsonAllergies = (JSONArray) camper.get(DataConstants.ALLERGIES);
                for (Object o : jsonAllergies) {
                    allergies.add((String) o);
                }
                LocalDate birthday = LocalDate.now();
                JSONObject pec = (JSONObject) camper.get(DataConstants.PRIMARYEMERGENCYCONTACT);
                Contact primaryEmergencyContact = new Contact((String) pec.get(DataConstants.FIRSTNAME),
                        (String) pec.get(DataConstants.LASTNAME), (String) pec.get(DataConstants.EMAIL),
                        (String) pec.get(DataConstants.PHONENUMBER),
                        (String) pec.get(DataConstants.RELATIONSHIP), (String) pec.get(DataConstants.ADDRESS));
                JSONObject sec = (JSONObject) camper.get(DataConstants.SECONDARYEMERGENCYCONTACT);
                Contact secondaryEmergencyContact = new Contact((String) sec.get(DataConstants.FIRSTNAME),
                        (String) sec.get(DataConstants.LASTNAME), (String) sec.get(DataConstants.EMAIL),
                        (String) sec.get(DataConstants.PHONENUMBER),
                        (String) sec.get(DataConstants.RELATIONSHIP), (String) sec.get(DataConstants.ADDRESS));
                JSONObject pcp = (JSONObject) camper.get(DataConstants.PRIMARYCAREPHYSICIAN);
                Contact primaryCarePhysician = new Contact((String) pcp.get(DataConstants.FIRSTNAME),
                        (String) pcp.get(DataConstants.LASTNAME), (String) pcp.get(DataConstants.EMAIL),
                        (String) pcp.get(DataConstants.PHONENUMBER),
                        (String) pcp.get(DataConstants.RELATIONSHIP), (String) pcp.get(DataConstants.ADDRESS));
                int pastEnrollment = ((Long) camper.get(DataConstants.PASTENROLLMENT)).intValue();
                String swimTestResult = (String) camper.get(DataConstants.SWIMTESTRESULT);
                String relationToCustomer = (String) camper.get(DataConstants.RELATIONTOCUSTOMER);
                camperList.put(id, new Camper(id, firstName, lastName, allergies, birthday, primaryEmergencyContact,
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
        HashMap<UUID, Group> groups = getGroups();
        if (groups.containsKey(id))
            return groups.get(id);
        System.out.println(groups.toString());
        return null;
    }

    public static HashMap<UUID, Group> getGroups() {
        if (groupCache != null && !dirtyFlags.groupsDirty) {
            return groupCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, Group> groupList = new HashMap<>();
        try {
            JSONArray groups = (JSONArray) parser.parse(new FileReader("data/groups.json"));
            for (Object object : groups) {
                JSONObject group = (JSONObject) object;
                UUID id = UUID.fromString((String) group.get(DataConstants.ID));
                int groupSize = ((Long) group.get(DataConstants.GROUPSIZE)).intValue();
                ArrayList<Camper> campers = new ArrayList<>();
                JSONArray jCampers = (JSONArray) group.get(DataConstants.CAMPERS);
                for (Object camper : jCampers) {
                    campers.add(getCamper(UUID.fromString((String) camper)));
                }
                ArrayList<DaySchedule> schedule = new ArrayList<>();
                groupList.put(id, new Group(id, campers, groupSize, schedule));
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
        groupCache = groupList;
        return groupList;
    }

    public static Counselor getCounselor(UUID id) {
        HashMap<UUID, Counselor> counselors = getCounselors();
        if (counselors.containsKey(id))
            return counselors.get(id);
        return null;
    }

    public static HashMap<UUID, Counselor> getCounselors() {
        if (counselorCache != null && !dirtyFlags.counselorDirty) {
            return counselorCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, Counselor> counselorList = new HashMap<>();

        try {
            JSONArray counselors = (JSONArray) parser.parse(new FileReader("data/counselors.json"));
            for (Object object : counselors) {
                JSONObject counselor = (JSONObject) object;
                UUID id = UUID.fromString((String) counselor.get(DataConstants.ID));
                String email = (String) counselor.get(DataConstants.EMAIL);
                String firstName = (String) counselor.get(DataConstants.FIRSTNAME);
                String lastName = (String) counselor.get(DataConstants.LASTNAME);
                String password = (String) counselor.get(DataConstants.PASSWORD);
                ArrayList<String> allergies = new ArrayList<>();
                JSONArray allergiesJsonArray = (JSONArray) counselor.get(DataConstants.ALLERGIES);
                for (Object allergy : allergiesJsonArray) {
                    allergies.add((String) allergy);
                }
                LocalDate birthday = LocalDate.parse((String) counselor.get(DataConstants.BIRTHDAY));
                Group group = getGroup(UUID.fromString((String) counselor.get(DataConstants.GROUP)));
                JSONObject pec = (JSONObject) counselor.get(DataConstants.PRIMARYEMERGENCYCONTACT);
                Contact primaryEmergencyContact = new Contact((String) pec.get(DataConstants.FIRSTNAME),
                        (String) pec.get(DataConstants.LASTNAME), (String) pec.get(DataConstants.EMAIL),
                        (String) pec.get(DataConstants.PHONENUMBER),
                        (String) pec.get(DataConstants.RELATIONSHIP), (String) pec.get(DataConstants.ADDRESS));
                JSONObject sec = (JSONObject) counselor.get(DataConstants.SECONDARYEMERGENCYCONTACT);
                Contact secondaryEmergencyContact = new Contact((String) sec.get(DataConstants.FIRSTNAME),
                        (String) sec.get(DataConstants.LASTNAME), (String) sec.get(DataConstants.EMAIL),
                        (String) sec.get(DataConstants.PHONENUMBER),
                        (String) sec.get(DataConstants.RELATIONSHIP), (String) sec.get(DataConstants.ADDRESS));
                JSONObject pcp = (JSONObject) counselor.get(DataConstants.PRIMARYCAREPHYSICIAN);
                Contact primaryCarePhysician = new Contact((String) pcp.get(DataConstants.FIRSTNAME),
                        (String) pcp.get(DataConstants.LASTNAME), (String) pcp.get(DataConstants.EMAIL),
                        (String) pcp.get(DataConstants.PHONENUMBER),
                        (String) pcp.get(DataConstants.RELATIONSHIP), (String) pcp.get(DataConstants.ADDRESS));
                CampLocation campLocation = getCampLocation();
                counselorList.put(id,
                        new Counselor(group, allergies, birthday, email, firstName, lastName, password,
                                campLocation, primaryEmergencyContact, secondaryEmergencyContact,
                                primaryCarePhysician));
            }
        } catch (Exception exception) {
            System.out.println(exception);
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
            UUID id = UUID.fromString((String) director.get(DataConstants.ID));
            String email = (String) director.get(DataConstants.EMAIL);
            String firstName = (String) director.get(DataConstants.FIRSTNAME);
            String lastName = (String) director.get(DataConstants.LASTNAME);
            String password = (String) director.get(DataConstants.PASSWORD);
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
            UUID id = UUID.fromString((String) campLocation.get(DataConstants.ID));
            String name = (String) campLocation.get(DataConstants.NAME);
            String location = (String) campLocation.get(DataConstants.LOCATION);
            double pricePerCamper = (double) campLocation.get(DataConstants.PRICEPERCAMPER);
            CampLocation campLocationObject = new CampLocation(id, name, location, pricePerCamper);
            return campLocationObject;
        } catch (Exception exception) {

        }
        return null;
    }

    public static Week getWeek(UUID id) {
        HashMap<UUID, Week> weeks = getWeeks();
        if (weeks.containsKey(id))
            weeks.get(id);
        return null;
    }

    public static HashMap<UUID, Week> getWeeks() {
        if (weeksCache != null && !dirtyFlags.weeksDirty) {
            return weeksCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, Week> weeksList = new HashMap<>();
        try {
            JSONArray weeksArray = (JSONArray) parser.parse(new FileReader("data/weeks.json"));
            for (Object weekObject : weeksArray) {
                JSONObject weekJsonObject = (JSONObject) weekObject;
                UUID id = UUID.fromString((String) weekJsonObject.get(DataConstants.ID));
                int maxCampers = ((Long) weekJsonObject.get(DataConstants.MAXCAMPERS)).intValue();
                int currentCampers = ((Long) weekJsonObject.get(DataConstants.CURRENTCAMPERS)).intValue();
                LocalDate startOfWeek = LocalDate.parse((String) weekJsonObject.get(DataConstants.STARTOFWEEK));
                ArrayList<Group> groups = new ArrayList<>();
                JSONArray groupArray = (JSONArray) weekJsonObject.get(DataConstants.GROUPS);
                for (Object groupObject : groupArray) {
                    groups.add(getGroup(UUID.fromString((String) groupObject)));
                }
                CampLocation campLocation = getCampLocation();
                weeksList.put(id, new Week(id, maxCampers, currentCampers, startOfWeek, groups, campLocation));
            }
        } catch (Exception exception) {

        }
        weeksCache = weeksList;
        return weeksList;
    }

    public static DaySchedule getDaySchedule(UUID id) {
        HashMap<UUID, DaySchedule> daySchedules = getDaySchedules();
        if (daySchedules.containsKey(id))
            return daySchedules.get(id);
        return null;
    }

    public static HashMap<UUID, DaySchedule> getDaySchedules() {
        if (daySchedulesCache != null && !dirtyFlags.daySchedulesDirty) {
            return daySchedulesCache;
        }
        JSONParser parser = new JSONParser();
        HashMap<UUID, DaySchedule> dayScheduleList = new HashMap<>();
        try {
            JSONArray dayScheduleArray = (JSONArray) parser.parse(new FileReader("data/daySchedule.json"));
            for (Object dayScheduleObject : dayScheduleArray) {
                JSONObject dayScheduleJsonObject = (JSONObject) dayScheduleObject;
                UUID id = UUID.fromString((String) dayScheduleJsonObject.get(DataConstants.ID));
                ArrayList<Activity> currentActivities = new ArrayList<>();
                JSONArray activities = (JSONArray) dayScheduleJsonObject.get(DataConstants.CURRENTACTIVITIES);
                for (Object aObject : activities) {
                    JSONObject aJsonObject = (JSONObject) aObject;
                    currentActivities.add(getActivity(UUID.fromString((String) aJsonObject.get(DataConstants.ID))));
                }
                Week week = null;
                LocalDate day = LocalDate.parse((String) dayScheduleJsonObject.get(DataConstants.DAY));
                dayScheduleList.put(id, new DaySchedule(id, currentActivities, week, day));
            }
        } catch (Exception exception) {

        }
        daySchedulesCache = dayScheduleList;
        return dayScheduleList;
    }

    public static HashMap<UUID, User> getUsers() {
        HashMap<UUID, User> users = new HashMap<>();
        for (Customer customer : getCustomers().values()) {
            users.put(customer.id, customer);
        }
        for (Counselor counselor : getCounselors().values()) {
            users.put(counselor.id, counselor);
        }
        users.put(getDirector().getId(), getDirector());
        return users;
    }
}
