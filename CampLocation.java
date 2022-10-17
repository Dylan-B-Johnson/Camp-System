// Copyright 2022 Row 3


import java.util.UUID;

public class CampLocation {
    private double pricePerCamper;
    private UUID id;
    private String name;
    private static CampLocation campLocation;

    private CampLocation() {
        this.id = UUID.randomUUID();
    }

    public CampLocation getInstance() {
        return null;
    }
}
