// Copyright 2022 Row 3

import java.util.UUID;

public class Activity {
    private UUID id;
    private String name;
    private String location;
    private String description;

    public Activity(String name, String location, String description) {
        this.id = UUID.randomUUID();
        setName(name);
        setLocation(location);
        setDescription(description);
    }

    public Activity(UUID id, String name, String location, String description) {
        this.id = id;
        setName(name);
        setLocation(location);
        setDescription(description);
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name != null){
            this.name = name;
        }
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        if(location != null){
            this.location = location;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if(description != null){
            this.description = description;
        }
    }

    public String toString(){
        return "Activity name: " + this.name +
        "\nActivity location: " + this.location +
        "\nActivity description: " + this.description;
    }
}
