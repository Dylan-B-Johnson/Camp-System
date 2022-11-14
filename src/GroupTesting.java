import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupTesting {

        private static String daySchedules;
        private static Scanner reader;
        private static Week week;
        private static LocalDate day;

        private ArrayList<String> allergies = new ArrayList<String>();
        private Contact primaryEmergencyContact;
        private Contact secondaryEmergencyContact;
        private Contact primaryCarePhysician;
        private int groupSize;
        private Counselor counselor;
        private ArrayList<Camper> campers = new ArrayList<Camper>();

        @BeforeEach
        public void setup() {
                counselor = new Counselor(allergies, LocalDate.now().minusYears(18), "email", "bob", "test",
                                "password", CampLocation.getCampLocation(), primaryEmergencyContact,
                                secondaryEmergencyContact,
                                primaryCarePhysician);
                primaryEmergencyContact = new Contact("Mitchell", "Pressely", "mitchiep@gmail.com",
                                "695-396-2956", "Father",
                                "8912 Greene St.");
                secondaryEmergencyContact = new Contact("Jeffrey", "Johnson", "jjohn@gmail.com",
                                "695-495-2059", "Brother",
                                "8912 Greene St.");
                primaryCarePhysician = new Contact("Ron", "Rhymer", "ron@pak.org", "811-234-2333",
                                "PCP",
                                "Pediatric Association of Knoxbville, 23 College Drive");
                allergies = new ArrayList<String>();
                groupSize = 0;
        }

        @AfterEach
        public void tearDown() {
                campers.clear();
        }

        @Test
        public void addValidCamper() {
                Group testGroup = new Group(campers, groupSize, new ArrayList<DaySchedule>(),
                                counselor);
                Camper testCamper = new Camper("bob", "jones", allergies, LocalDate.now().minusYears(12),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician,
                                0, "good swimmer", "son");
                testGroup.addCamper(testCamper);
                assertEquals(1, testGroup.campers.size());
        }

        @Test
        public void addInvalidCamper() {
                Group testGroup = new Group(campers, groupSize, new ArrayList<DaySchedule>(),
                                counselor);
                Camper testCamper = new Camper("bob", "jones", allergies, LocalDate.now().minusYears(30),
                                primaryEmergencyContact,
                                secondaryEmergencyContact, primaryCarePhysician, 0,
                                "good swimmer", "son");
                testGroup.addCamper(testCamper);
                assertEquals(0, testGroup.campers.size());
        }

        @Test
        public void addNullCamper() {
                Group testGroup = new Group(campers, groupSize, new ArrayList<DaySchedule>(),
                                counselor);
                Camper testCamper = null;
                testGroup.addCamper(testCamper);
                assertEquals(0, testGroup.campers.size());
        }

        @Test
        public void removeValidCamper() {
                Group testGroup = new Group(campers, groupSize, new ArrayList<DaySchedule>(),
                                counselor);
                Camper testCamper = new Camper("bob", "jones", allergies, LocalDate.now().minusYears(12),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician,
                                0, "good swimmer", "son");
                testGroup.removeCamper(testCamper);
                assertEquals(0, testGroup.campers.size());
        }

        @Test
        public void removeInvalidCamper() {
                Group testGroup = new Group(campers, groupSize, new ArrayList<DaySchedule>(),
                                counselor);
                Camper testCamper = new Camper("bob", "jones", allergies, LocalDate.now().minusYears(30),
                                primaryEmergencyContact,
                                secondaryEmergencyContact, primaryCarePhysician, 0, "good swimmer", "son");
                assertEquals(0, testGroup.campers.size());
        }

        @Test
        public void removeNullCamper() {
                Group testGroup = new Group(campers, groupSize, new ArrayList<DaySchedule>(),
                                counselor);
                Camper testCamper = null;
                testGroup.removeCamper(testCamper);
                assertEquals(0, testGroup.campers.size());
        }
}