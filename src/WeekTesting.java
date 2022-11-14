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

//@author Collin Remer

public class WeekTesting {

        private ArrayList<String> allergies = new ArrayList<String>();
        private Contact primaryEmergencyContact;
        private Contact secondaryEmergencyContact;
        private Contact primaryCarePhysician;
        private String firstName;
        private String lastName;
        private CampLocation campLocation;
        private int currentCampers;

        @BeforeEach
        public void setup() {

        }

        @AfterEach
        public void tearDown() {
                currentCampers = 0;
        }

        @Test
        public void canRegisterValidCamper() {

                Camper testCamper = new Camper(firstName, lastName, allergies, LocalDate.now().minusYears(12),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0,
                                "good swimmer", "son");
                Week testWeek = new Week(10, 5, LocalDate.now(),
                                campLocation, "test");

                boolean canRegister = testWeek.canRegisterCamper(testCamper);

                assertTrue(canRegister);
        }

        @Test
        public void canRegisterInvalidCamper() {

                Camper testCamper = new Camper(firstName, lastName, allergies, LocalDate.now().minusYears(30),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0,
                                "good swimmer", "son");
                Week testWeek = new Week(10, 5, LocalDate.now().plusMonths(1), campLocation, "test");

                boolean canRegister = testWeek.canRegisterCamper(testCamper);

                assertFalse(canRegister);
        }

        @Test
        public void canRegisterNullCamper() {

                Camper testCamper = new Camper(firstName, firstName, allergies, null,
                                primaryCarePhysician, primaryCarePhysician, primaryCarePhysician, 0, firstName,
                                firstName);

                testCamper = null;

                Week testWeek = new Week(10, 5, LocalDate.now().plusMonths(1), campLocation, "test");

                boolean canRegister = testWeek.canRegisterCamper(testCamper);

                assertFalse(canRegister);
        }

        @Test
        public void registerValidCamper() {
                Camper testCamper = new Camper(firstName, lastName, allergies, LocalDate.now().minusYears(12),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0,
                                "good swimmer", "son");
                Week testWeek = new Week(10, 5, LocalDate.now(),
                                campLocation, "test");

                testWeek.registerCamper(testCamper);

                assertEquals(1, testWeek.getCurrentCampers());
        }

        @Test
        public void registerInvalidCamper() {
                Camper testCamper = new Camper(firstName, lastName, allergies, LocalDate.now().minusYears(30),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0,
                                "good swimmer", "son");
                Week testWeek = new Week(10, 5, LocalDate.now(),
                                campLocation, "test");

                testWeek.registerCamper(testCamper);

                assertEquals(0, testWeek.getCurrentCampers());
        }

        @Test
        public void registerNullCamper() {
                Camper testCamper = new Camper(firstName, lastName, allergies, LocalDate.now().minusYears(12),
                                primaryEmergencyContact, secondaryEmergencyContact, primaryCarePhysician, 0,
                                "good swimmer", "son");
                testCamper = null;
                Week testWeek = new Week(10, 5, LocalDate.now(),
                                campLocation, "test");

                testWeek.registerCamper(testCamper);

                assertEquals(0, testWeek.getCurrentCampers());
        }
}
