import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


/**
 * @author Caleb Henry
 */
public class DayScheduleTesting {
    private ArrayList<String> allergies;
    private Contact primaryEmergencyContact;
    private Contact secondaryEmergencyContact;
    private Contact primaryCarePhysician;

    @BeforeEach
    public void setup(){
    primaryEmergencyContact = new Contact("Ryan", "Harp", "harr@gmail.com",
            "657-234-2231", "Friend",
            "55 Hoback St.");
    secondaryEmergencyContact = new Contact("Thomas", "Mandrus", "mandrusT@gmail.com",
            "456-586-2231", "Friend",
            "76 Hoback St.");
    primaryCarePhysician = new Contact("Ron", "Rhymer", "ron@pak.org", "811-234-2333",
            "PCP",
            "Pediatric Association of Knoxbville, 23 College Drive");
    allergies = new ArrayList<String>();    
    }
    
    @AfterEach
    public void tearDown(){

    }

    @Test
    public void createValidRandomDaySchedule(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(null, testWeek, LocalDate.now().plusDays(3));
        schedule = schedule.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        assertEquals(6, schedule.getActivities().size());
    }

    @Test
    public void createRandomDayScheduleWithNullWeek(){
        Week testWeek = null;
        DaySchedule schedule = new DaySchedule(null, testWeek, LocalDate.now().plusDays(3));
        schedule = schedule.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        assertNull(schedule);
    }

    @Test
    public void createRandomDayScheduleOutOfRangeDay(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(null, testWeek, LocalDate.now());
        schedule = schedule.getRandomDaySchedule(testWeek, LocalDate.now());
        assertNull(schedule);
    }

    @Test
    public void addValidActivity(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(new ArrayList<Activity>(), testWeek, LocalDate.now());
        schedule.addToSchedule(ActivitiesList.getActivities().get(0).getName());
        assertTrue(schedule.getActivities().contains(ActivitiesList.getActivities().get(0)));
    }

    @Test
    public void addInvalidActivity(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(new ArrayList<Activity>(), testWeek, LocalDate.now());
        schedule.addToSchedule("hello there");
        assertEquals(0, schedule.getActivities().size());
    }

    @Test
    public void replaceWithValidActivitiy(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(null, testWeek, LocalDate.now().plusDays(3));
        schedule = schedule.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        Activity priorActivity = schedule.getActivities().get(0);
        schedule.replaceActivity(0, ActivitiesList.getActivities().get(0).getName());
        assertNotEquals(priorActivity, schedule.getActivities().get(0));
    }    

    @Test
    public void replaceWithInvalidActivitiy(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(null, testWeek, LocalDate.now().plusDays(3));
        schedule = schedule.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        Activity priorActivity = null;
        schedule.replaceActivity(0, ActivitiesList.getActivities().get(0).getName());
        assertNotEquals(priorActivity, schedule.getActivities().get(0));
    }   

    @Test
    public void replaceWithInvalidSlot(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule = new DaySchedule(null, testWeek, LocalDate.now().plusDays(3));
        schedule = schedule.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        schedule.replaceActivity(7, ActivitiesList.getActivities().get(0).getName());
        assertEquals(6, schedule.getActivities().size());
    }   

    @Test
    public void verifyValidActivityAvailablility(){
        Week testWeek = new Week(0, 0, LocalDate.now().plusDays(1), CampLocation.getCampLocation(), "test");
        DaySchedule schedule1 = new DaySchedule(null, testWeek, LocalDate.now().plusDays(3));
        schedule1 = schedule1.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        ArrayList<DaySchedule> schedule1list = new ArrayList<DaySchedule>();
        schedule1list.add(schedule1);
        DaySchedule schedule2 = schedule1.getRandomDaySchedule(testWeek, LocalDate.now().plusDays(3));
        ArrayList<DaySchedule> schedule2list = new ArrayList<DaySchedule>();
        schedule2list.add(schedule2);
        Group testGroup1 = new Group(null, 0, schedule1list, null);
        Group testGroup2 = new Group(null, 0, schedule2list, null);
        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(testGroup1);
        groups.add(testGroup2);
        testWeek.setGroups(groups);
        assertFalse(schedule1.verifyActivityAvailablility(schedule2.getActivities().get(0).getName(), 0));
}
}
