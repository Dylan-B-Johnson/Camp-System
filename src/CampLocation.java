// Copyright 2022 Row 3

import java.util.UUID;

public class CampLocation {
    private double pricePerCamper;
    private UUID id;
    private String name;
    private String location;
    private static CampLocation campLocation;

    public CampLocation(UUID id, String name, String location, double pricePerCamper) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerCamper = pricePerCamper;
    }
}
