// Copyright row 3

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataReader {

    public static void main(String[] args) {
        getCamper(UUID.fromString("35f810c6-ed26-42ec-a423-1db01478251f")).getFirstName();
    }

    public static ArrayList<Customer> getCustomers() {
        JSONParser parser = new JSONParser();
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        try {
            JSONArray customers = (JSONArray) parser.parse(new FileReader("Data/customers.json"));
            for (Object object : customers) {
                JSONObject user = (JSONObject) object;
                String id = (String) user.get("id");
                String email = (String) user.get("email");
                String firstName = (String) user.get("firstName");
                String lastName = (String) user.get("lastName");
                ArrayList<Camper> campers = new ArrayList<Camper>();
                customerList
                        .add(new Customer(UUID.fromString(id), email, firstName, lastName, lastName, null, campers));
            }
        } catch (Exception exception) {

        }
        return customerList;
    }

    public static ArrayList<Activity> getActivities() {
        JSONParser parser = new JSONParser();
        ArrayList<Activity> activities = new ArrayList<Activity>();

        try {
            JSONArray jActivities = (JSONArray) parser.parse(new FileReader("Data/activities.json"));
            for (Object object : jActivities) {
                JSONObject activity = (JSONObject) object;
                String id = (String) activity.get("id");
                String name = (String) activity.get("name");
                String location = (String) activity.get("location");
                String description = (String) activity.get("description");
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
            JSONArray campers = (JSONArray) parser.parse(new FileReader("Data/campers.json"));
            for (Object object : campers) {
                JSONObject camper = (JSONObject) object;
                UUID id = UUID.fromString((String) camper.get("id"));
                String firstName = (String) camper.get("firstName");
                String lastName = (String) camper.get("lastName");
                ArrayList<String> allergies = new ArrayList<String>();
                JSONArray jsonAllergies = (JSONArray) camper.get("allergies");
                for (Object o : jsonAllergies) {
                    allergies.add((String) o);
                }
                LocalDate birthday = LocalDate.now();
                JSONObject pec = (JSONObject) camper.get("primaryEmergencyContact");
                Contact primaryEmergencyContact = new Contact((String) pec.get("firstName"),
                        (String) pec.get("lastName"), (String) pec.get("email"), (String) pec.get("phoneNumber"),
                        (String) pec.get("relationship"), (String) pec.get("address"));
                Contact secondaryEmergencyContact = null;
                Contact primaryCarePhysician = null;
                int pastEnrollment = ((Long) camper.get("pastEnrollment")).intValue();
                String swimTestResult = (String) camper.get("swimTestResult");
                String relationToCustomer = (String) camper.get("relationToCustomer");
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
            JSONArray groups = (JSONArray) parser.parse(new FileReader("Data/groups.json"));
            for (Object object : groups) {
                JSONObject group = (JSONObject) object;
                UUID id = UUID.fromString((String) group.get("id"));
                int groupSize = ((Long) group.get("groupSize")).intValue();
                ArrayList<Camper> campers = new ArrayList<>();
                Counselor counselor = null;
                ArrayList<DaySchedule> schedule = new ArrayList<>();
                groupList.add(new Group(id, campers, counselor, groupSize, schedule));
            }
        } catch (Exception exception) {

        }
        return groupList;
    }
}
