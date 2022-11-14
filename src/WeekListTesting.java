import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

//@author Collin Remer

public class WeekListTesting {

    private int maxCampers;
    private int currentCampers;
    private CampLocation campLocation;
    private WeekList weekList;

    @BeforeEach
    public void setup() {
        currentCampers = 0;
        maxCampers = 10;
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void addWeek() {
        Week testWeek = new Week(maxCampers, currentCampers, LocalDate.now(), campLocation, "test");

        assertTrue(WeekList.addWeek(testWeek));
    }

    @Test
    public void addEmptyWeek() {
        Week testWeek = new Week(maxCampers, currentCampers, LocalDate.now(), campLocation, "test");
        testWeek = null;

        assertFalse(WeekList.addWeek(testWeek));
    }

}
