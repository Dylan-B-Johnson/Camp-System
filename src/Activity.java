// Copyright 2022 Row 3

import java.util.UUID;

public class Activity {
    private UUID id;
    private String name;
    private String location;
    private String description;

    public Activity(String name, String location, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public Activity(UUID id, String name, String location, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public UUID getId() {
        return this.id;
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
