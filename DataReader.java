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
        ArrayList<Customer> customers = getAllCustomers();
        for (Customer cus : customers) {
            System.out.println(cus.getName());
        }
    }

    public static ArrayList<Customer> getAllCustomers() {
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
                customerList.add(new Customer(email, firstName, lastName, lastName, null, campers));
            }
        } catch (Exception exception) {

        }
        return customerList;
    }

    public static ArrayList<Activity> getAllActivites() {
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

    public static ArrayList<Week> getAllWeeks() {
        return null;
    }

    public static CampLocation getCampLocation() {
        return null;
    }

    public static Camper getCamper(UUID id) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray campers = (JSONArray) parser.parse(new FileReader("Data/campers.json"));
            for (Object object : campers) {
                JSONObject camper = (JSONObject) object;
                if (UUID.fromString((String) camper.get("id")).compareTo(id) == 0) {
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
                    return new Camper(id, firstName, lastName, allergies, birthday, primaryEmergencyContact,
                            secondaryEmergencyContact, primaryCarePhysician, pastEnrollment, swimTestResult,
                            relationToCustomer);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
