// Copyright 2022 Row 3

import java.util.UUID;

public class CampLocation {
    private double pricePerCamper;
    private UUID id;
    private String name;
    private String location;

    private CampLocation(UUID id, String name, String location, double pricePerCamper) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerCamper = pricePerCamper;
    }

    public double getPricePerCamper() {
        return pricePerCamper;
    }

    public void setPricePerCamper(double pricePerCamper) {
        this.pricePerCamper = pricePerCamper;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
}
