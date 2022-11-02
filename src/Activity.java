// Copyright 2022 Row 3

import java.util.UUID;

/**
 * An activity with a name, location, and description
 * 
 * @author Row 3
 */
public class Activity {
    private UUID id;
    private String name;
    private String location;
    private String description;

    /**
     * Creates an instance of Activity, setting it's id equal to a random UUID
     * 
     * @param name        The name of the activity
     * @param location    The location of the activity
     * @param description The description of the activity
     */
    public Activity(String name, String location, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.description = description;
    }

    /**
     * Creates an instance of Activity, setting it's id equal to the supplied
     * parameter
     * 
     * @param id          The id of the activity
     * @param name        The name of the activity
     * @param location    The location of the activity
     * @param description The description of the activity
     */
    public Activity(UUID id, String name, String location, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
    }

    /**
     * Gets the id of the activty
     * 
     * @return The id of the activity
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Gets the name of the activity
     * 
     * @return The name of the activity
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the activity
     * 
     * @param name The name of the activity
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
            DataWriter.updateActivity(this.id, this);
        }
    }

    /**
     * Gets the location of the activity
     * 
     * @return The location of the activity
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets the location of the activity
     * 
     * @param location The location of the activity
     */
    public void setLocation(String location) {
        if (location != null) {
            this.location = location;
            DataWriter.updateActivity(this.id, this);
        }
    }

    /**
     * Gets the description of the activity
     * 
     * @return The description of the activity
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the activity
     * 
     * @param description The description of the activity
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
            DataWriter.updateActivity(this.id, this);
        }
    }

    /**
     * Creates a string representation of the activity
     * 
     * @return A string representation of the activity
     */
    public String toString() {
        return "Activity name: " + this.name +
                "\n\tLocation: " + this.location +
                "\n\tDescription: " + this.description;
    }
}
