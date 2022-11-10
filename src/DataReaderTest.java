import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DataReaderTest {
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Camper> campers = new ArrayList<>();

    @BeforeEach
    public void setup() {
        DataWriter.saveCustomers(new HashMap<>());
        DataWriter.saveCampers(new HashMap<>());
        campers.add(new Camper("firstName", "lastName", new ArrayList<String>(), LocalDate.of(2010, 1, 1),
                new Contact("firstName", "lastName", "test@test.test", "8034772355", "mom", "101 test dr"),
                new Contact("firstName", "lastName", "test@test.test", "8034772355", "mom", "101 test dr"),
                new Contact("firstName", "lastName", "test@test.test", "8034772355", "mom", "101 test dr"), 0,
                "good swimmer", "daughter"));
        customers.add(new Customer("test@test.test", "firstName", "lastName", "password",
                new CampLocation("name", "location", 0), campers,
                new Contact("firstName", "lastName", "test@test.test", "8034772355", "mom", "101 test dr")));
        for (Customer customer : customers) {
            DataWriter.createCustomer(customer);
        }
        for (Camper camper : campers) {
            DataWriter.createCamper(camper);
        }
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void testGetUsersSize() {
        assertEquals(customers.size(), DataReader.getCustomers().values().size());
    }

    @Test
    void testGetCamperSize() {
        assertEquals(campers.size(), DataReader.getCampers().values().size());
    }
}
