// Copyright 2022 Row 3

import java.util.UUID;

public class CampLocation {
    private double pricePerCamper;
    private UUID id;
    private String name;
    private String location;
    private CampLocation campLocation;

    private CampLocation() {

    }

    public CampLocation getCampLocation(){
        if (campLocation==null){
            campLocation=new CampLocation();
        }
        return campLocation;
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
