import java.util.UUID;

/**
 * The Camp Location, including ID name location and price
 * 
 * @author Row 3
 */

public class CampLocation {
    private double pricePerCamper;
    private UUID id;
    private String name;
    private String location;
    private final int MIN_CAMPER_AGE = 7;
    private final int MAX_CAMPER_AGE = 18;

    /**
     * Creates a CampLocation given the following parameters, including ID
     * 
     * @param id             The ID for the camp location
     * @param name           The name of the camp location
     * @param location       The geographical location of the camp location
     * @param pricePerCamper The price for every camper for the camp location
     */
    public CampLocation(UUID id, String name, String location, double pricePerCamper) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerCamper = pricePerCamper;
    }

    /**
     * Creates a camp location given the following parameters
     * 
     * @param name
     * @param location
     * @param pricePerCamper
     */
    public CampLocation(String name, String location, double pricePerCamper) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.pricePerCamper = pricePerCamper;
    }

    /**
     * Gets the CampLocation from the DataReader
     * 
     * @return CampLocation object
     */
    public static CampLocation getCampLocation() {
        return DataReader.getCampLocation();
    }

    /**
     * Gets the price for each camper
     * 
     * @return Double price for each camper
     */
    public double getPricePerCamper() {
        return pricePerCamper;
    }

    /**
     * Sets the price per camper
     * 
     * @param pricePerCamper The price per camper for the new CampLocation
     */
    public void setPricePerCamper(double pricePerCamper) {
        this.pricePerCamper = pricePerCamper;
    }

    /**
     * Gets the UUID of the camp location
     * 
     * @return UUID of camp location
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the ID of the new camp location
     * 
     * @param id UUID of camp location
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the name of the camp location
     * 
     * @return Name of camp location
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the new camp location
     * 
     * @param name Name of camp location
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the geographical location of the camp location
     * 
     * @return Location of CampLocation
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the geographical location of the new camp location
     * 
     * @param location Location of new camp location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the constant of minimum camper age
     * 
     * @return Minimum camper age for camp location
     */
    public int getMinCamperAge() {
        return MIN_CAMPER_AGE;
    }

    /**
     * Gets the constant of maximum camper age
     * 
     * @return Maximum camper age for camp location
     */
    public int getMaxCamperAge() {
        return MAX_CAMPER_AGE;
    }

}
