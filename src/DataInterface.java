import java.util.ArrayList;

public class DataInterface {
    public static ArrayList<Customer> getCustomerByName(String name) {
        ArrayList<Customer> results = new ArrayList<>();
        for (Customer customer : DataReader.getCustomers().values()) {
            if (customer.getName().contains(name))
                results.add(customer);
        }
        return results;
    }
}
