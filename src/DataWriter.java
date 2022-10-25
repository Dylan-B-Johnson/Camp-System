// Copyright Row 3

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

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

    public static boolean createCustomer(Customer customer) {
        HashMap<UUID, Customer> modifiedCustomers = DataReader.getCustomers();
        modifiedCustomers.put(customer.getId(), customer);
        return saveCustomers(modifiedCustomers);
    }

    public static boolean updateCustomer(UUID id, Customer customer) {
        HashMap<UUID, Customer> modifiedCustomers = DataReader.getCustomers();
        if (!modifiedCustomers.containsKey(id))
            return false;
        modifiedCustomers.put(id, customer);
        return saveCustomers(modifiedCustomers);
    }

    public static boolean deleteCustomer(UUID id) {
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

    public static boolean createActivity(Activity activity) {
        HashMap<UUID, Activity> modifiedActivities = DataReader.getActivities();
        modifiedActivities.put(activity.getId(), activity);
        return saveActivities(modifiedActivities);
    }

    public static boolean updateActivity(UUID id, Activity activity) {
        HashMap<UUID, Activity> modifiedActivities = DataReader.getActivities();
        if (!modifiedActivities.containsKey(id))
            return false;
        modifiedActivities.put(id, activity);
        return saveActivities(modifiedActivities);
    }

    public static boolean deleteActivity(UUID id) {
        HashMap<UUID, Activity> modifiedActivities = DataReader.getActivities();
        if (!modifiedActivities.containsKey(id))
            return false;
        modifiedActivities.remove(id);
        return saveActivities(modifiedActivities);
    }

    public static boolean saveWeeks(ArrayList<Week> weeks) {
        return false;
    }

    public static boolean saveCampLocation(CampLocation campLocation) {
        return false;
    }

    private static boolean saveCounselors(HashMap<UUID, Counselor> counselors) {
        DataReader.setDirtyFlag(DataReader.COUNSELORDIRTY);
        try {
            FileWriter file = new FileWriter("data/counselors.json");
            JSONArray counselorsJsonArray = new JSONArray();
            for (Counselor counselor : counselors.values()) {
                JSONObject counselorJsonObject = new JSONObject();
                counselorJsonObject.put(DataConstants.ID, counselor.getId().toString());
                counselorJsonObject.put(DataConstants.EMAIL, counselor.getEmail());
                counselorJsonObject.put(DataConstants.FIRSTNAME, counselor.getFirstName());
                counselorJsonObject.put(DataConstants.LASTNAME, counselor.getLastName());
                counselorJsonObject.put(DataConstants.PASSWORD, counselor.getPassword());
                counselorJsonObject.put(DataConstants.TYPEOFUSER, counselor.getTypeOfUser().toString());
                counselorJsonObject.put(DataConstants.GROUP, counselor.getGroup().getId().toString());
                counselorJsonObject.put(DataConstants.BIRTHDAY, counselor.getBirthday().toString());
                JSONArray allergyJsonArray = new JSONArray();
                for (String allergyString : counselor.getAllergies()) {
                    allergyJsonArray.add(allergyString);
                }
                counselorJsonObject.put(DataConstants.ALLERGIES, allergyJsonArray);
                JSONObject pec = new JSONObject();
                pec.put(DataConstants.EMAIL, counselor.getPrimaryCarePhysician().getEmail());
                pec.put(DataConstants.FIRSTNAME, counselor.getPrimaryCarePhysician().getFirstName());
                pec.put(DataConstants.LASTNAME, counselor.getPrimaryCarePhysician().getLastName());
                pec.put(DataConstants.PHONENUMBER, counselor.getPrimaryCarePhysician().getPhoneNum());
                pec.put(DataConstants.RELATIONSHIP, counselor.getPrimaryCarePhysician().getRelationship());
                pec.put(DataConstants.ADDRESS, counselor.getPrimaryCarePhysician().getAddress());
                counselorJsonObject.put(DataConstants.PRIMARYEMERGENCYCONTACT, pec);
                JSONObject sec = new JSONObject();
                sec.put(DataConstants.EMAIL, counselor.getSecondaryEmergencyContact().getEmail());
                sec.put(DataConstants.FIRSTNAME, counselor.getSecondaryEmergencyContact().getFirstName());
                sec.put(DataConstants.LASTNAME, counselor.getSecondaryEmergencyContact().getLastName());
                sec.put(DataConstants.PHONENUMBER, counselor.getSecondaryEmergencyContact().getPhoneNum());
                sec.put(DataConstants.RELATIONSHIP, counselor.getSecondaryEmergencyContact().getRelationship());
                sec.put(DataConstants.ADDRESS, counselor.getSecondaryEmergencyContact().getAddress());
                counselorJsonObject.put(DataConstants.SECONDARYEMERGENCYCONTACT, sec);
                JSONObject pcp = new JSONObject();
                pcp.put(DataConstants.EMAIL, counselor.getPrimaryCarePhysician().getEmail());
                pcp.put(DataConstants.FIRSTNAME, counselor.getPrimaryCarePhysician().getFirstName());
                pcp.put(DataConstants.LASTNAME, counselor.getPrimaryCarePhysician().getLastName());
                pcp.put(DataConstants.PHONENUMBER, counselor.getPrimaryCarePhysician().getPhoneNum());
                pcp.put(DataConstants.RELATIONSHIP, counselor.getPrimaryCarePhysician().getRelationship());
                pcp.put(DataConstants.ADDRESS, counselor.getPrimaryCarePhysician().getAddress());
                counselorJsonObject.put(DataConstants.PRIMARYCAREPHYSICIAN, pcp);
                counselorsJsonArray.add(counselorJsonObject);
            }
            file.write(counselorsJsonArray.toJSONString());
            file.close();
        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
        return true;
    }

    public static boolean createCounselor(Counselor counselor) {
        HashMap<UUID, Counselor> counselorHashMap = DataReader.getCounselors();
        counselorHashMap.put(counselor.getId(), counselor);
        return saveCounselors(counselorHashMap);
    }

    public static boolean editCounselor(UUID id, Counselor counselor) {
        HashMap<UUID, Counselor> counselorHashMap = DataReader.getCounselors();
        if (!counselorHashMap.containsKey(id))
            return false;
        counselorHashMap.put(id, counselor);
        return saveCounselors(counselorHashMap);
    }

    public static boolean deleteCounselor(UUID id) {
        HashMap<UUID, Counselor> counselorHashMap = DataReader.getCounselors();
        if (!counselorHashMap.containsKey(id))
            return false;
        counselorHashMap.remove(id);
        return saveCounselors(counselorHashMap);
    }

    public static boolean saveDirector(Director director) {
        DataReader.setDirtyFlag(DataReader.DIRECTORDIRTY);
        try {
            FileWriter file = new FileWriter("data/director.json");
            JSONObject directorJsonObject = new JSONObject();
            directorJsonObject.put(DataConstants.ID, director.getId().toString());
            directorJsonObject.put(DataConstants.EMAIL, director.getEmail());
            directorJsonObject.put(DataConstants.FIRSTNAME, director.getFirstName());
            directorJsonObject.put(DataConstants.LASTNAME, director.getLastName());
            directorJsonObject.put(DataConstants.PASSWORD, director.getPassword());
            directorJsonObject.put(DataConstants.CAMPLOCATION, director.getCampLocation().getId().toString());
            directorJsonObject.put(DataConstants.TYPEOFUSER, "DIRECTOR");
            file.write(directorJsonObject.toJSONString());
            file.close();
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public static boolean editDirector(Director director) {
        return saveDirector(director);
    }
}
