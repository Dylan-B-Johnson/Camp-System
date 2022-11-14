import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Caleb Henry
 */

public class ActivitiesListTesting {
    @Test
    public void AddingEmptyActivity(){
        Activity activity = new Activity(null, null, null);
        assertFalse(ActivitiesList.addActivity(activity));
    }

    @Test
    public void AddingActivity(){
        Activity activity = new Activity("hi", "hello", "there");
        assertTrue(ActivitiesList.addActivity(activity));
    }

    @Test
    public void FetchingActivities(){
        assertNotNull(ActivitiesList.getActivities());
    }

}
