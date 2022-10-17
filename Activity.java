// Copyright 2022 Row 3

package campSystem;

public class Activity {
    private String id;
    private String name;
    private String location;
    private String description;

    public Activity(String name, String location, String description) {
        this.id = getId();
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
