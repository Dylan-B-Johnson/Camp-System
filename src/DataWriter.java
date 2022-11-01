// Copyright Row 3

import java.io.FileWriter;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter {

    public static boolean saveCustomers(HashMap<UUID, Customer> customers) {
        DataReader.setDirtyFlag(DataReader.CUSTOMERSDIRTY);
        try {
            FileWriter file = new FileWriter("data/customers.json");
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
                JSONObject contact = new JSONObject();
                contact.put(DataConstants.EMAIL, customer.getContactInfo().getEmail());
                contact.put(DataConstants.FIRSTNAME, customer.getContactInfo().getFirstName());
                contact.put(DataConstants.LASTNAME, customer.getContactInfo().getLastName());
                contact.put(DataConstants.PHONENUMBER, customer.getContactInfo().getPhoneNum());
                contact.put(DataConstants.RELATIONSHIP, customer.getContactInfo().getRelationship());
                contact.put(DataConstants.ADDRESS, customer.getContactInfo().getAddress());
                customerJsonObject.put(DataConstants.CONTACTINFO, contact);
                customersJsonArray.add(customerJsonObject);
            }
            file.write(customersJsonArray.toJSONString());
            file.close();
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println("bruh");
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
        if (!modifiedCustomers.containsKey(id) || !(customer.getId().compareTo(id) == 0))
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
            FileWriter file = new FileWriter("data/activities.json");
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
            System.out.println(exception);
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
        if (!modifiedActivities.containsKey(id) || !(activity.getId().compareTo(id) == 0))
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

    public static boolean saveWeeks(HashMap<UUID, Week> weeks) {
        DataReader.setDirtyFlag(DataReader.WEEKSDIRTY);
        try {
            FileWriter file = new FileWriter("data/weeks.json");
            JSONArray weeksJsonArray = new JSONArray();
            for (Week week : weeks.values()) {
                JSONObject weekJsonObject = new JSONObject();
                weekJsonObject.put(DataConstants.ID, week.getId().toString());
                weekJsonObject.put(DataConstants.MAXCAMPERS, week.getMaxCampers());
                weekJsonObject.put(DataConstants.STARTOFWEEK, week.getStartOfWeek().toString());
                weekJsonObject.put(DataConstants.CURRENTCAMPERS, week.getCurrentCampers());
                JSONArray groupJsonArray = new JSONArray();
                for (Group group : week.getGroups()) {
                    groupJsonArray.add(group.getId().toString());
                }
                weekJsonObject.put(DataConstants.GROUPS, groupJsonArray);
                weekJsonObject.put(DataConstants.THEME, week.getTheme());
                weeksJsonArray.add(weekJsonObject);
            }
            file.write(weeksJsonArray.toJSONString());
            file.close();
        } catch (Exception e) {
            System.out.print(e);
            return false;
        }
        return true;
    }

    public static boolean createWeek(Week week) {
        HashMap<UUID, Week> weeksHashMap = DataReader.getWeeks();
        weeksHashMap.put(week.getId(), week);
        return saveWeeks(weeksHashMap);
    }

    public static boolean deleteWeek(UUID id) {
        HashMap<UUID, Week> weeksHashMap = DataReader.getWeeks();
        if (!weeksHashMap.containsKey(id))
            return false;
        weeksHashMap.remove(id);
        return saveWeeks(weeksHashMap);
    }

    public static boolean updateWeek(UUID id, Week week) {
        HashMap<UUID, Week> weeksHashMap = DataReader.getWeeks();
        if (!weeksHashMap.containsKey(id) || !(week.getId().compareTo(id) == 0))
            return false;
        weeksHashMap.put(id, week);
        return saveWeeks(weeksHashMap);
    }

    private static boolean saveCampLocation(CampLocation campLocation) {
        DataReader.setDirtyFlag(DataReader.CAMPLOCATIONDIRTY);
        try {
            FileWriter file = new FileWriter("data/campLocation.json");
            JSONObject campLocationJsonObject = new JSONObject();
            campLocationJsonObject.put(DataConstants.ID, campLocation.getId().toString());
            campLocationJsonObject.put(DataConstants.NAME, campLocation.getName());
            campLocationJsonObject.put(DataConstants.LOCATION, campLocation.getLocation());
            campLocationJsonObject.put(DataConstants.PRICEPERCAMPER, campLocation.getPricePerCamper());
            file.write(campLocationJsonObject.toJSONString());
            file.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static boolean updateCampLocation(CampLocation campLocation) {
        return saveCampLocation(campLocation);
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

    public static boolean updateCounselor(UUID id, Counselor counselor) {
        HashMap<UUID, Counselor> counselorHashMap = DataReader.getCounselors();
        if (!counselorHashMap.containsKey(id) || !(counselor.getId().compareTo(id) == 0))
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

    public static boolean updateDirector(Director director) {
        return saveDirector(director);
    }

    private static boolean saveCampers(HashMap<UUID, Camper> campers) {
        DataReader.setDirtyFlag(DataReader.CAMPERSDIRTY);
        try {
            FileWriter file = new FileWriter("data/campers.json");
            JSONArray campersJsonArray = new JSONArray();
            for (Camper camper : campers.values()) {
                JSONObject camperJsonObject = new JSONObject();
                camperJsonObject.put(DataConstants.ID, camper.getId().toString());
                camperJsonObject.put(DataConstants.FIRSTNAME, camper.getFirstName());
                camperJsonObject.put(DataConstants.LASTNAME, camper.getLastName());
                JSONArray allergyJsonArray = new JSONArray();
                for (String allergyString : camper.getAllergies()) {
                    allergyJsonArray.add(allergyString);
                }
                camperJsonObject.put(DataConstants.ALLERGIES, allergyJsonArray);
                JSONObject pec = new JSONObject();
                pec.put(DataConstants.EMAIL, camper.getPrimaryCarePhysician().getEmail());
                pec.put(DataConstants.FIRSTNAME, camper.getPrimaryCarePhysician().getFirstName());
                pec.put(DataConstants.LASTNAME, camper.getPrimaryCarePhysician().getLastName());
                pec.put(DataConstants.PHONENUMBER, camper.getPrimaryCarePhysician().getPhoneNum());
                pec.put(DataConstants.RELATIONSHIP, camper.getPrimaryCarePhysician().getRelationship());
                pec.put(DataConstants.ADDRESS, camper.getPrimaryCarePhysician().getAddress());
                camperJsonObject.put(DataConstants.PRIMARYEMERGENCYCONTACT, pec);
                JSONObject sec = new JSONObject();
                sec.put(DataConstants.EMAIL, camper.getSecondaryEmergencyContact().getEmail());
                sec.put(DataConstants.FIRSTNAME, camper.getSecondaryEmergencyContact().getFirstName());
                sec.put(DataConstants.LASTNAME, camper.getSecondaryEmergencyContact().getLastName());
                sec.put(DataConstants.PHONENUMBER, camper.getSecondaryEmergencyContact().getPhoneNum());
                sec.put(DataConstants.RELATIONSHIP, camper.getSecondaryEmergencyContact().getRelationship());
                sec.put(DataConstants.ADDRESS, camper.getSecondaryEmergencyContact().getAddress());
                camperJsonObject.put(DataConstants.SECONDARYEMERGENCYCONTACT, sec);
                JSONObject pcp = new JSONObject();
                pcp.put(DataConstants.EMAIL, camper.getPrimaryCarePhysician().getEmail());
                pcp.put(DataConstants.FIRSTNAME, camper.getPrimaryCarePhysician().getFirstName());
                pcp.put(DataConstants.LASTNAME, camper.getPrimaryCarePhysician().getLastName());
                pcp.put(DataConstants.PHONENUMBER, camper.getPrimaryCarePhysician().getPhoneNum());
                pcp.put(DataConstants.RELATIONSHIP, camper.getPrimaryCarePhysician().getRelationship());
                pcp.put(DataConstants.ADDRESS, camper.getPrimaryCarePhysician().getAddress());
                camperJsonObject.put(DataConstants.PRIMARYCAREPHYSICIAN, pcp);
                camperJsonObject.put(DataConstants.BIRTHDAY, camper.getBirthday().toString());
                camperJsonObject.put(DataConstants.PASTENROLLMENT, camper.getPastEnrollment());
                camperJsonObject.put(DataConstants.SWIMTESTRESULT, camper.getSwimTestResult());
                camperJsonObject.put(DataConstants.RELATIONTOCUSTOMER, camper.getRelationshipToCustomer());
                campersJsonArray.add(camperJsonObject);
            }
            file.write(campersJsonArray.toJSONString());
            file.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static boolean createCamper(Camper camper) {
        HashMap<UUID, Camper> campersHashMap = DataReader.getCampers();
        campersHashMap.put(camper.getId(), camper);
        return saveCampers(campersHashMap);
    }

    public static boolean updateCamper(UUID id, Camper camper) {
        HashMap<UUID, Camper> camperHashMap = DataReader.getCampers();
        if (!camperHashMap.containsKey(id) || !(camper.getId().compareTo(id) == 0))
            return false;
        camperHashMap.put(id, camper);
        return saveCampers(camperHashMap);
    }

    public static boolean deleteCamper(UUID id) {
        HashMap<UUID, Camper> camperHashMap = DataReader.getCampers();
        if (!camperHashMap.containsKey(id))
            return false;
        camperHashMap.remove(id);
        return saveCampers(camperHashMap);
    }

    private static boolean saveDaySchedules(HashMap<UUID, DaySchedule> daySchedules) {
        DataReader.setDirtyFlag(DataReader.DAYSCHEDULESDIRTY);
        try {
            FileWriter file = new FileWriter("data/daySchedules.json");
            JSONArray dayScheduleJsonArray = new JSONArray();
            for (DaySchedule daySchedule : daySchedules.values()) {
                JSONObject dayScheduleJsonObject = new JSONObject();
                dayScheduleJsonObject.put(DataConstants.ID, daySchedule.getId().toString());
                JSONArray currentActivitiesJsonArray = new JSONArray();
                for (Activity activity : daySchedule.getCurrentAcitivities()) {
                    currentActivitiesJsonArray.add(activity.getId().toString());
                }
                dayScheduleJsonObject.put(DataConstants.CURRENTACTIVITIES, currentActivitiesJsonArray);
                dayScheduleJsonObject.put(DataConstants.DAY, daySchedule.getDay().toString());
                dayScheduleJsonArray.add(dayScheduleJsonObject);
            }
            file.write(dayScheduleJsonArray.toJSONString());
            file.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static boolean createDaySchedule(DaySchedule daySchedule) {
        HashMap<UUID, DaySchedule> dayScheduleHashMap = DataReader.getDaySchedules();
        dayScheduleHashMap.put(daySchedule.getId(), daySchedule);
        return saveDaySchedules(dayScheduleHashMap);
    }

    public static boolean updateDaySchedule(UUID id, DaySchedule daySchedule) {
        HashMap<UUID, DaySchedule> dayScheduleHashMap = DataReader.getDaySchedules();
        if (!dayScheduleHashMap.containsKey(id) || !(daySchedule.getId().compareTo(id) == 0))
            return false;
        dayScheduleHashMap.put(id, daySchedule);
        return saveDaySchedules(dayScheduleHashMap);
    }

    public static boolean deleteDaySchedule(UUID id) {
        HashMap<UUID, DaySchedule> dayScheduleHashMap = DataReader.getDaySchedules();
        if (!dayScheduleHashMap.containsKey(id))
            return false;
        dayScheduleHashMap.remove(id);
        return saveDaySchedules(dayScheduleHashMap);
    }

    private static boolean saveGroups(HashMap<UUID, Group> groups) {
        DataReader.setDirtyFlag(DataReader.GROUPSDIRTY);

        try {
            FileWriter file = new FileWriter("data/groups.json");

            JSONArray groupsJsonArray = new JSONArray();
            for (Group group : groups.values()) {
                JSONObject groupJsonObject = new JSONObject();
                groupJsonObject.put(DataConstants.ID, group.getId().toString());
                groupJsonObject.put(DataConstants.GROUPSIZE, group.getGroupSize());
                JSONArray campersJsonArray = new JSONArray();
                for (Camper camper : group.getCampers()) {
                    campersJsonArray.add(camper.getId().toString());
                }
                groupJsonObject.put(DataConstants.CAMPERS, campersJsonArray);
                JSONArray scheduleJsonArray = new JSONArray();
                for (DaySchedule daySchedule : group.getSchedule()) {
                    scheduleJsonArray.add(daySchedule.getId().toString());
                }
                groupJsonObject.put(DataConstants.SCHEDULE, scheduleJsonArray);
                groupsJsonArray.add(groupJsonObject);
            }
            file.write(groupsJsonArray.toJSONString());
            file.close();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static boolean createGroup(Group group) {
        HashMap<UUID, Group> groupsHashMap = DataReader.getGroups();
        groupsHashMap.put(group.getId(), group);
        return saveGroups(groupsHashMap);
    }

    public static boolean updateGroup(UUID id, Group group) {
        HashMap<UUID, Group> groupsHashMap = DataReader.getGroups();
        if (!groupsHashMap.containsKey(id) || !(group.getId().compareTo(id) == 0))
            return false;
        groupsHashMap.put(id, group);
        return saveGroups(groupsHashMap);
    }

    public static boolean deleteGroup(UUID id) {
        HashMap<UUID, Group> groupsHashMap = DataReader.getGroups();
        if (!groupsHashMap.containsKey(id))
            return false;
        groupsHashMap.remove(id);
        return saveGroups(groupsHashMap);
    }
}
