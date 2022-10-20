// Copyright Row 3

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

    public static void main(String[] args) {
        saveActivities(DataReader.getActivities());
    }

    public static boolean saveCustomers(HashMap<UUID, Customer> customers) {
        DataReader.setDirtyFlag(DataReader.CUSTOMERSDIRTY);
        try {
            FileWriter file = new FileWriter("data/customersTest.json");
            JSONArray customersJsonArray = new JSONArray();
            for (Customer customer : customers.values()) {
                JSONObject customerJsonObject = new JSONObject();
                customerJsonObject.put(DataConstants.ID, customer.getId().toString());
                customerJsonObject.put(DataConstants.EMAIL, customer.getEmail());
                customerJsonObject.put(DataConstants.FIRSTNAME, customer.getFirstName());
                customerJsonObject.put(DataConstants.LASTNAME, customer.getLastName());
                customerJsonObject.put(DataConstants.PASSWORD, customer.getPassword());
                customerJsonObject.put(DataConstants.CAMPLOCATION, "2abbae6f-07eb-4e30-ba07-ec8fc05a503b");
                customerJsonObject.put(DataConstants.TYPEOFUSER, "CUSTOMER");
                JSONArray campersJsonArray = new JSONArray();
                for (Camper camper : customer.getCampers()) {
                    campersJsonArray.add(camper.getId().toString());
                }
                customerJsonObject.put(DataConstants.CAMPERS, campersJsonArray);
                customersJsonArray.add(customerJsonObject);
            }
            file.write(customersJsonArray.toJSONString());
            file.close();
        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
        return true;
    }

    public static boolean editCustomer(UUID id, Customer customer) {
        HashMap<UUID, Customer> modifiedCustomers = DataReader.getCustomers();
        if (!modifiedCustomers.containsKey(id))
            return false;
        modifiedCustomers.put(id, customer);
        return saveCustomers(modifiedCustomers);
    }

    public static boolean removeCustomer(UUID id) {
        HashMap<UUID, Customer> modifiedCustomers = DataReader.getCustomers();
        if (!modifiedCustomers.containsKey(id))
            return false;
        modifiedCustomers.remove(id);
        return saveCustomers(modifiedCustomers);
    }

    public static boolean saveActivities(HashMap<UUID, Activity> activites) {
        DataReader.setDirtyFlag(DataReader.ACTIVITIESDIRTY);
        try {
            FileWriter file = new FileWriter("data/activitiesTest.json");
            JSONArray activitiesJsonArray = new JSONArray();
            for (Activity activity : activites.values()) {
                JSONObject activityJsonObject = new JSONObject();
                activityJsonObject.put(DataConstants.ID, activity.getId().toString());
                activityJsonObject.put(DataConstants.NAME, activity.getName());
                activityJsonObject.put(DataConstants.LOCATION, activity.getLocation());
                activityJsonObject.put(DataConstants.DESCRIPTION, activity.getDescription());
                activitiesJsonArray.add(activityJsonObject);
            }
            file.write(activitiesJsonArray.toJSONString());
            file.close();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static boolean saveWeeks(ArrayList<Week> weeks) {
        return false;
    }

    public static boolean saveCampLocation(CampLocation campLocation) {
        return false;
    }
}
